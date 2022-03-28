package br.com.alura.financeiro.controller

import br.com.alura.financeiro.dto.DespesaForm
import br.com.alura.financeiro.dto.DespesaView
import br.com.alura.financeiro.service.DespesaService
import br.com.alura.financeiro.service.UsuarioService
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/despesas")
class DespesaController(
    val despesaService : DespesaService,
    val usuarioService : UsuarioService
) {

    @GetMapping
    fun listar(descricao: String?, @RequestHeader("Authorization") bearer: String): List<DespesaView>{
        val usuario = usuarioService.getUser(bearer)
        if (descricao != null) {
            return despesaService.buscarPorDescricao(descricao, usuario)
        } else {
            return despesaService.listar(usuario)
        }
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long, @RequestHeader("Authorization") bearer: String): DespesaView{
        return despesaService.buscarPorId(id, usuarioService.getUser(bearer))
    }

    @PostMapping
    @Transactional
    fun cadastrar(@RequestBody @Valid nova: DespesaForm, @RequestHeader("Authorization") bearer: String){
        despesaService.cadastrar(nova, usuarioService.getUser(bearer))
    }

    @PutMapping("/{id}")
    @Transactional
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid nova: DespesaForm, @RequestHeader("Authorization") bearer: String): DespesaView {
        return despesaService.atualizar(id,nova,usuarioService.getUser(bearer))
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun remover(@PathVariable id: Long, @RequestHeader("Authorization") bearer: String){
        return despesaService.remover(id,usuarioService.getUser(bearer))
    }

    @GetMapping("/{ano}/{mes}")
    fun listarPorMes(@PathVariable ano: Int,@PathVariable mes: Int, @RequestHeader("Authorization") bearer: String): List<DespesaView>{
        return despesaService.buscarPorMes(ano, mes, usuarioService.getUser(bearer))
    }
}