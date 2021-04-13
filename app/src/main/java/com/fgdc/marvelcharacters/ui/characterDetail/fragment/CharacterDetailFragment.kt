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
        binding.apply {
            rvComics.adapter = comicsAdapter
            rvSeries.adapter = seriesAdapter
        }
    }

    private fun setCharacterDetail(characterDetailView: CharacterDetailView?) {
        characterDetailView?.let {
            binding.apply {
                characterImage.simpleLoad(characterDetailView.image, requireContext())
                toolbarTitle.text = characterDetailView.name
                title.text = characterDetailView.name
                if (characterDetailView.description.isNotEmpty()) {
                    characterDescription.text = characterDetailView.description
                } else {
                    characterDescription.visibility = View.GONE
                    characterDescriptionLabel.visibility = View.GONE
                }
                backButton.setOnClickListener {
                    findNavController().navigateUp()
                }
                backButtonSecondary.setOnClickListener {
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun setComicsCarousel(comics: List<ComicListView>?) {
        comicsAdapter.submitList(comics?.sortedBy { it.title } ?: emptyList())
        binding.apply {
            rvComics.scrollToPosition(0)
            if (comicsAdapter.itemCount == 0) {
                characterComicsLabel.visibility = View.GONE
                rvComics.visibility = View.GONE
            } else {
                characterComicsLabel.visibility = View.VISIBLE
                rvComics.visibility = View.VISIBLE
            }
        }
    }

    private fun setSeriesCarousel(series: List<SeriesListView>?) {
        seriesAdapter.submitList(series?.sortedBy { it.title } ?: emptyList())
        binding.apply {
            rvSeries.scrollToPosition(0)
            if (seriesAdapter.itemCount == 0) {
                characterSeriesLabel.visibility = View.GONE
                rvSeries.visibility = View.GONE
            } else {
                characterSeriesLabel.visibility = View.VISIBLE
                rvSeries.visibility = View.VISIBLE
            }
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
