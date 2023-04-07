package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import com.example.trabalho03.databinding.TelaInserirBinding

class TelaInserir : AppCompatActivity() {

    lateinit var binding: TelaInserirBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaInserirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInserir.setOnClickListener {
            val curso = Curso(binding.etCodigoCurso.text.toString().toInt(), binding.etNomeCurso.text.toString(),
                binding.etNAlunosCurso.text.toString().toInt(), binding.etNotaMecCurso.text.toString().toDouble())

            Intent().apply {
                putExtra("555", curso as Parcelable)
                setResult(RESULT_OK, this)
            }

            Toast.makeText(applicationContext, "Curso Inserido com Sucesso", Toast.LENGTH_SHORT).show()

            finish()

        }

    }
}