package com.example.trabalho03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.widget.Toast
import com.example.trabalho03.databinding.TelaInserirBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class TelaInserir : AppCompatActivity() {

    private val caminhoDoArquivo = "My Files"
    private var arquivoExterno: File?=null
    private val armazenamentoExternoSomenteLeitura: Boolean get() {
        var armazSomLeitRet = false
        val armazenamentoExterno = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED_READ_ONLY == armazenamentoExterno) {
            armazSomLeitRet = true
        }
        return (armazSomLeitRet)
    }
    private val armazenamentoExternoDisponivel: Boolean get() {
        var armazExtDispRet = false
        val extStorageState = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == extStorageState) {
            armazExtDispRet = true
        }
        return(armazExtDispRet)
    }

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

            //salvando em um arquivo
            arquivoExterno = File(getExternalFilesDir(caminhoDoArquivo), "fazendas")
            try {
                val fileOutPutStream = FileOutputStream(arquivoExterno)
                fileOutPutStream.write(fazenda.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
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
