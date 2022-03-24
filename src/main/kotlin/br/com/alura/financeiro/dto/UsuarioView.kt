package br.com.alura.financeiro.dto

data class UsuarioView(
    val id: Long? = null,
    val nome: String,
    val email: String,
    val password: String
) {

}
