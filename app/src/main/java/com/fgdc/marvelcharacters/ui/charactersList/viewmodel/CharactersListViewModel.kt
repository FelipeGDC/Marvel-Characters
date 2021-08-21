package com.fgdc.marvelcharacters.ui.charactersList.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.fgdc.marvelcharacters.domain.usecases.GetAllCharacters
import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView
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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(val getAllCharacters: GetAllCharacters) :
    ViewModel() {

    companion object {
        private const val MAX_OFFSET = 20
    }

    private var lastPageCount: Int = 0

    var charactersList: MutableList<CharacterListView> = mutableListOf()
    var charactersResponse = MutableLiveData<List<CharacterListView>>()
    var moreCharactersResponse = MutableLiveData<List<CharacterListView>>()

    fun getAllCharacters() {
        viewModelScope.launch {
            getAllCharacters(GetAllCharacters.Params(lastPageCount))
                .onStart {  }
                .onEach {  }
                .catch { failure ->  }.collect { result ->
                    when (result) {
                        is Success<List<CharacterListDomain>> -> handleSuccessGetAllCharacters(
                            result.data
                        )
                    }
                }
        }
    }

    fun charactersListScrolled() {
        lastPageCount += MAX_OFFSET
        viewModelScope.launch {
            getAllCharacters(GetAllCharacters.Params(lastPageCount))
                .catch { failure -> }
                .collect { result ->
                    when (result) {
                        is Success<List<CharacterListDomain>> -> handleSuccessGetMoreCharacters(
                            result.data
                        )
                    }
                }
        }
    }

    private fun handleSuccessGetAllCharacters(data: List<CharacterListDomain>) {
        charactersList = data.map { it.toCharacterView() }.toMutableList()
        charactersResponse.postValue(charactersList)
    }

    private fun handleSuccessGetMoreCharacters(data: List<CharacterListDomain>) {
        charactersList.addAll(data.map { it.toCharacterView() })
        moreCharactersResponse.postValue(charactersList)
    }

    fun filterCharactersList(text: String) {
        val filteredList = charactersList.filter { character ->
            containsText(character, text.toLowerCase(Locale.getDefault()))
        }
        charactersResponse.postValue(filteredList)
    }

    private fun containsText(character: CharacterListView, text: String): Boolean {
        return character.name.toLowerCase(Locale.getDefault()).contains(text)
    }
}
