package br.com.money.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.money.http.PessoaDTO;
import br.com.money.mapper.PessoaMapper;
import br.com.money.model.Pessoa;
import br.com.money.model.SimNao;
import br.com.money.service.IPessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private IPessoaService pessoaService;
	@Autowired
	public PessoaMapper pessoaMapper;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Pessoa>> listarTodos() {
		return ResponseEntity.ok(pessoaService.obterTodos());
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable(name = "codigo") Long id, HttpServletResponse response) {
		Optional<Pessoa> pessoa = Optional.ofNullable(pessoaService.obterPorId(id));
		if (!pessoa.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pessoa.get());
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<?> criar(@Valid @RequestBody PessoaDTO pessoaDto, HttpServletResponse response) {
		Pessoa salvo = pessoaService.criar(pessoaMapper.pessoaDTOToPessoa(pessoaDto));

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(salvo.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(salvo);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(name = "codigo") Long id) {
		pessoaService.remover(id);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable(name = "codigo") Long codigo,
			@Valid @RequestBody PessoaDTO pessoaDto, HttpServletResponse response) {
		Pessoa pessoaModificado = pessoaMapper.pessoaDTOToPessoa(pessoaDto);
		return ResponseEntity.ok(pessoaService.atualizar(pessoaModificado, codigo));
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarAtivo(@PathVariable(name = "codigo") Long codigo,
			@RequestBody SimNao ativo, HttpServletResponse response) {
		pessoaService.atualizarStatus(ativo, codigo);
	}
}
