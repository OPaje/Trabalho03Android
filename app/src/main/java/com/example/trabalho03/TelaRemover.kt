package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.trabalho03.databinding.TelaRemoverBinding

class TelaRemover : AppCompatActivity() {
        lateinit var binding: TelaRemoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaRemoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var lista : ArrayList<Fazenda> = ArrayList()

        if(intent.hasExtra("333")){
            lista.addAll(intent.getParcelableArrayListExtra("333")!!)
            val adaptador = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, lista)
            binding.lvRemover.adapter = adaptador
        }

        binding.lvRemover.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val item = parent.getItemAtPosition(position)

            Intent().apply {
                putExtra("888", item as Parcelable)
                setResult(RESULT_OK, this)
            }

            Toast.makeText(applicationContext, "Curso Removido com Sucesso", Toast.LENGTH_SHORT).show()

            finish()
        }

    }
}