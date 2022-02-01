package br.com.alura.financeiro.service

import br.com.alura.financeiro.dto.DespesaForm
import br.com.alura.financeiro.dto.DespesaView
import br.com.alura.financeiro.dto.ReceitaView
import br.com.alura.financeiro.exception.NotFoundException
import br.com.alura.financeiro.model.Despesa
import br.com.alura.financeiro.repository.DespesaRepository
import org.springframework.stereotype.Service

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
        val despesa = repository.findById(id)
        if(despesa.isPresent){
            return DespesaView(
                id = despesa.get().id,
                descricao = despesa.get().descricao,
                valor = despesa.get().valor,
                data = despesa.get().data
            )
        }
        throw NotFoundException(notFoundMessage)
    }

    fun cadastrar(nova: DespesaForm) {
        try {
            repository.save(
                Despesa(
                    descricao = nova.descricao,
                    valor = nova.valor,
                    data = nova.data
                )
            )
        } catch(e: Exception){
            println("Erro ao cadastrar")
        }
    }

    fun atualizar(id: Long,  nova: DespesaForm) : DespesaView {
        val despesa = repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}

        despesa.valor = nova.valor
        despesa.descricao = nova.descricao
        despesa.data = nova.data

        return DespesaView(
            id = despesa.id,
            descricao = despesa.descricao,
            valor = despesa.valor,
            data = despesa.data
        )
    }

    fun remover(id: Long) {
        val remove = repository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
        repository.delete(remove)
    }



}
