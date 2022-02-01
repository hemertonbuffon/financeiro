package br.com.alura.financeiro.controller

import br.com.alura.financeiro.dto.ReceitaForm
import br.com.alura.financeiro.dto.ReceitaView
import br.com.alura.financeiro.service.ReceitasService
import org.springframework.data.annotation.Persistent
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/receitas")
class ReceitaController(
    val service : ReceitasService
) {

    @GetMapping
    fun listar(): List<ReceitaView>{
        return service.listar()
    }

    @GetMapping("/{id}")
    fun buscaPorId(@PathVariable id: Long): ReceitaView{
        return service.buscaPorId(id)
    }

    @PostMapping
    @Transactional
    fun cadastrar(@RequestBody @Valid nova: ReceitaForm){
        service.cadastrar(nova)
    }

    @PutMapping("/{id}")
    @Transactional
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid nova: ReceitaForm): ReceitaView{
        return service.atualizar(id,nova)
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun remover(@PathVariable id: Long){
        return service.remover(id)
    }
}