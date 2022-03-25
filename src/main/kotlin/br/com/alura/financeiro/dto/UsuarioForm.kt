package br.com.alura.financeiro.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class UsuarioForm(
    @field:NotEmpty
    @field:Size(min= 2, max = 100)
    val nome: String,
    @field:NotEmpty
    @field:Email
    val email: String,
    @field:Size(min= 6, max = 100)
    val password: String
) {
}
