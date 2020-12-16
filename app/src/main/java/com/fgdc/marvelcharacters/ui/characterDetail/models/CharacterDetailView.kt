package com.fgdc.marvelcharacters.ui.characterDetail.models

data class CharacterDetailView(
    val name: String,
    val image: String,
    val description: String,
    val comics: MutableList<ComicListView>
)
