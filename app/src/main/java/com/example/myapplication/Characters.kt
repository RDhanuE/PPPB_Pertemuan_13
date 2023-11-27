package com.example.myapplication

import com.google.firebase.firestore.Exclude

data class Characters(

    @set:Exclude @get:Exclude @Exclude
    var id : String = "",
    var char_name : String,
    var char_class : String,
    var hp : Int,
    var mana : Int,
    var char_weapon : String

)
