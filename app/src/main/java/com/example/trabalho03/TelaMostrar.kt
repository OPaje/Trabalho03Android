package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.trabalho03.databinding.TelaMostrarBinding
import com.google.firebase.firestore.FirebaseFirestore

class TelaMostrar : AppCompatActivity() {

    lateinit var binding: TelaMostrarBinding
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaMostrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("777")){
            val lista : ArrayList<Fazenda> = intent.getParcelableArrayListExtra("777")!!
            val adaptador = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, lista)
            binding.lvTelaMostrar.adapter = adaptador
        }

        binding.btnHomeTelaMostrar.setOnClickListener {
                finish()
        }

    }


}