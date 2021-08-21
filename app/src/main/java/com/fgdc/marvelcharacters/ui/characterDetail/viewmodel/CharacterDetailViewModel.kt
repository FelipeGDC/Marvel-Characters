package com.fgdc.marvelcharacters.ui.characterDetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.fgdc.marvelcharacters.domain.usecases.GetCharacterById
import com.fgdc.marvelcharacters.domain.usecases.GetComicById
import com.fgdc.marvelcharacters.domain.usecases.GetSeriesById
import com.fgdc.marvelcharacters.ui.characterDetail.models.CharacterDetailView
import com.fgdc.marvelcharacters.ui.characterDetail.models.ComicListView
import com.fgdc.marvelcharacters.ui.characterDetail.models.SeriesListView
import com.fgdc.marvelcharacters.utils.functional.BadRequest
import com.fgdc.marvelcharacters.utils.functional.Error
import com.fgdc.marvelcharacters.utils.functional.ErrorNoConnection
import com.fgdc.marvelcharacters.utils.functional.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacter: GetCharacterById,
    private val getComic: GetComicById,
    private val getSeries: GetSeriesById
) : ViewModel() {

    lateinit var characterDetail: CharacterDetailView
    var characterDetailResponse = MutableLiveData<CharacterDetailView>()
    var comicLists: MutableList<ComicListView> = mutableListOf()
    var comicsListResponse = MutableLiveData<List<ComicListView>>()
    var seriesLists: MutableList<SeriesListView> = mutableListOf()
    var seriesListResponse = MutableLiveData<List<SeriesListView>>()

    fun getCharacterById(characterId: Int) {
        viewModelScope.launch {
            getCharacter(GetCharacterById.Params(characterId))
                .onStart {  }
                .onEach {  }
                .catch { failure ->  }
                .collect { result ->
                    when (result) {
                        is Success<List<CharacterDetailDomain>> -> handleSuccessGetCharacter(result.data)
                    }
                }
        }
    }

    private fun handleSuccessGetCharacter(data: List<CharacterDetailDomain>?) {
        if (data?.get(0) != null) {
            characterDetail = data[0].toCharacterDetailView()
            data[0].comicsIds.map {
                getComicById(it)
            }

            data[0].seriesIds.map {
                getSeriesById(it)
            }
            characterDetail.comics.addAll(comicLists)
            characterDetailResponse.postValue(characterDetail)
        }
    }

    fun getComicById(comicId: Int) {
        viewModelScope.launch {
            getComic(GetComicById.Params(comicId))
                .onStart {  }
                .onEach {  }
                .catch { failure -> }
                .collect { result ->
                    when (result) {
                        is Success<List<ComicListDomain>> -> {
                            comicLists.addAll(result.data.map { it.toComicListView() })
                            comicsListResponse.postValue(comicLists)
                        }
                    }
                }
        }
    }

    fun getSeriesById(seriesId: Int) {
        viewModelScope.launch {
            getSeries(GetSeriesById.Params(seriesId))
                .onStart {  }
                .onEach {  }
                .catch { failure -> }
                .collect { result ->
                    when (result) {
                        is Success<List<SeriesListDomain>> -> {
                            seriesLists.addAll(result.data.map { it.toSeriesListView() })
                            seriesListResponse.postValue(seriesLists)
                        }
                    }
                }
        }
    }
}
