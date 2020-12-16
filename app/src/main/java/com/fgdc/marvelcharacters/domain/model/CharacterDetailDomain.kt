package com.fgdc.marvelcharacters.domain.model

import com.fgdc.marvelcharacters.ui.characterDetail.models.CharacterDetailView

data class CharacterDetailDomain(
    val name: String,
    val image: String,
    val description: String,
    val comicsIds: List<Int>,
    val seriesIds: List<Int>
) {
    fun toCharacterDetailView() = CharacterDetailView(name, image, description, mutableListOf())
}