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

class TelaPrincipal : AppCompatActivity() {

    private lateinit var binding: TelaPrincipalBinding
    private lateinit var listaFazendas : ArrayList<Fazenda>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val opcoesCrud = resources.getStringArray(R.array.opcoesCRUD)
        val adaptador = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, opcoesCrud)
        binding.lvOpcoes.adapter = adaptador

        listaFazendas = ArrayList()

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
            "Inserir Curso" to {register.launch(Intent(applicationContext, TelaInserir::class.java))},

            "Mostrar Cursos" to {startActivity(Intent(applicationContext, TelaMostrar::class.java).let {
                it.putParcelableArrayListExtra("777", listaFazendas)})},

            "Atualizar Cursos" to {register.launch(Intent(applicationContext, TelaAtualizar::class.java).let {
                        it.putParcelableArrayListExtra("333", listaFazendas) })},

            "Remover Cursos" to {register.launch(Intent(applicationContext, TelaRemover::class.java).let {
                      it.putParcelableArrayListExtra("333", listaFazendas) })},

            "Buscar Cursos" to {startActivity(Intent(applicationContext, TelaBuscar::class.java).let {
                        it.putParcelableArrayListExtra("333", listaFazendas) })},

            "Mostrar curso com o maior número de alunos" to {
                val maiorNumeroAlunos : Fazenda = listaFazendas.maxBy { it.valor }
                Toast.makeText(applicationContext, "Maior número de Alunos: ${maiorNumeroAlunos.nome}", Toast.LENGTH_LONG).show()
            },
            "Mostrar total de alunos da universidade" to {
                val total : Double = listaFazendas.sumOf { it.valor }
                Toast.makeText(applicationContext, "Quantidade de Alunos: $total", Toast.LENGTH_LONG).show()
            },
            "Mostrar curso com a menor nota no MEC" to {
                val menorNota : Fazenda = listaFazendas.minBy { it.valor }
                Toast.makeText(applicationContext, "Menor Nota MEC: ${menorNota.nome} | ${menorNota.valor}", Toast.LENGTH_LONG).show()

            }
        )

        binding.lvOpcoes.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val textoSelecionado = parent.getItemAtPosition(position)
            opcoes[textoSelecionado]?.invoke()
        }
    }
}