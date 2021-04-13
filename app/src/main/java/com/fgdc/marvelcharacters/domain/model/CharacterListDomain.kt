package com.fgdc.marvelcharacters.domain.model

import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView

data class CharacterListDomain(val id: Int, val name: String, val image: String, val desc: String) {
    fun toCharacterView() = CharacterListView(id, name, image, desc)
}
