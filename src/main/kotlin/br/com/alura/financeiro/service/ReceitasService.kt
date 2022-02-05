package br.com.alura.financeiro.service

import br.com.alura.financeiro.dto.ReceitaForm
import br.com.alura.financeiro.dto.ReceitaView
import br.com.alura.financeiro.exception.InvalidEnumeratorException
import br.com.alura.financeiro.exception.NotFoundException
import br.com.alura.financeiro.model.Categoria
import br.com.alura.financeiro.model.Receita
import br.com.alura.financeiro.repository.ReceitaRepository
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.stereotype.Service
import java.time.LocalDate

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
        val receita = repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}

        return ReceitaView(
             id = receita.id,
             descricao = receita.descricao,
             valor = receita.valor,
             data = receita.data,
             categoria = receita.categoria
        )
    }

    fun cadastrar(nova: ReceitaForm) {
        if (nova.categoria in Categoria.values()) {
            try {
                repository.save(
                    Receita(
                        descricao = nova.descricao,
                        valor = nova.valor,
                        data = nova.data,
                        categoria = nova.categoria
                    )
                )
            } catch (e: Exception) {
                println("Erro ao cadastrar")
            }
        } else{
            throw InvalidEnumeratorException("Não é uma categoria válida! "+Categoria.values().toString())
        }
    }

    fun atualizar(id: Long,  nova: ReceitaForm) : ReceitaView {
        val receita = repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}

        if (nova.categoria in Categoria.values()) {
            receita.valor = nova.valor
            receita.descricao = nova.descricao
            receita.data = nova.data
            receita.categoria = nova.categoria
        } else{
            throw InvalidEnumeratorException("Não é uma categoria válida! "+Categoria.values().toString())
        }

        return ReceitaView(
            id = receita.id,
            descricao = receita.descricao,
            valor = receita.valor,
            data = receita.data,
            categoria = receita.categoria
        )
    }

    fun remover(id: Long) {
        val remove = repository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
        repository.delete(remove)
    }

    fun buscarPorDescricao(descricao: String): List<ReceitaView> {
        val lista = repository.findByDescricaoContaining(descricao)

        return ReceitaView.converter(lista)
    }

    fun buscarPorMes(ano: Int, mes: Int): List<ReceitaView> {
        val primeiroDia = LocalDate.of(ano,mes, 1)
        val ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth())

        return ReceitaView.converter(repository.findByDataBetween(primeiroDia, ultimoDia))
    }


}
