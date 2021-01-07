package br.com.money.resource;

import br.com.money.mapper.PessoaMapper;
import br.com.money.model.Lancamento;
import br.com.money.model.Pessoa;
import br.com.money.model.SimNao;
import br.com.money.repository.filter.LancamentoFilter;
import br.com.money.repository.filter.PessoaFilter;
import br.com.money.service.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    public PessoaMapper pessoaMapper;
    @Autowired
    private IPessoaService pessoaService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public ResponseEntity<Page<Pessoa>> pesquisar(PessoaFilter filter, Pageable pageable) {
        return ResponseEntity.ok(pessoaService.pesquisar(filter, pageable));
    }

//    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
//    public ResponseEntity<List<Pessoa>> listarTodos() {
//        return ResponseEntity.ok(pessoaService.obterTodos());
//    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable(name = "codigo") Long id, HttpServletResponse response) {
        Optional<Pessoa> pessoa = Optional.ofNullable(pessoaService.obterPorId(id));
        if (!pessoa.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pessoa.get());
    }

//	@PostMapping
//	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
//	public ResponseEntity<?> criar(@Valid @RequestBody PessoaDTO pessoaDto, HttpServletResponse response) {
//		Pessoa salvo = pessoaService.criar(pessoaMapper.pessoaDTOToPessoa(pessoaDto));
//
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
//				.buildAndExpand(salvo.getCodigo()).toUri();
//		response.setHeader("Location", uri.toASCIIString());
//
//		return ResponseEntity.created(uri).body(salvo);
//	}

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable(name = "codigo") Long id) {
        pessoaService.remover(id);
    }

//	@PutMapping("/{codigo}")
//	public ResponseEntity<Pessoa> atualizar(@PathVariable(name = "codigo") Long codigo,
//			@Valid @RequestBody PessoaDTO pessoaDto, HttpServletResponse response) {
//		Pessoa pessoaModificado = pessoaMapper.pessoaDTOToPessoa(pessoaDto);
//		return ResponseEntity.ok(pessoaService.atualizar(pessoaModificado, codigo));
//	}

    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarAtivo(@PathVariable(name = "codigo") Long codigo,
                               @RequestBody SimNao ativo, HttpServletResponse response) {
        pessoaService.atualizarStatus(ativo, codigo);
    }
}
