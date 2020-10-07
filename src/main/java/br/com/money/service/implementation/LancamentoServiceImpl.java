package br.com.money.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.money.model.Categoria;
import br.com.money.model.Lancamento;
import br.com.money.model.Pessoa;
import br.com.money.repository.CategoriaRepository;
import br.com.money.repository.LancamentoRepository;
import br.com.money.repository.PessoaRepository;
import br.com.money.service.ILancamentoService;

public class LancamentoServiceImpl implements ILancamentoService {
	
	@Autowired
	private LancamentoRepository LancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public Lancamento criar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		Categoria categoria = categoriaRepository.findOne(lancamento.getCategoria().getCodigo());
		lancamento.setPessoa(pessoa);
		lancamento.setCategoria(categoria);
		return LancamentoRepository.save(lancamento);
	}

	@Override
	public List<Lancamento> obterTodos() {
		return LancamentoRepository.findAll();
	}

	@Override
	public Lancamento obterPorId(Long id) {
		return LancamentoRepository.findOne(id);
	}

}
