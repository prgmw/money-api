package br.com.money.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.money.http.CategoriaDTO;
import br.com.money.mapper.CategoriaMapper;
import br.com.money.model.Categoria;
import br.com.money.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private CategoriaMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> listarTodos() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable(name = "codigo") Long id, HttpServletResponse response) {
		Optional<Categoria> categoria = Optional.ofNullable(repository.findOne(id));
		if(!categoria.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoria.get());
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody CategoriaDTO categoriaDto, HttpServletResponse response) {
		Categoria categoria = mapper.categoriaDTOToCategoria(categoriaDto);
		Categoria salvo = repository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
		.buildAndExpand(salvo.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(salvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(name = "codigo") Long id) {
		repository.delete(id);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> atualizar(@PathVariable(name = "codigo") Long codigo, @Valid @RequestBody CategoriaDTO categoriaDto, HttpServletResponse response) {
		Categoria atual = repository.findOne(codigo);
		Categoria categoria = mapper.categoriaDTOToCategoria(categoriaDto);
		BeanUtils.copyProperties(categoria, atual, "codigo");
		Categoria salvo = repository.save(atual);
		return ResponseEntity.ok(salvo);
	}
}
