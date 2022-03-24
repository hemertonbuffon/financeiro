package br.com.alura.financeiro.model

import javax.persistence.*

@Entity
@Table(name = "usuarios")
class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    @Column(unique=true)
    val email: String,
    val password: String
) {
}