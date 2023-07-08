package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import com.example.trabalho03.databinding.TelaInserirBinding
import com.google.firebase.firestore.FirebaseFirestore

class TelaInserir : AppCompatActivity() {

    lateinit var binding: TelaInserirBinding
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaInserirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInserir.setOnClickListener {
            val fazenda = Fazenda(binding.etCodigoCurso.text.toString().toInt(), binding.etNomeCurso.text.toString(),
                binding.etNAlunosCurso.text.toString().toDouble(), binding.etNotaMecCurso.text.toString().toInt())

            salvarFazenda(fazenda)

            Intent().apply {
                putExtra("555", fazenda as Parcelable)
                setResult(RESULT_OK, this)
            }

            Toast.makeText(applicationContext, "Fazenda Inserida com Sucesso", Toast.LENGTH_SHORT).show()

            finish()

        }

    }


    private fun salvarFazenda(fazenda: Fazenda) {
        firestore.collection("fazendas")
            .add(fazenda)
            .addOnSuccessListener { documentReference ->
                println("Fazenda salva com ID: ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                println("Erro ao salvar fazenda: $exception")
            }
    }


}
