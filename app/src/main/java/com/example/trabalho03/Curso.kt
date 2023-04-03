package com.example.trabalho03

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Curso(
    val codigo: Int,
    val nome: String,
    val nAlunos: Int,
    val notaMec: Double
): Parcelable{

    override fun toString(): String {
        return "Codigo = $codigo, Nome = $nome, Número de Alunos = $nAlunos, Nota no Mec = $notaMec"
    }
}