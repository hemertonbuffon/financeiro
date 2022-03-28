package br.com.alura.financeiro.repository

import br.com.alura.financeiro.model.Receita
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ReceitaRepository: JpaRepository<Receita, Long> {

    fun findByIdAndUsuario_Id(id: Long, user_id: Long): Receita?
    fun findByUsuario_Id(user_id: Long): List<Receita>
    fun findByDescricaoContainingAndUsuario_Id(descricao: String, user_id: Long): List<Receita>
    fun findByDataBetweenAndUsuario_Id(primeiroDia: LocalDate?, ultimoDia: LocalDate?, user_id: Long?): List<Receita>
}