package br.com.alura.financeiro.model

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "despesas")
data class Despesa(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var descricao: String,
    var valor: BigDecimal,
    var data: LocalDate,
    @Enumerated(EnumType.STRING)
    var categoria: Categoria = Categoria.OUTRAS
    )    {

}