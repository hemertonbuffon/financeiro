package br.com.alura.financeiro.repository

import br.com.alura.financeiro.model.Despesa
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface DespesaRepository: JpaRepository<Despesa, Long> {
    fun findByIdAndUsuario_Id(id: Long, user_id: Long): Despesa?
    fun findByUsuario_Id(user_id: Long): List<Despesa>
    fun findByDescricaoContainingAndUsuario_Id(descricao: String, id: Long?): List<Despesa>
    fun findByDataBetweenAndUsuario_Id(primeiroDia: LocalDate?, ultimoDia: LocalDate?,id: Long?): List<Despesa>
}
