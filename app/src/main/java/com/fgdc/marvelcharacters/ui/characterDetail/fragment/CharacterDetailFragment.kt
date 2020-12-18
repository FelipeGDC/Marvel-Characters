package com.fgdc.marvelcharacters.ui.characterDetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fgdc.marvelcharacters.R
import com.fgdc.marvelcharacters.databinding.FragmentCharacterDetailBinding
import com.fgdc.marvelcharacters.di.component.ViewComponent
import com.fgdc.marvelcharacters.ui.base.BaseFragment
import com.fgdc.marvelcharacters.ui.characterDetail.adapters.ComicsListAdapter
import com.fgdc.marvelcharacters.ui.characterDetail.adapters.SeriesListAdapter
import com.fgdc.marvelcharacters.ui.characterDetail.models.CharacterDetailView
import com.fgdc.marvelcharacters.ui.characterDetail.models.ComicListView
import com.fgdc.marvelcharacters.ui.characterDetail.models.SeriesListView
import com.fgdc.marvelcharacters.ui.characterDetail.viewmodel.CharacterDetailViewModel
import com.fgdc.marvelcharacters.utils.extensions.failure
import com.fgdc.marvelcharacters.utils.extensions.observe
import com.fgdc.marvelcharacters.utils.extensions.showInfoAlertDialog
import com.fgdc.marvelcharacters.utils.extensions.simpleLoad
import javax.inject.Inject

class CharacterDetailFragment : BaseFragment() {

    @Inject
    lateinit var characterDetailViewModel: CharacterDetailViewModel
    override fun initializeInjector(viewComponent: ViewComponent) = viewComponent.inject(this)

    private lateinit var binding: FragmentCharacterDetailBinding
    private val args: CharacterDetailFragmentArgs by navArgs()
    private val comicsAdapter = ComicsListAdapter()
    private val seriesAdapter = SeriesListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(characterDetailViewModel) {
            observe(showSpinner, ::showSpinner)
            failure(failure, ::handleFailure)
            failure(badRequest, ::handleBadRequest)
            observe(characterDetailResponse, ::setCharacterDetail)
            observe(comicsListResponse, ::setComicsCarousel)
            observe(seriesListResponse, ::setSeriesCarousel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterDetailViewModel.getCharacterById(args.characterId)
        binding.rvComics.adapter = comicsAdapter
        binding.rvSeries.adapter = seriesAdapter
    }

    private fun setCharacterDetail(characterDetailView: CharacterDetailView?) {
        characterDetailView?.let {
            binding.characterImage.simpleLoad(characterDetailView.image, requireContext())
            binding.toolbarTitle.text = characterDetailView.name
            binding.title.text = characterDetailView.name
            if (characterDetailView.description.isNotEmpty()) {
                binding.characterDescription.text = characterDetailView.description
            } else {
                binding.characterDescription.visibility = View.GONE
                binding.characterDescriptionLabel.visibility = View.GONE
            }
            binding.backButton.setOnClickListener{
                findNavController().navigateUp()
            }
            binding.backButtonSecondary.setOnClickListener{
                findNavController().navigateUp()
            }
        }
    }

    private fun setComicsCarousel(comics: List<ComicListView>?) {
        comicsAdapter.submitList(comics?.sortedBy { it.title } ?: emptyList())
        binding.rvComics.scrollToPosition(0)
        if (comicsAdapter.itemCount == 0) {
            binding.characterComicsLabel.visibility = View.GONE
            binding.rvComics.visibility = View.GONE
        } else {
            binding.characterComicsLabel.visibility = View.VISIBLE
            binding.rvComics.visibility = View.VISIBLE
        }
    }

    private fun setSeriesCarousel(series: List<SeriesListView>?) {
        seriesAdapter.submitList(series?.sortedBy { it.title } ?: emptyList())
        binding.rvSeries.scrollToPosition(0)
        if (seriesAdapter.itemCount == 0) {
            binding.characterSeriesLabel.visibility = View.GONE
            binding.rvSeries.visibility = View.GONE
        } else {
            binding.characterSeriesLabel.visibility = View.VISIBLE
            binding.rvSeries.visibility = View.VISIBLE
        }
    }

    private fun handleFailure(failure: Throwable?) {
        showInfoAlertDialog {
            setTitle(getString(R.string.error_title))
            setText(failure?.message ?: getString(R.string.common_error))
            btnAccept {
                findNavController().navigateUp()
            }
        }.show()
    }

    private fun handleBadRequest(failure: Throwable?) {
        showInfoAlertDialog {
            setTitle(getString(R.string.bad_request))
            setText(failure?.message ?: getString(R.string.common_error))
            btnAccept {
                findNavController().navigateUp()
            }
        }.show()
    }
}
