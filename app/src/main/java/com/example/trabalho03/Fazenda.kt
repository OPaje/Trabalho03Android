package com.example.trabalho03

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Fazenda(
    val codigo: Int,
    val nome: String,
    var valor: Double,
    var qtdFuncionarios: Int
): Parcelable{

    override fun toString(): String {
        return "Codigo = $codigo| Nome = $nome| Valor da propriedade = $valor| Quantidade de funcionários = $qtdFuncionarios"
    }
}