package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
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


        var lista : ArrayList<Fazenda> = ArrayList()
        var cod : Int = 0

        if(intent.hasExtra("333")){
            lista.addAll(intent.getParcelableArrayListExtra("333")!!)
            val adaptador = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, lista)
            binding.lvTelaAtualizar.adapter = adaptador
        }

        binding.lvTelaAtualizar.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val item : Fazenda = parent.getItemAtPosition(position) as Fazenda
            cod = lista.indexOfFirst { it.codigo == item.codigo }

        }

        binding.btnAtualizar.setOnClickListener {
            lista[cod].valor = binding.etAtualizaNAlunos.text.toString().toDouble()
            lista[cod].qtdFuncionarios = binding.etAtualizaNotaMec.text.toString().toInt()

            Intent().apply {
                putExtra("444", lista[cod] as Parcelable)
                setResult(RESULT_OK, this)
            }

            Toast.makeText(applicationContext, "Curso Atualizado com Sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }



    }
}