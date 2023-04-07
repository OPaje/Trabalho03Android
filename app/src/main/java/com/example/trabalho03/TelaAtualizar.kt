package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.trabalho03.databinding.TelaAtualizarBinding

class TelaAtualizar : AppCompatActivity() {

    lateinit var binding : TelaAtualizarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaAtualizarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val bundle : Bundle? = intent.extras
        var lista : ArrayList<Curso> = ArrayList()
        var cod : Int = 0

        if(intent.hasExtra("333")){
            lista.addAll(intent.getParcelableArrayListExtra("333")!!)
        }


        val adaptador = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, lista)
        binding.lvTelaAtualizar.adapter = adaptador


        binding.lvTelaAtualizar.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val item : Curso = parent.getItemAtPosition(position) as Curso
            cod = item.codigo
        }

        binding.btnAtualizar.setOnClickListener {
            lista.get(cod).nAlunos = binding.etAtualizaNAlunos.text.toString().toInt()
            lista.get(cod).notaMec = binding.etAtualizaNotaMec.text.toString().toDouble()

            Intent().apply {
                putExtra("333", lista as Parcelable)
            }
        }

    }
}