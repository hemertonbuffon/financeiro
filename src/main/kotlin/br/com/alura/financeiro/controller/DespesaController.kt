package br.com.alura.financeiro.controller

import br.com.alura.financeiro.dto.DespesaForm
import br.com.alura.financeiro.dto.DespesaView
import br.com.alura.financeiro.dto.ReceitaForm
import br.com.alura.financeiro.dto.ReceitaView
import br.com.alura.financeiro.service.DespesaService
import br.com.alura.financeiro.service.ReceitasService
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/despesas")
class DespesaController(
    val service : DespesaService
) {

    @GetMapping
    fun listar(): List<DespesaView>{
        return service.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): DespesaView{
        return service.buscarPorId(id)
    }

    @PostMapping
    @Transactional
    fun cadastrar(@RequestBody @Valid nova: DespesaForm){
        service.cadastrar(nova)
    }

    @PutMapping("/{id}")
    @Transactional
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid nova: DespesaForm): DespesaView {
        return service.atualizar(id,nova)
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun remover(@PathVariable id: Long){
        return service.remover(id)
    }
}