package br.com.alura.financeiro.repository

import br.com.alura.financeiro.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long> {
     fun findByEmail(email: String?): Usuario?
}