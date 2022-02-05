package br.com.alura.financeiro.dto

import br.com.alura.financeiro.model.Categoria
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.*

data class ReceitaForm(
    @field:NotEmpty
    @field:Size(min= 2, max = 50)
    val descricao: String,
    @field:NotNull
    @field:Positive
    val valor: BigDecimal,
    @field:NotNull
    val data: LocalDate,
    val categoria: Categoria = Categoria.OUTRAS
) {
}
