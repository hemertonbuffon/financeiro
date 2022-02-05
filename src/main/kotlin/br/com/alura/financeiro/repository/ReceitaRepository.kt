package br.com.alura.financeiro.repository

import br.com.alura.financeiro.model.Receita
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ReceitaRepository: JpaRepository<Receita, Long> {

    fun findByDescricaoContaining(descricao: String): List<Receita>
    fun findByDataBetween(primeiroDia: LocalDate, ultimoDia: LocalDate): List<Receita>
}