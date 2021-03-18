package com.fgdc.marvelcharacters.ui.charactersList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fgdc.marvelcharacters.databinding.ItemCharacterBinding
import com.fgdc.marvelcharacters.ui.charactersList.fragment.CharactersListFragmentDirections
import com.fgdc.marvelcharacters.ui.charactersList.models.CharacterListView
import com.fgdc.marvelcharacters.utils.extensions.circleListLoad
import java.util.*

class CharactersListAdapter :
    ListAdapter<CharacterListView, CharactersListAdapter.CharactersViewHolder>(DIFF_CALLBACK),
    Filterable {

    var currentListItems: List<CharacterListView>? = null
    private var listItemsFiltered: List<CharacterListView>? = null

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

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    listItemsFiltered = currentListItems
                } else {
                    currentListItems?.let {
                        val filteredList = arrayListOf<CharacterListView>()
                        for (item in currentListItems!!) {
                            if (charString.toLowerCase(Locale.ENGLISH) in item.name.toLowerCase(
                                    Locale.ENGLISH
                                )
                            ) {
                                filteredList.add(item)
                            }
                        }

                        listItemsFiltered = filteredList
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = listItemsFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                listItemsFiltered = filterResults.values as List<CharacterListView>?
                submitList(listItemsFiltered)
            }
        }
    }

    inner class CharactersViewHolder(private val itemBinding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(character: CharacterListView) {
            itemBinding.characterName.text = character.name
            itemBinding.characterImage.circleListLoad(character.image, itemBinding.root.context)
            itemBinding.root.setOnClickListener {
                itemBinding.root.findNavController()
                    .navigate(CharactersListFragmentDirections.actionListToCharacterDetail(character.id))
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
