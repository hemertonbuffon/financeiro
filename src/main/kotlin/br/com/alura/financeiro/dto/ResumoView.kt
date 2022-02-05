package br.com.alura.financeiro.dto

import br.com.alura.financeiro.model.Categoria
import java.math.BigDecimal

data class ResumoView(
    val receitas: BigDecimal,
    val despesas: BigDecimal,
    val saldoFinal: BigDecimal,
    val gastoPorCategoria: Map<Categoria, BigDecimal>
) {
}