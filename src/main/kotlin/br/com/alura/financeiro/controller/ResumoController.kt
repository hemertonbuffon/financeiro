package br.com.alura.financeiro.controller

import br.com.alura.financeiro.dto.ResumoView
import br.com.alura.financeiro.service.ResumoService
import br.com.alura.financeiro.service.UsuarioService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/resumo")
class ResumoController(
    val resumoService: ResumoService,
    val usuarioService: UsuarioService
) {

    @GetMapping("/{ano}/{mes}")
    fun resumoMensal(@PathVariable ano: Int, @PathVariable mes: Int, @RequestHeader("Authorization") bearer: String): ResumoView{

        return resumoService.resumoMensal(ano = ano, mes = mes, usuario = usuarioService.getUser(bearer))
    }
}