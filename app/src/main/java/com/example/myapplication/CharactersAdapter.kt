package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCharactersBinding

typealias OnClickUpdate = (Characters) -> Unit
typealias OnClickDelete = (Characters) -> Unit

class CharactersAdapter (private val listCharacters: MutableList<Characters>, private val onClickUpdate: OnClickUpdate, private val onClickDelete: OnClickDelete) : RecyclerView.Adapter<CharactersAdapter.ItemCharactersViewHolder>() {
    inner class ItemCharactersViewHolder (private val binding: ItemCharactersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(characters: Characters) {
            with(binding) {
                charName.text = characters.char_name
                charClass.text = characters.char_class
                charHP.text = characters.hp.toString()
                charMana.text = characters.mana.toString()
                charWeapon.text = characters.char_weapon
                charUpdateButton.setOnClickListener{
                    onClickUpdate(characters)
                }
                charDeleteButton.setOnClickListener{
                    onClickDelete(characters)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCharactersViewHolder {
        val binding = ItemCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCharactersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCharacters.size
    }

    override fun onBindViewHolder(holder: ItemCharactersViewHolder, position: Int) {
        holder.bind(listCharacters[position])
    }
}