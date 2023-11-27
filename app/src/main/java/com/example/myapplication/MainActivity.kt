package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

//    Instance firestore, docRef, serta CRUD nya ditaruh di companion object, biar nanti bisa digunakan
//    oleh activity lain (create dan update)
    companion object {
        val firestore = FirebaseFirestore.getInstance()

//    Kenapa livedata ?  karena nantinya kita akan banyak meng-update data List budget tersebuut.
//    Penggunaan live data membuat data tersebut akan selalu terupdate, tanpa perlu memanggil2
//    suatu fungsi berulang - ulang
        val charListLiveData: MutableLiveData<List<Characters>> by lazy {
            MutableLiveData<List<Characters>>()
        }

//        Membuat koleksi baru pada firestore
        val charactersCollectionRef = firestore.collection("Characters")

//        Fungsi CRUD nya
        fun addCharacter(characters: Characters) {
            charactersCollectionRef.add(characters).addOnFailureListener {
                Log.d("AddCharacter", "Error adding characters : ", it)
            }
        }

        fun updateCharacter(characters: Characters, updateID : String){
            charactersCollectionRef.document(updateID).set(characters).addOnFailureListener {
                Log.d("UpdateCharacter", "Error updating characters : ", it)
            }
        }

        fun deleteCharacters(deleteID : String) {
            charactersCollectionRef.document(deleteID).delete().addOnFailureListener {
                Log.d("DeleteCharacter", "Error deleting characters", it)
            }
        }
    }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            mainRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            mainCreateButton.setOnClickListener{
                val intent = Intent(this@MainActivity, CharactersCreate::class.java)
                startActivity(intent)
            }
//            Menambah TextWatcher untuk menjalankan sesuatu ketika isi dari search berubah. Disini
//            ketika search selesai diisi, data dengan nama yang sama akan ditampilkan
            mainSearch.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    getAllCharacters()
                }

            })
        }


        observeCharacters()
        getAllCharacters()
    }

    private fun getAllCharacters() {

        val query = if (binding.mainSearch.text.toString().isEmpty()) {
            charactersCollectionRef
        } else {
            charactersCollectionRef.whereEqualTo("char_name", binding.mainSearch.text.toString().capitalize())
        }
        query.addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(this, "Error listening to characters data", Toast.LENGTH_SHORT)
                    .show()
            }
            val characters = arrayListOf<Characters>()
            value?.forEach {
                documentReference ->
                characters.add(
                    Characters(
                        documentReference.id,
                        documentReference.get("char_name").toString(),
                        documentReference.get("char_class").toString(),
                        documentReference.get("hp").toString().toInt(),
                        documentReference.get("mana").toString().toInt(),
                        documentReference.get("char_weapon").toString()
                    )
                )
            }
            if (characters != null) {
                charListLiveData.postValue(characters)
            }
        }
    }

    private fun observeCharacters() {
        charListLiveData.observe(this) {
            characters ->
            var listCharacter = characters.toMutableList()
            binding.mainRecyclerView.adapter = CharactersAdapter(listCharacter, {
                    characters ->
                val bundle = Bundle()
                bundle.putString("ID", characters.id)
                bundle.putString("NAME", characters.char_name)
                bundle.putString("CLASS", characters.char_class)
                bundle.putInt("HP", characters.hp)
                bundle.putInt("MANA", characters.mana)
                bundle.putString("WEAPON", characters.char_weapon)
                val intent = Intent(this@MainActivity, CharactersUpdate::class.java).apply { putExtras(bundle) }
                startActivity(intent)
            }, {
                    characters ->
                deleteCharacters(characters.id)
                })
        }
    }
}