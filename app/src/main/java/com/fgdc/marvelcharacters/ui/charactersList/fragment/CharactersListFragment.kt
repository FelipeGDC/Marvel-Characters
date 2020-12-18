package com.fgdc.marvelcharacters.ui.charactersList.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fgdc.marvelcharacters.R
import com.fgdc.marvelcharacters.databinding.FragmentCharactersListBinding
import com.fgdc.marvelcharacters.di.component.ViewComponent
import com.fgdc.marvelcharacters.ui.base.BaseFragment
import com.fgdc.marvelcharacters.ui.charactersList.adapters.CharactersListAdapter
import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView
import com.fgdc.marvelcharacters.ui.charactersList.viewmodel.CharactersListViewModel
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(charactersListViewModel) {
            observe(showSpinner, ::showSpinner)
            failure(failure, ::handleFailure)
            failure(badRequest, ::handleBadRequest)
            observe(charactersResponse, ::setListOfCharacters)
            observe(moreCharactersResponse, ::addMoreCharacters)
        }
        setHasOptionsMenu(true)
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

    private fun setListOfCharacters(characters: List<CharacterListView>?) {
        binding.emptyView.visibility = View.GONE
        binding.rvCharacters.visibility = View.VISIBLE
        adapter.submitList(characters ?: emptyList())
    }

    private fun addMoreCharacters(characters: List<CharacterListView>?) {
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
