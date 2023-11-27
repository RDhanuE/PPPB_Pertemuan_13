package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.CreateCharactersBinding

class CharactersCreate : AppCompatActivity() {
    private lateinit var binding: CreateCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = CreateCharactersBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            BackButton.setOnClickListener{
                finish()
            }
            CreateButton.setOnClickListener{
                CreateNameContainer.error = null
                CreateClassContainer.error = null
                CreateHealthContainer.error = null
                CreateManaContainer.error = null
                CreateWeaponContainer.error = null
                if (CreateName.text?.isEmpty() == true){
                    CreateNameContainer.error = "Your hero will need a name"
                } else if (CreateClass.text?.isEmpty() == true){
                    CreateClassContainer.error = "Tell us more about your hero"
                } else if (CreateHealth.text?.isEmpty() == true){
                    CreateHealthContainer.error = "Mighty hero have great vitality"
                } else if (CreateMana.text?.isEmpty() == true){
                    CreateManaContainer.error = "Does magic flow's trough your hero ?"
                } else if (CreateWeapon.text?.isEmpty() == true){
                    CreateWeaponContainer.error = "What does your hero use to fight ?"
                } else {
                    MainActivity.addCharacter(
                        Characters(
                            char_name = CreateName.text.toString(),
                            char_class = CreateClass.text.toString(),
                            hp = CreateHealth.text.toString().toInt(),
                            mana = CreateMana.text.toString().toInt(),
                            char_weapon = CreateWeapon.text.toString()
                        )
                    )
                    finish()
                }
            }
        }
    }
}