package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.trabalho03.databinding.TelaPrincipalBinding
import com.google.firebase.firestore.FirebaseFirestore

class TelaPrincipal : AppCompatActivity() {
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var binding: TelaPrincipalBinding
    private lateinit var listaFazendas : ArrayList<Fazenda>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val opcoesCrud = resources.getStringArray(R.array.opcoesCRUD)
        val adaptador = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, opcoesCrud)
        binding.lvOpcoes.adapter = adaptador

        listaFazendas = ArrayList( )

        val register = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    if (it.hasExtra("555")) {
                        val fazenda : Fazenda? = it.getParcelableExtra("555")
                        if (fazenda != null) {
                            listaFazendas.add(fazenda)
                        }
                    }
                    if (it.hasExtra("444")) {
                        val fazenda : Fazenda? = it.getParcelableExtra("444")
                        if (fazenda != null) {
                            val indice = listaFazendas.indexOfFirst { fazenda.codigo == it.codigo }
                            listaFazendas[indice] = fazenda
                        }
                    }
                    if (it.hasExtra("888")) {
                        val fazenda : Fazenda? = it.getParcelableExtra("888")
                        if (fazenda != null) {
                            val indice = listaFazendas.indexOfFirst { fazenda.codigo == it.codigo }
                            listaFazendas.removeAt(indice)
                        }
                    }
                }
            }
        }

        val opcoes = hashMapOf(
            "Inserir Fazenda" to {register.launch(Intent(applicationContext, TelaInserir::class.java))},

            "Mostrar Fazenda" to {startActivity(Intent(applicationContext, TelaMostrar::class.java).let {
                it.putParcelableArrayListExtra("777", listaFazendas)})},

            "Atualizar Fazenda" to {register.launch(Intent(applicationContext, TelaAtualizar::class.java).let {
                        it.putParcelableArrayListExtra("333", listaFazendas) })},

            "Remover Fazenda" to {register.launch(Intent(applicationContext, TelaRemover::class.java).let {
                      it.putParcelableArrayListExtra("333", listaFazendas) })},

            "Buscar Fazenda" to {startActivity(Intent(applicationContext, TelaBuscar::class.java).let {
                        it.putParcelableArrayListExtra("333", listaFazendas) })},

            "Média dos valores das propriedades" to {
                var soma : Double = listaFazendas.sumOf { it.valor }
                var media = soma/listaFazendas.size
                Log.d("TAG", "$media")
                Toast.makeText(applicationContext, "Média dos valores das propriedades: $media", Toast.LENGTH_LONG).show()
            }
        )

        binding.lvOpcoes.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val textoSelecionado = parent.getItemAtPosition(position)
            opcoes[textoSelecionado]?.invoke()
        }
    }

    private fun carregarFazendas() :ArrayList<Fazenda>{
        val fazendas = ArrayList<Fazenda>()
        firestore.collection("fazendas")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val fazenda = document.toObject(Fazenda::class.java)
                        fazendas.add(fazenda)
                    }

                } else {
                    println("Erro ao carregar fazendas: ${task.exception}")
                }
            }
        return fazendas
    }
}

