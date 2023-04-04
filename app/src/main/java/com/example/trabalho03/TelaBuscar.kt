package com.example.trabalho03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.trabalho03.databinding.TelaBuscarBinding

class TelaBuscar : AppCompatActivity() {
    lateinit var binding: TelaBuscarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaBuscarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle : Bundle? = intent.extras

        binding.btnBuscar.setOnClickListener {
            val codigo = binding.etPegaCodigo.text.toString().toInt()

            bundle?.apply {

                val lista : ArrayList<Curso>? = intent.getParcelableArrayListExtra("333")!!

                if (lista != null) {
                    for (curso in lista){
                        if(curso.codigo == codigo){
                            binding.tvCursoBuscado.text = "${curso.toString()}"

                        }
                    }
                }
            }

        }
    }
}