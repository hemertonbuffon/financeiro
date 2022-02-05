package br.com.alura.financeiro.controller

import br.com.alura.financeiro.dto.ResumoView
import br.com.alura.financeiro.service.ResumoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/resumo")
class ResumoController(
    val service: ResumoService
) {

    @GetMapping("/{ano}/{mes}")
    fun resumoMensal(@PathVariable ano: Int, @PathVariable mes: Int): ResumoView{
        return service.resumoMensal(ano = ano, mes = mes)
    }
}