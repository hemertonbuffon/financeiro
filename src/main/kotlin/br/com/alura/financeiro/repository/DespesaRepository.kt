package br.com.alura.financeiro.repository

import br.com.alura.financeiro.model.Despesa
import org.springframework.data.jpa.repository.JpaRepository

interface DespesaRepository: JpaRepository<Despesa, Long> {

}
