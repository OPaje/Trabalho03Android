package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
                            Log.i("Teste", "Lista completa: $listaCursos")
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
                }else if(textoSelecionado.equals("Atualizar Cursos")){
                    Intent(applicationContext, TelaAtualizar::class.java).let {
                        register.launch(it)
                    }
                }else if(textoSelecionado.equals("Buscar Cursos")){
                    Intent(applicationContext, TelaBuscar::class.java).let {
                        it.putParcelableArrayListExtra("333", listaCursos)
                        this.startActivity(it)
                    }
                }
            }

    }
}