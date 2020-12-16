package com.fgdc.marvelcharacters.di.component

import com.fgdc.marvelcharacters.di.module.ViewModule
import com.fgdc.marvelcharacters.di.scope.ViewScope
import com.fgdc.marvelcharacters.ui.MainActivity
import com.fgdc.marvelcharacters.ui.characterDetail.fragment.CharacterDetailFragment
import com.fgdc.marvelcharacters.ui.charactersList.fragment.CharactersListFragment
import dagger.Subcomponent

@ViewScope
@Subcomponent(
    modules = [
        ViewModule::class
    ]
)
interface ViewComponent {

    fun inject(activity: MainActivity)

    fun inject(charactersListFragment: CharactersListFragment)

    fun inject(characterDetailFragment: CharacterDetailFragment)
}
