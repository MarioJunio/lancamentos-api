package br.com.mj.resource;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mj.dto.LancamentoDTO;
import br.com.mj.model.Lancamento;
import br.com.mj.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource extends BaseResource<Lancamento, LancamentoDTO> {

	@Autowired
	private LancamentoService lancamentoService;

	@GetMapping
	public ResponseEntity<Collection<LancamentoDTO>> buscarTodos() {
		List<Lancamento> lancamentos = lancamentoService.buscarTodos();

		return lancamentos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(toDto(lancamentos));
	}

	@PostMapping
	public ResponseEntity<LancamentoDTO> novo(@RequestBody @Valid LancamentoDTO lancamentoDTO) throws Exception {
		
		Lancamento lancamentoSalvo = lancamentoService.novo(toEntity(lancamentoDTO));
		return ResponseEntity.created(null).body(toDto(lancamentoSalvo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<LancamentoDTO> atualizar(@PathVariable Long id, @RequestBody LancamentoDTO lancamentoDTO) {

		Lancamento lancamentoAtualizado = lancamentoService.atualizar(id, toEntity(lancamentoDTO));
		return ResponseEntity.ok(toDto(lancamentoAtualizado));

	} 

	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		lancamentoService.remover(id);
	}
}
