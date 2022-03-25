package br.com.alura.financeiro.service

import br.com.alura.financeiro.dto.UsuarioForm
import br.com.alura.financeiro.exception.NotFoundException
import br.com.alura.financeiro.model.Usuario
import br.com.alura.financeiro.repository.UsuarioRepository
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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

    fun cadastrar(novo: UsuarioForm) {
        try {
            repository.save(
                Usuario(
                    nome = novo.nome,
                    email = novo.email,
                    password = bCryptEnconder().encode(novo.password)
                )
            )
        } catch (e: Exception) {
            println("Erro ao cadastrar")
        }
    }

    @Bean
    fun bCryptEnconder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}