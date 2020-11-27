package br.com.money.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.money.model.projection.ResumoLancamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.money.http.LancamentoDTO;
import br.com.money.mapper.LancamentoMapper;
import br.com.money.model.Lancamento;
import br.com.money.repository.LancamentoRepository;
import br.com.money.repository.filter.LancamentoFilter;
import br.com.money.service.ILancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private ILancamentoService service;
	
	@Autowired
	private LancamentoMapper mapper;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<Lancamento>> pesquisar(LancamentoFilter filter, Pageable pageable) {
		return ResponseEntity.ok(repository.filtrar(filter, pageable));
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<ResumoLancamento>> resumir(LancamentoFilter filter, Pageable pageable) {
		return ResponseEntity.ok(repository.resumoLancamento(filter, pageable));
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable(name = "codigo") Long id, HttpServletResponse response) {
		Optional<Lancamento> lancamento = Optional.ofNullable(repository.findOne(id));
		if(!lancamento.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lancamento.get());
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody LancamentoDTO lancamentoDto, HttpServletResponse response) {
		Lancamento lancamento = mapper.lancamentoDTOToLancamento(lancamentoDto);

		Lancamento salvo = service.criar(lancamento);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
		.buildAndExpand(salvo.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(salvo);
	}
	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(name = "codigo") Long id) {
		repository.delete(id);
	}
//	
//	@PutMapping("/{codigo}")
//	public ResponseEntity<Categoria> atualizar(@PathVariable(name = "codigo") Long codigo, @Valid @RequestBody CategoriaDTO categoriaDto, HttpServletResponse response) {
//		Categoria atual = repository.findOne(codigo);
//		Categoria categoria = mapper.categoriaDTOToCategoria(categoriaDto);
//		BeanUtils.copyProperties(categoria, atual, "codigo");
//		Categoria salvo = repository.save(atual);
//		return ResponseEntity.ok(salvo);
//	}
}
