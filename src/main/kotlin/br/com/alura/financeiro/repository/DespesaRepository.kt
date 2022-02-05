package br.com.alura.financeiro.repository

import br.com.alura.financeiro.model.Despesa
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface DespesaRepository: JpaRepository<Despesa, Long> {
    fun findByDescricaoContaining(descricao: String): List<Despesa>
    fun findByDataBetween(primeiroDia: LocalDate?, ultimoDia: LocalDate?): List<Despesa>
}
