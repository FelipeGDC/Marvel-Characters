package com.fgdc.marvelcharacters.ui.charactersList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fgdc.marvelcharacters.R
import com.fgdc.marvelcharacters.databinding.ItemCharacterBinding
import com.fgdc.marvelcharacters.ui.charactersList.fragment.CharactersListFragmentDirections
import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView
import com.fgdc.marvelcharacters.utils.extensions.squaredListLoad

class CharactersListAdapter :
    ListAdapter<CharacterListView, CharactersListAdapter.CharactersViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CharactersListAdapter.CharactersViewHolder,
        position: Int
    ) {
        val characterItem = getItem(position)
        if (characterItem != null) {
            holder.bind(characterItem)
        }
    }

    inner class CharactersViewHolder(private val itemBinding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(character: CharacterListView) {
            itemBinding.apply {
                item.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.down_to_up)
                characterId.text =
                    root.context.getString(R.string.character_id, character.id.toString())
                characterName.text = character.name
                characterImage.squaredListLoad(character.image, itemBinding.root.context)
                root.setOnClickListener {
                    root.findNavController()
                        .navigate(
                            CharactersListFragmentDirections.actionListToCharacterDetail(
                                character.id
                            )
                        )
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<CharacterListView>() {

            override fun areItemsTheSame(
                oldCharacter: CharacterListView,
                newCharacter: CharacterListView
            ) = oldCharacter.id == newCharacter.id

            override fun areContentsTheSame(
                oldCharacter: CharacterListView,
                newCharacter: CharacterListView
            ) = oldCharacter == newCharacter
        }
    }
}
