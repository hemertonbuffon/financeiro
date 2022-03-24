package br.com.alura.financeiro.service

import br.com.alura.financeiro.exception.NotFoundException
import br.com.alura.financeiro.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    val notFoundMessage: String = "Usuario não encontrado!"
) : UserDetailsService
{
    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = repository.findByEmail(username) ?: throw NotFoundException(notFoundMessage)

        return UserDetail(usuario)
    }
}