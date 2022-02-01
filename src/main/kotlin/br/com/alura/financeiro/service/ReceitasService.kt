package br.com.alura.financeiro.service

import br.com.alura.financeiro.dto.ReceitaForm
import br.com.alura.financeiro.dto.ReceitaView
import br.com.alura.financeiro.exception.NotFoundException
import br.com.alura.financeiro.model.Receita
import br.com.alura.financeiro.repository.ReceitaRepository
import org.springframework.stereotype.Service

@Service
class ReceitasService(
    private val repository : ReceitaRepository,
    val notFoundMessage: String = "Receita não encontrada!"
) {
    fun listar(): List<ReceitaView> {
        val receitas = repository.findAll()

        return ReceitaView.converter(receitas)
    }

    fun buscaPorId(id: Long): ReceitaView {
        val receita = repository.findById(id)
        if(receita.isPresent){
            return ReceitaView(
                id = receita.get().id,
                descricao = receita.get().descricao,
                valor = receita.get().valor,
                data = receita.get().data
            )
        }
        throw NotFoundException(notFoundMessage)
    }

    fun cadastrar(nova: ReceitaForm) {
        try {
            repository.save(
                Receita(
                    descricao = nova.descricao,
                    valor = nova.valor,
                    data = nova.data
                )
            )
        } catch(e: Exception){
            println("Erro ao cadastrar")
        }
    }

    fun atualizar(id: Long,  nova: ReceitaForm) : ReceitaView {
        val receita = repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}

        receita.valor = nova.valor
        receita.descricao = nova.descricao
        receita.data = nova.data

        return ReceitaView(
            id = receita.id,
            descricao = receita.descricao,
            valor = receita.valor,
            data = receita.data
        )
    }

    fun remover(id: Long) {
        val remove = repository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
        repository.delete(remove)
    }


}
