package br.com.alura.financeiro.controller

import br.com.alura.financeiro.dto.ReceitaForm
import br.com.alura.financeiro.dto.ReceitaView
import br.com.alura.financeiro.service.ReceitasService
import br.com.alura.financeiro.service.UsuarioService
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/receitas")
class ReceitaController(
    val receitasService : ReceitasService,
    val usuarioService: UsuarioService
) {

    @GetMapping
    fun listar(descricao: String?, @RequestHeader("Authorization") bearer: String): List<ReceitaView>{
        if (descricao != null) {
            return receitasService.buscarPorDescricao(descricao = descricao, usuario = usuarioService.getUser(bearer))
        } else {
            return receitasService.listar(usuarioService.getUser(bearer = bearer))
        }
    }

    @GetMapping("/{id}")
    fun buscaPorId(@PathVariable id: Long, @RequestHeader("Authorization") bearer: String): ReceitaView{
        return receitasService.buscaPorId(id, usuarioService.getUser(bearer = bearer))
    }

    @PostMapping
    @Transactional
    fun cadastrar(@RequestBody @Valid nova: ReceitaForm, @RequestHeader("Authorization") bearer: String){
        receitasService.cadastrar(nova, usuarioService.getUser(bearer = bearer))
    }

    @PutMapping("/{id}")
    @Transactional
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid nova: ReceitaForm, @RequestHeader("Authorization") bearer: String): ReceitaView{
        return receitasService.atualizar(id,nova, usuarioService.getUser(bearer = bearer))
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun remover(@PathVariable id: Long, @RequestHeader("Authorization") bearer: String){
        return receitasService.remover(id, usuarioService.getUser(bearer = bearer))
    }

    @GetMapping("/{ano}/{mes}")
    fun listarPorMes(@PathVariable ano: Int,@PathVariable mes: Int, @RequestHeader("Authorization") bearer: String): List<ReceitaView>{
        return receitasService.buscarPorMes(ano, mes, usuarioService.getUser(bearer = bearer))
    }
}