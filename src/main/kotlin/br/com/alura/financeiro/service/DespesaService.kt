package br.com.alura.financeiro.service

import br.com.alura.financeiro.dto.DespesaForm
import br.com.alura.financeiro.dto.DespesaView
import br.com.alura.financeiro.exception.InvalidEnumeratorException
import br.com.alura.financeiro.exception.NotFoundException
import br.com.alura.financeiro.model.Categoria
import br.com.alura.financeiro.model.Despesa
import br.com.alura.financeiro.repository.DespesaRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DespesaService(
    private val repository: DespesaRepository,
    val notFoundMessage: String = "Despesa não encontrada!"
) {
    fun listar(): List<DespesaView> {
        val despesas = repository.findAll()

        return DespesaView.converter(despesas)
    }

    fun buscarPorId(id: Long): DespesaView{
        val despesa = repository.findById(id).orElseThrow {NotFoundException(notFoundMessage)}

        return DespesaView(
                id = despesa.id,
                descricao = despesa.descricao,
                valor = despesa.valor,
                data = despesa.data,
                categoria = despesa.categoria
        )
    }

    fun cadastrar(nova: DespesaForm) {
        if (nova.categoria in Categoria.values()) {
            try {
                repository.save(
                    Despesa(
                        descricao = nova.descricao,
                        valor = nova.valor,
                        data = nova.data,
                        categoria = nova.categoria
                    )
                )
            } catch (e: Exception) {
                println("Erro ao cadastrar")
            }
        } else {
            throw InvalidEnumeratorException("Não é uma categoria válida! " + Categoria.values().toString())
        }
    }

    fun atualizar(id: Long,  nova: DespesaForm) : DespesaView {
        val despesa = repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}

        despesa.valor = nova.valor
        despesa.descricao = nova.descricao
        despesa.data = nova.data
        despesa.categoria = nova.categoria

        return DespesaView(
            id = despesa.id,
            descricao = despesa.descricao,
            valor = despesa.valor,
            data = despesa.data,
            categoria = despesa.categoria
        )
    }

    fun remover(id: Long) {
        val remove = repository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
        repository.delete(remove)
    }

    fun buscarPorDescricao(descricao: String): List<DespesaView> {
        val lista = repository.findByDescricaoContaining(descricao)

        return DespesaView.converter(lista)
    }

    fun buscarPorMes(ano: Int, mes: Int): List<DespesaView> {
        val primeiroDia = LocalDate.of(ano,mes, 1)
        val ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth())

        return DespesaView.converter(repository.findByDataBetween(primeiroDia, ultimoDia))
    }

}
