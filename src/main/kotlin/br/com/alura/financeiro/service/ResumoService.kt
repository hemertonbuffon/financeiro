package br.com.alura.financeiro.service

import br.com.alura.financeiro.dto.ResumoView
import br.com.alura.financeiro.model.Despesa
import br.com.alura.financeiro.repository.DespesaRepository
import br.com.alura.financeiro.repository.ReceitaRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

@Service
class ResumoService(
    private val receitaRepository: ReceitaRepository,
    private val despesaRepository: DespesaRepository
) {
    fun resumoMensal(ano: Int, mes: Int): ResumoView {
        val primeiroDia = LocalDate.of(ano,mes, 1)
        val ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth())

        val receitas = receitaRepository.findByDataBetween(primeiroDia, ultimoDia)
        val despesas = despesaRepository.findByDataBetween(primeiroDia, ultimoDia)

        val sumReceitas: BigDecimal = receitas.sumOf { r -> r.valor }
        val sumDespesas: BigDecimal = despesas.sumOf { d -> d.valor }

        val saldoFinal: BigDecimal = sumReceitas - sumDespesas

        val gastoPorCategoria = despesas.associate { despesa: Despesa ->
            Pair(despesa.categoria, BigDecimal.ZERO)
        }.toMutableMap()

        despesas.forEach { d: Despesa ->
            gastoPorCategoria[d.categoria] = gastoPorCategoria[d.categoria]?.plus(d.valor)
        }

        return ResumoView(
            receitas = sumReceitas,
            despesas = sumDespesas,
            saldoFinal = saldoFinal,
            gastoPorCategoria = gastoPorCategoria
        )
    }

}
