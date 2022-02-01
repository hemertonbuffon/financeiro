package br.com.alura.financeiro.dto

import java.sql.Timestamp
import java.time.LocalDateTime

data class ErrorView(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val mensagem: String?,
    val path: String
) {

}
