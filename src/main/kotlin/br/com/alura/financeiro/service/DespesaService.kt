package br.com.alura.financeiro.service

import br.com.alura.financeiro.dto.DespesaForm
import br.com.alura.financeiro.dto.DespesaView
import br.com.alura.financeiro.exception.InvalidEnumeratorException
import br.com.alura.financeiro.exception.NotFoundException
import br.com.alura.financeiro.model.Categoria
import br.com.alura.financeiro.model.Despesa
import br.com.alura.financeiro.model.Usuario
import br.com.alura.financeiro.repository.DespesaRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DespesaService(
    private val repository: DespesaRepository,
    val notFoundMessage: String = "Despesa não encontrada!"
) {
    fun listar(usuario: Usuario): List<DespesaView> {
        val despesas = repository.findByUsuario_Id(usuario.id!!)

        return DespesaView.converter(despesas)
    }

    fun buscarPorId(id: Long, usuario: Usuario): DespesaView{
        val despesa = repository.findByIdAndUsuario_Id(id, usuario.id!!)

        if(despesa != null) {
            return DespesaView(
                id = despesa.id,
                descricao = despesa.descricao,
                valor = despesa.valor,
                data = despesa.data,
                categoria = despesa.categoria
            )
        } else{
            throw NotFoundException(notFoundMessage)
        }
    }

    fun cadastrar(nova: DespesaForm, usuario: Usuario) {
        if (nova.categoria in Categoria.values()) {
            try {
                repository.save(
                    Despesa(
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
        } else {
            throw InvalidEnumeratorException("Não é uma categoria válida! " + Categoria.values().toString())
        }
    }

    fun atualizar(id: Long, nova: DespesaForm, usuario: Usuario) : DespesaView {
        val despesa = repository.findByIdAndUsuario_Id(id,usuario.id!!)

        if(despesa != null) {
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
        } else {
            throw NotFoundException(notFoundMessage)
        }
    }

    fun remover(id: Long, usuario: Usuario) {
        val remove = repository.findByIdAndUsuario_Id(id, usuario.id!!)

        if(remove != null) {
            repository.delete(remove)
        } else {
            throw NotFoundException(notFoundMessage)
        }
    }

    fun buscarPorDescricao(descricao: String, usuario: Usuario): List<DespesaView> {
        val lista = repository.findByDescricaoContainingAndUsuario_Id(descricao, usuario.id)

        return DespesaView.converter(lista)
    }

    fun buscarPorMes(ano: Int, mes: Int, usuario: Usuario): List<DespesaView> {
        val primeiroDia = LocalDate.of(ano,mes, 1)
        val ultimoDia = primeiroDia.withDayOfMonth(primeiroDia.lengthOfMonth())

        return DespesaView.converter(repository.findByDataBetweenAndUsuario_Id(primeiroDia, ultimoDia, usuario.id))
    }

}
