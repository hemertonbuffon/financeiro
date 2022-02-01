package br.com.alura.financeiro.repository

import br.com.alura.financeiro.model.Receita
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReceitaRepository: JpaRepository<Receita, Long> {
}