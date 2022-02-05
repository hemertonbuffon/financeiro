package br.com.alura.financeiro.dto

import br.com.alura.financeiro.model.Categoria
import br.com.alura.financeiro.model.Receita
import java.math.BigDecimal
import java.time.LocalDate
import java.util.stream.Collectors

data class ReceitaView(
    val id: Long?,
    val descricao: String,
    val valor: BigDecimal,
    val data: LocalDate,
    val categoria: Categoria
) {
    companion object{
        fun converter(receitas: List<Receita>) : List<ReceitaView>{
            return receitas.stream().map { r -> ReceitaView(
                id = r.id,
                descricao = r.descricao,
                valor = r.valor,
                data = r.data,
                categoria = r.categoria
            )
            }.collect(Collectors.toList())
        }
    }
}