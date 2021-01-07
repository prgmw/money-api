package br.com.money.service.implementation;

import br.com.money.exception.PessoaInexistenteOuInativaException;
import br.com.money.model.Categoria;
import br.com.money.model.Lancamento;
import br.com.money.model.Pessoa;
import br.com.money.model.SimNao;
import br.com.money.repository.CategoriaRepository;
import br.com.money.repository.LancamentoRepository;
import br.com.money.repository.PessoaRepository;
import br.com.money.service.ILancamentoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoServiceImpl implements ILancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Lancamento atualizar(Long codigo, Lancamento lancamento) {
        Lancamento atual = lancamentoRepository.findOne(codigo);
        if (atual == null) {
            throw new IllegalArgumentException();
        }
        BeanUtils.copyProperties(lancamento, atual, "codigo");
        return lancamentoRepository.save(atual);
    }

    @Override
    public Lancamento criar(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
        if (pessoa == null || pessoa.getAtivo() == SimNao.N) {
            throw new PessoaInexistenteOuInativaException("Pessoa inexistente ou inativa");
        }
        Categoria categoria = categoriaRepository.findOne(lancamento.getCategoria().getCodigo());
        lancamento.setPessoa(pessoa);
        lancamento.setCategoria(categoria);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    public List<Lancamento> obterTodos() {
        return lancamentoRepository.findAll();
    }

    @Override
    public Lancamento obterPorId(Long id) {
        return lancamentoRepository.findOne(id);
    }

}
