package br.com.money.resource;

import br.com.money.http.LancamentoDTO;
import br.com.money.mapper.LancamentoMapper;
import br.com.money.model.Lancamento;
import br.com.money.model.projection.ResumoLancamento;
import br.com.money.repository.LancamentoRepository;
import br.com.money.repository.filter.LancamentoFilter;
import br.com.money.service.ILancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

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
        if (!lancamento.isPresent()) {
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

    @PutMapping("/{codigo}")
    public ResponseEntity<Lancamento> atualizar(@PathVariable(name = "codigo") Long codigo, @Valid @RequestBody LancamentoDTO lancamentoDto, HttpServletResponse response) {
        try {
            Lancamento lancamento = mapper.lancamentoDTOToLancamento(lancamentoDto);
            return ResponseEntity.ok(service.atualizar(codigo, lancamento));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
