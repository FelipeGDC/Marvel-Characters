package com.fgdc.marvelcharacters.ui.charactersList.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fgdc.marvelcharacters.R
import com.fgdc.marvelcharacters.databinding.FragmentCharactersListBinding
import com.fgdc.marvelcharacters.ui.MainActivity
import com.fgdc.marvelcharacters.ui.charactersList.adapters.CharactersListAdapter
import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView
import com.fgdc.marvelcharacters.ui.charactersList.viewmodel.CharactersListViewModel
import com.fgdc.marvelcharacters.utils.exception.ErrorHandler
import com.fgdc.marvelcharacters.utils.extensions.endless
import com.fgdc.marvelcharacters.utils.extensions.failure
import com.fgdc.marvelcharacters.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersListFragment : Fragment() {

    private val charactersListViewModel: CharactersListViewModel by viewModels()

    private lateinit var binding: FragmentCharactersListBinding
    private val adapter = CharactersListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setObservers()
        binding.apply {
            rvCharacters.endless { charactersListViewModel.charactersListScrolled() }
            rvCharacters.adapter = adapter
            if (adapter.itemCount == 0) {
                charactersListViewModel.getAllCharacters()
            }
        }
    }

    private fun setObservers() {
        with(charactersListViewModel) {
            failure(failure, ::handleFailure)
            observe(showSpinner, ::showSpinner)
            observe(charactersResponse, ::setListOfCharacters)
            observe(moreCharactersResponse, ::addMoreCharacters)
        }
    }

    private fun setListOfCharacters(characters: List<CharacterListView>?) {
        binding.apply {
            emptyView.visibility = View.GONE
            rvCharacters.visibility = View.VISIBLE
            adapter.submitList(characters ?: emptyList())
        }
    }

    private fun addMoreCharacters(characters: List<CharacterListView>?) {
        adapter.submitList(characters ?: emptyList())
    }

    private fun handleFailure(failure: Throwable?) {
        when (failure?.message) {
            ErrorHandler.NETWORK_ERROR_MESSAGE, ErrorHandler.UNKNOWN_ERROR -> {
                binding.apply {
                    rvCharacters.visibility = View.GONE
                    emptyView.visibility = View.VISIBLE
                    errorMessage.text = failure.message ?: getString(R.string.common_error)
                    tryAgainBtn.setOnClickListener {
                        charactersListViewModel.getAllCharacters()
                    }
                }
            }
            ErrorHandler.BAD_REQUEST -> {
                binding.apply {
                    rvCharacters.visibility = View.GONE
                    emptyView.visibility = View.VISIBLE
                    errorMessage.text = failure?.message ?: getString(R.string.common_error)
                    tryAgainBtn.setOnClickListener {
                        charactersListViewModel.getAllCharacters()
                    }
                }
            }
        }
    }

    private fun showSpinner(show: Boolean?) {
        when (show) {
            true -> progressStatus(View.VISIBLE)
            false -> progressStatus(View.GONE)
        }
    }

    private fun progressStatus(viewStatus: Int) =
        with(activity) {
            if (this is MainActivity) {
                this.showProgressStatus(viewStatus)
            }
        }
}
