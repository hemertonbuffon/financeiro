package br.com.alura.financeiro.dto

import br.com.alura.financeiro.model.Categoria
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class DespesaForm(
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
