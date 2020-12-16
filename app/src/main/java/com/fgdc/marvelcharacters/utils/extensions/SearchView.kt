package com.fgdc.marvelcharacters.utils.extensions

import androidx.appcompat.widget.SearchView

fun SearchView.onQueryTextChange(queryTextChange: (String) -> Unit) {
    this.setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    queryTextChange.invoke(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        }
    )
}
