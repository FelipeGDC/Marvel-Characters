package com.fgdc.marvelcharacters.ui.charactersList.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.domain.usecases.GetAllCharacters
import com.fgdc.marvelcharacters.domain.usecases.GetCharactersByName
import com.fgdc.marvelcharacters.ui.base.BaseViewModel
import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView
import com.fgdc.marvelcharacters.utils.functional.BadRequest
import com.fgdc.marvelcharacters.utils.functional.Error
import com.fgdc.marvelcharacters.utils.functional.ErrorNoConnection
import com.fgdc.marvelcharacters.utils.functional.Success
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(
    private val getAllCharacters: GetAllCharacters,
    private val getCharactersByName: GetCharactersByName
) :
    BaseViewModel() {

    companion object {
        private const val MAX_OFFSET = 20
    }

    private var lastPageCount: Int = 0

    var charactersList: MutableList<CharacterListView> = mutableListOf()
    var charactersResponse = MutableLiveData<List<CharacterListView>>()
    var moreCharactersResponse = MutableLiveData<List<CharacterListView>>()
    var loading = MutableLiveData<Boolean>()
    var noResult = MutableLiveData<Boolean>()

    fun getAllCharacters() {
        viewModelScope.launch {
            getAllCharacters(GetAllCharacters.Params(lastPageCount))
                .onStart { handleShowSpinner(true) }
                .onEach { handleShowSpinner(false) }
                .catch { failure -> handleFailure(failure) }.collect { result ->
                    when (result) {
                        is Success<List<CharacterListDomain>> -> handleSuccessGetAllCharacters(
                            result.data
                        )
                        is Error -> handleFailure(result.exception)
                        is ErrorNoConnection -> handleFailure(result.exception)
                        is BadRequest -> handleBadRequest(result.exception)
                    }
                }
        }
    }

    fun charactersListScrolled() {
        lastPageCount += MAX_OFFSET
        viewModelScope.launch {
            getAllCharacters(GetAllCharacters.Params(lastPageCount))
                .catch { failure -> handleFailure(failure) }
                .collect { result ->
                    when (result) {
                        is Success<List<CharacterListDomain>> -> handleSuccessGetMoreCharacters(
                            result.data
                        )
                        is Error -> handleFailure(result.exception)
                        is ErrorNoConnection -> handleFailure(result.exception)
                        is BadRequest -> handleBadRequest(result.exception)
                    }
                }
        }
    }

    fun getCharactersByName(name: String) {
        viewModelScope.launch {
            getCharactersByName(GetCharactersByName.Params(name))
                .onStart { loading.postValue(true) }
                .onEach { loading.postValue(false) }
                .catch { failure -> handleFailure(failure) }.collect { result ->
                    when (result) {
                        is Success<List<CharacterListDomain>> -> handleSuccessGetCharactersByName(
                            result.data
                        )
                        is Error -> handleFailure(result.exception)
                        is ErrorNoConnection -> handleFailure(result.exception)
                        is BadRequest -> handleBadRequest(result.exception)
                    }
                }
        }
    }

    fun resetToDefaultList() {
        if (charactersList.size > 0) {
            charactersResponse.postValue(charactersList)
        }
    }

    private fun handleSuccessGetAllCharacters(data: List<CharacterListDomain>) {
        charactersList = data.map { it.toCharacterView() }.toMutableList()
        charactersResponse.postValue(charactersList)
    }

    private fun handleSuccessGetCharactersByName(data: List<CharacterListDomain>) {
        if (data.isNotEmpty()) {
            noResult.postValue(false)
            charactersResponse.postValue(data.map { it.toCharacterView() }.toMutableList())
        } else {
            noResult.postValue(true)
        }
    }

    private fun handleSuccessGetMoreCharacters(data: List<CharacterListDomain>) {
        charactersList.addAll(data.map { it.toCharacterView() })
        moreCharactersResponse.postValue(charactersList)
    }

    fun filterCharactersList(text: String) {
        val filteredList = charactersList.filter { character ->
            containsText(character, text.toLowerCase(Locale.getDefault()))
        }
        if (filteredList.isNotEmpty()) {
            loading.postValue(false)
            noResult.postValue(false)
            charactersResponse.postValue(filteredList)
        } else {
            loading.postValue(true)
            charactersResponse.postValue(filteredList)
        }
    }

    private fun containsText(character: CharacterListView, text: String): Boolean {
        return character.name.toLowerCase(Locale.getDefault()).contains(text)
    }
}
