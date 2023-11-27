package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.UpdateCharactersBinding

class CharactersUpdate : AppCompatActivity() {
    private lateinit var binding: UpdateCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = UpdateCharactersBinding.inflate(layoutInflater)
        binding = UpdateCharactersBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val data = intent.extras

        with(binding){
            BackButton.setOnClickListener{
                finish()
            }
            EditName.setText(data?.getString("NAME"))
            EditClass.setText(data?.getString("CLASS"))
            EditHealth.setText(data?.getInt("HP").toString())
            EditMana.setText(data?.getInt("MANA").toString())
            EditWeapon.setText(data?.getString("WEAPON"))

            EditButton.setOnClickListener{
                EditNameContainer.error = null
                EditClassContainer.error = null
                EditHealthContainer.error = null
                EditManaContainer.error = null
                EditWeaponContainer.error = null
                if (EditName.text?.isEmpty() == true){
                    EditNameContainer.error = "Your hero will need a name"
                } else if (EditClass.text?.isEmpty() == true){
                    EditClassContainer.error = "Tell us more about your hero"
                } else if (EditHealth.text?.isEmpty() == true){
                    EditHealthContainer.error = "Mighty hero have great vitality"
                } else if (EditMana.text?.isEmpty() == true){
                    EditManaContainer.error = "Does magic flow's trough your hero ?"
                } else if (EditWeapon.text?.isEmpty() == true){
                    EditWeaponContainer.error = "What does your hero use to fight ?"
                } else {
                    MainActivity.updateCharacter(
                        Characters(
                            char_name = EditName.text.toString(),
                            char_class = EditClass.text.toString(),
                            hp = EditHealth.text.toString().toInt(),
                            mana = EditMana.text.toString().toInt(),
                            char_weapon = EditWeapon.text.toString()
                        ), data?.getString("ID").toString()
                    )
                    finish()
                }
            }
        }
    }
}