package br.com.alura.financeiro.service

import br.com.alura.financeiro.config.JWTUtil
import br.com.alura.financeiro.dto.UsuarioForm
import br.com.alura.financeiro.exception.AlreadyRegistredException
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
    private val jwtUtil: JWTUtil,
    val notFoundMessage: String = "Usuario não encontrado!",
    val alreadyRegistredMessage: String = "Email já cadastrado!",
) : UserDetailsService
{
    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = repository.findByEmail(username) ?: throw NotFoundException(notFoundMessage)

        return UserDetail(usuario)
    }

    fun cadastrar(novo: UsuarioForm) {
        val usuario = repository.findByEmail(novo.email)
        if(usuario == null){
            repository.save(
                Usuario(
                    nome = novo.nome,
                    email = novo.email,
                    password = bCryptEnconder().encode(novo.password)
                )
            )
        } else{
            throw AlreadyRegistredException(alreadyRegistredMessage)
        }
    }

    @Bean
    fun bCryptEnconder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    fun getUser(bearer: String): Usuario {
        val token = bearer.let { jwt ->
            jwt.startsWith("Bearer ")
            jwt.substring(7, jwt.length)
        }

        return repository.findByEmail(jwtUtil.getUsername(token))!!
    }
}