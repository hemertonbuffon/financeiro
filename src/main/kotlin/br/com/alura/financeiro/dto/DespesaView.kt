package br.com.alura.financeiro.dto

import br.com.alura.financeiro.model.Despesa
import java.math.BigDecimal
import java.time.LocalDate
import java.util.stream.Collectors

class DespesaView(
    val id: Long?,
    val descricao: String,
    val valor: BigDecimal,
    val data: LocalDate
) {
    companion object{
        fun converter(despesas: List<Despesa>) : List<DespesaView>{
            return despesas.stream().map { d -> DespesaView(
                id = d.id,
                descricao = d.descricao,
                valor = d.valor,
                data = d.data
            )
            }.collect(Collectors.toList())
        }
    }
}