package br.com.alura.financeiro.service

import br.com.alura.financeiro.dto.ReceitaForm
import br.com.alura.financeiro.dto.ReceitaView
import br.com.alura.financeiro.exception.InvalidEnumeratorException
import br.com.alura.financeiro.exception.NotFoundException
import br.com.alura.financeiro.model.Categoria
import br.com.alura.financeiro.model.Receita
import br.com.alura.financeiro.model.Usuario
import br.com.alura.financeiro.repository.ReceitaRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReceitasService(
    private val repository : ReceitaRepository,
    val notFoundMessage: String = "Receita não encontrada!"
) {
    fun listar(usuario: Usuario): List<ReceitaView> {
        val receitas = repository.findByUsuario_Id(usuario.id!!)

        return ReceitaView.converter(receitas)
    }

    fun buscaPorId(id: Long, usuario: Usuario): ReceitaView {
        val receita = repository.findByIdAndUsuario_Id(id, usuario.id!!)

        if(receita != null) {
            return ReceitaView(
                id = receita.id,
                descricao = receita.descricao,
                valor = receita.valor,
                data = receita.data,
                categoria = receita.categoria
            )
        } else{
            throw NotFoundException(notFoundMessage)
        }
    }

    fun cadastrar(nova: ReceitaForm, usuario: Usuario) {
        if (nova.categoria in Categoria.values()) {
            try {
                repository.save(
                    Receita(
                        descricao = nova.descricao,
                        valor = nova.valor,
                        data = nova.data,
                        categoria = nova.categoria,
                        usuario = usuario
                    )
                )
            } catch (e: Exception) {
                println("Erro ao cadastrar")
            }
        } else{
            throw InvalidEnumeratorException("Não é uma categoria válida! "+Categoria.values().toString())
        }
    }

    fun atualizar(id: Long, nova: ReceitaForm, usuario: Usuario) : ReceitaView {
        val receita = repository.findByIdAndUsuario_Id(id, usuario.id!!)

        if(receita != null) {
            if (nova.categoria in Categoria.values()) {
                receita.valor = nova.valor
                receita.descricao = nova.descricao
                receita.data = nova.data
                receita.categoria = nova.categoria

                return ReceitaView(
                    id = receita.id,
                    descricao = receita.descricao,
                    valor = receita.valor,
                    data = receita.data,
                    categoria = receita.categoria
                )
            } else {
                throw InvalidEnumeratorException("Não é uma categoria válida! " + Categoria.values().toString())
            }
        } else {
            throw NotFoundException(notFoundMessage)
        }
    }

    fun remover(id: Long, usuario: Usuario) {
        val remove = repository.findByIdAndUsuario_Id(id, usuario.id!!)
        if (remove != null) {
            repository.delete(remove)
        } else {
            throw NotFoundException(notFoundMessage)
        }
    }

    fun buscarPorDescricao(descricao: String, usuario: Usuario): List<ReceitaView> {
        val lista = repository.findByDescricaoContainingAndUsuario_Id(descricao, usuario.id!!)

        return ReceitaView.converter(lista)
    }

    fun buscarPorMes(ano: Int, mes: Int, usuario: Usuario): List<ReceitaView> {
        val primeiroDia = LocalDate.of(ano,mes, 1)
        val ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth())

        return ReceitaView.converter(repository.findByDataBetweenAndUsuario_Id(primeiroDia, ultimoDia, usuario.id))
    }


}
