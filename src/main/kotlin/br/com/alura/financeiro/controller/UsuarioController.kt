package br.com.alura.financeiro.controller

import br.com.alura.financeiro.dto.UsuarioForm
import br.com.alura.financeiro.service.UsuarioService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/usuario")
class UsuarioController(
    val service: UsuarioService
) {

    @PostMapping
    @Transactional
    fun cadastrarUsuario(@RequestBody @Valid novo: UsuarioForm){
        service.cadastrar(novo)
    }
}