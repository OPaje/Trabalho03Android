package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
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
                            adaptador.notifyDataSetChanged()
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
                            adaptador.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

        binding.lvOpcoes.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val textoSelecionado = parent.getItemAtPosition(position)

                if (textoSelecionado == "Inserir Curso") {
                    Intent(applicationContext, TelaInserir::class.java).let {
                        register.launch(it)
                    }
                } else if (textoSelecionado.equals("Mostrar Cursos")) {
                    Intent(applicationContext, TelaMostrar::class.java).let {
                        it.putParcelableArrayListExtra("777", listaCursos)
                        this.startActivity(it)
                    }
                } else if (textoSelecionado.equals("Buscar Cursos")) {
                    Intent(applicationContext, TelaBuscar::class.java).let {
                        it.putParcelableArrayListExtra("333", listaCursos)
                        this.startActivity(it)
                    }
                } else if (textoSelecionado.equals("Mostrar curso com o maior número de alunos")) {
                    val maiorNumeroAlunos: Curso = listaCursos.maxBy { it.nAlunos }

                    Toast.makeText(
                        applicationContext,
                        "Maior número de alunos: ${maiorNumeroAlunos.nome}",
                        Toast.LENGTH_LONG
                    ).show()

                } else if (textoSelecionado.equals("Mostrar total de alunos da universidade")) {
                    val total: Int = listaCursos.sumOf { it.nAlunos }

                    Toast.makeText(
                        applicationContext,
                        "Quantidade de Alunos: $total",
                        Toast.LENGTH_LONG
                    ).show()

                } else if (textoSelecionado.equals("Mostrar curso com a menor nota no MEC")) {
                    val menorNota: Curso = listaCursos.minBy { it.notaMec }

                    Toast.makeText(
                        applicationContext,
                        "Menor Nota no Mec: ${menorNota.nome}| ${menorNota.notaMec}",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (textoSelecionado.equals("Remover Cursos")) {
                    Intent(applicationContext, TelaRemover::class.java).let {
                        it.putParcelableArrayListExtra("333", listaCursos)
                        registerRemover.launch(it)
                    }
                } else if (textoSelecionado.equals("Atualizar Cursos")) {
                    Intent(applicationContext, TelaAtualizar::class.java).let {
                        it.putParcelableArrayListExtra("333", listaCursos)
                        registerAtualiza.launch(it)
                    }
                }
            }
    }
}