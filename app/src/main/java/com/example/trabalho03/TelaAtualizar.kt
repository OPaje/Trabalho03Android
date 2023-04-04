package com.example.trabalho03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.trabalho03.databinding.TelaAtualizarBinding

class TelaAtualizar : AppCompatActivity() {

    lateinit var binding : TelaAtualizarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaAtualizarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val bundle : Bundle? = intent.extras
        val lista : ArrayList<Curso> = ArrayList()


        if(intent.hasExtra("333")){
            lista.addAll(intent.getParcelableArrayListExtra("333")!!)
        }

        val adaptador = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, lista)
        binding.lvTelaAtualizar.adapter = adaptador


        binding.lvTelaAtualizar.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val item = parent.getItemAtPosition(position)

        }

        binding.btnAtualizar.setOnClickListener {

        }

    }
}