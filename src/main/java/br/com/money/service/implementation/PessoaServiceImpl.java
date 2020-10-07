package br.com.money.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.money.mapper.EnderecoMapper;
import br.com.money.mapper.PessoaMapper;
import br.com.money.model.Endereco;
import br.com.money.model.Pessoa;
import br.com.money.model.SimNao;
import br.com.money.repository.PessoaRepository;
import br.com.money.service.IPessoaService;

@Service
public class PessoaServiceImpl implements IPessoaService {

	@Autowired
	public PessoaRepository repository;

	@Autowired
	public PessoaMapper pessoaMapper;

	@Autowired
	public EnderecoMapper enderecoMapper;

	public Pessoa atualizar(Pessoa pessoa, Long codigoPessoa) {
		Pessoa pessoaAtual = repository.findOne(codigoPessoa);

		if (!Optional.ofNullable(pessoaAtual).isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}

		BeanUtils.copyProperties(pessoa, pessoaAtual, "codigo", "endereco");

		Endereco enderecoAtual = pessoaAtual.getEndereco();
		BeanUtils.copyProperties(pessoa.getEndereco(), enderecoAtual, "codigo", "pessoa");

		return repository.save(pessoaAtual);
	}

	@Override
	public void remover(Long id) {
		repository.delete(id);
	}

	@Override
	public List<Pessoa> obterTodos() {
		return repository.findAll();
	}

	@Override
	public Pessoa criar(Pessoa pessoa) {
		Endereco endereco = pessoa.getEndereco();
		endereco.setPessoa(pessoa);
		return repository.saveAndFlush(pessoa);
	}

	@Override
	public Pessoa obterPorId(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void atualizarStatus(SimNao ativo, Long codigo) {
		Pessoa pessoaAtual = repository.findOne(codigo);
		if (!Optional.ofNullable(pessoaAtual).isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		pessoaAtual.setAtivo(ativo);
		repository.save(pessoaAtual);
	}

}