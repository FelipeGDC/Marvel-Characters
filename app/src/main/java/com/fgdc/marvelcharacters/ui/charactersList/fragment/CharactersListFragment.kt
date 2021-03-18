package com.fgdc.marvelcharacters.ui.charactersList.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import com.fgdc.marvelcharacters.R
import com.fgdc.marvelcharacters.databinding.FragmentCharactersListBinding
import com.fgdc.marvelcharacters.di.component.ViewComponent
import com.fgdc.marvelcharacters.ui.base.BaseFragment
import com.fgdc.marvelcharacters.ui.charactersList.adapters.CharactersListAdapter
import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView
import com.fgdc.marvelcharacters.ui.charactersList.viewmodel.CharactersListViewModel
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.extensions.empty
import com.fgdc.marvelcharacters.utils.extensions.endless
import com.fgdc.marvelcharacters.utils.extensions.failure
import com.fgdc.marvelcharacters.utils.extensions.observe
import javax.inject.Inject

class CharactersListFragment : BaseFragment() {

    @Inject
    lateinit var charactersListViewModel: CharactersListViewModel

    override fun initializeInjector(viewComponent: ViewComponent) = viewComponent.inject(this)

    private lateinit var binding: FragmentCharactersListBinding
    private val adapter = CharactersListAdapter()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(charactersListViewModel) {
            observe(showSpinner, ::showSpinner)
            failure(failure, ::handleFailure)
            failure(badRequest, ::handleBadRequest)
            observe(charactersResponse, ::setListOfCharacters)
            observe(moreCharactersResponse, ::addMoreCharacters)
            observe(loading, ::showLoading)
            observe(noResult, ::showNoResult)
        }
        setHasOptionsMenu(true)
    }

    private fun showLoading(loading: Boolean?) {
        when (loading) {
            true -> binding.loadingResults.visibility = View.VISIBLE
            false -> binding.loadingResults.visibility = View.INVISIBLE
        }
    }

    private fun showNoResult(noResult: Boolean?) {
        when (noResult) {
            true -> binding.noResultView.visibility = View.VISIBLE
            false -> binding.noResultView.visibility = View.INVISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCharacters.endless { charactersListViewModel.charactersListScrolled() }
        binding.rvCharacters.adapter = adapter
        if (adapter.itemCount == 0) {
            charactersListViewModel.getAllCharacters()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        createSearchFunction(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun createSearchFunction(menu: Menu) {
        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search character name"
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        searchView.setOnCloseListener { true }

        searchView.setOnQueryTextFocusChangeListener { v, _ ->
            if ((v as SearchView).query.isEmpty()) {
                charactersListViewModel.resetToDefaultList()
            }
        }

        searchView.setOnQueryTextListener(object :
                OnQueryTextListener,
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.rvCharacters.visibility = View.GONE
                    charactersListViewModel.getCharactersByName(query ?: String.empty())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    charactersListViewModel.filterCharactersList(newText ?: String.empty())
                    return true
                }
            })
    }

    private fun setListOfCharacters(characters: List<CharacterListView>?) {
        binding.emptyView.visibility = View.GONE
        binding.rvCharacters.visibility = View.VISIBLE
        if (adapter.currentListItems == null) {
            adapter.currentListItems = characters ?: emptyList()
        }
        adapter.submitList(characters ?: emptyList())
    }

    private fun addMoreCharacters(characters: List<CharacterListView>?) {
        if (adapter.currentListItems == null) {
            adapter.currentListItems = characters ?: emptyList()
        }
        adapter.submitList(characters ?: emptyList())
    }

    private fun handleFailure(failure: Throwable?) {
        if (failure?.message == ErrorHandler.NETWORK_ERROR_MESSAGE) {
            binding.rvCharacters.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
            binding.errorMessage.text = failure.message ?: getString(R.string.common_error)
            binding.tryAgainBtn.setOnClickListener {
                charactersListViewModel.getAllCharacters()
            }
        }
    }

    private fun handleBadRequest(failure: Throwable?) {
        binding.rvCharacters.visibility = View.GONE
        binding.emptyView.visibility = View.VISIBLE
        binding.errorMessage.text = failure?.message ?: getString(R.string.common_error)

        binding.tryAgainBtn.setOnClickListener {
            charactersListViewModel.getAllCharacters()
        }
    }
}
