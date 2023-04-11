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
    private lateinit var listaCursos : ArrayList<Curso>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val opcoesCrud = resources.getStringArray(R.array.opcoesCRUD)
        val adaptador = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, opcoesCrud)
        binding.lvOpcoes.adapter = adaptador

        listaCursos = ArrayList()

        val register = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    if (it.hasExtra("555")) {
                        val curso : Curso? = it.getParcelableExtra("555")
                        if (curso != null) {
                            listaCursos.add(curso)
                            adaptador.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

        val registerAtualiza = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    if (it.hasExtra("444")) {
                        val curso : Curso? = it.getParcelableExtra("444")
                        if (curso != null) {
                            val indice = listaCursos.indexOfFirst { curso.codigo == it.codigo }
                            listaCursos[indice] = curso
                             // não deu certo
                        }
                    }
                }
            }
        }

        val registerRemover = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    if (it.hasExtra("444")) {
                        val curso : Curso? = it.getParcelableExtra("444")
                        if (curso != null) {
                            val indice = listaCursos.indexOfFirst { curso.codigo == it.codigo }
                            listaCursos.removeAt(indice)
                        }
                    }
                }
            }
        }

        /*fun criaRegister(opcao: String, requestCode : Int) : ActivityResultLauncher<Intent>{
            return registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()){result: ActivityResult ->
                if(result.resultCode == RESULT_OK){
                    result.data?.let {
                        when (opcao){
                            "Inserir Curso" -> {
                                if(intent.hasExtra("555")){
                                    val curso : Curso? = it.getParcelableExtra("555")
                                    if (curso != null) {
                                        listaCursos.add(curso)
                                    }
                                }
                            }

                            "Atualizar Cursos" -> {
                                if(intent.hasExtra("444")){
                                    val curso : Curso? = it.getParcelableExtra("444")
                                    if(curso != null){
                                        val indice = listaCursos.indexOfFirst{it.codigo == curso.codigo}
                                        listaCursos[indice] = curso
                                    }
                                }
                            }

                            "Remover Cursos" -> {
                                if (intent.hasExtra("444")){
                                    val curso : Curso? = it.getParcelableExtra("444")
                                    if(curso != null){
                                        val indice = listaCursos.indexOfFirst{it.codigo == curso.codigo}
                                        listaCursos.removeAt(indice)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }



        val register = criaRegister("Inserir Curso", 123)
        val registerAtualiza = criaRegister("Atualizar Cursos", 333)
        val registerRemover = criaRegister("Remover Cursos", 333)*/

        val opcoes = hashMapOf(
            "Inserir Curso" to {register.launch(Intent(applicationContext, TelaInserir::class.java))},

            "Mostrar Cursos" to {startActivity(Intent(applicationContext, TelaMostrar::class.java).let {
                it.putParcelableArrayListExtra("777", listaCursos)})},

            "Atualizar Cursos" to {registerAtualiza.launch(Intent(applicationContext, TelaAtualizar::class.java).let {
                        it.putParcelableArrayListExtra("333", listaCursos) })},

            "Remover Cursos" to {registerRemover.launch(Intent(applicationContext, TelaRemover::class.java).let {
                      it.putParcelableArrayListExtra("333", listaCursos) })},

            "Buscar Cursos" to {startActivity(Intent(applicationContext, TelaBuscar::class.java).let {
                        it.putParcelableArrayListExtra("333", listaCursos) })},

            "Mostrar curso com o maior número de alunos" to {
                val maiorNumeroAlunos : Curso = listaCursos.maxBy { it.nAlunos }
                Toast.makeText(applicationContext, "Maior número de Alunos: ${maiorNumeroAlunos.nome}", Toast.LENGTH_LONG).show()
            },
            "Mostrar total de alunos da universidade" to {
                val total : Int = listaCursos.sumOf { it.nAlunos }
                Toast.makeText(applicationContext, "Quantidade de Alunos: $total", Toast.LENGTH_LONG).show()
            },
            "Mostrar curso com a menor nota no MEC" to {
                val menorNota : Curso = listaCursos.minBy { it.notaMec }
                Toast.makeText(applicationContext, "Menor Nota MEC: ${menorNota.nome} | ${menorNota.notaMec}", Toast.LENGTH_LONG).show()

            }
        )

        binding.lvOpcoes.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val textoSelecionado = parent.getItemAtPosition(position)
            opcoes[textoSelecionado]?.invoke()
        }
    }
}