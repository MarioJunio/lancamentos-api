package br.com.mj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mj.exception.ServiceException;
import br.com.mj.model.Lancamento;
import br.com.mj.repository.ILancamentoRepository;

@Service
@Transactional
public class LancamentoService {

	@Autowired
	private ILancamentoRepository lancamentoRepository;

	public List<Lancamento> buscarTodos() {
		return lancamentoRepository.findAll();
	}

	public Lancamento novo(Lancamento lancamento) {
		return lancamentoRepository.save(lancamento);
	}

	public Lancamento atualizar(Long id, Lancamento lancamento) throws ServiceException {
		checarLancamento(id);

		lancamento.setId(id);

		return lancamentoRepository.save(lancamento);
	}

	public void remover(Long idLancamento) {
		checarLancamento(idLancamento);
		
		lancamentoRepository.deleteById(idLancamento);
	}

	private void checarLancamento(Long idLancamento) {
		Optional<Lancamento> lancamentoOriginal = lancamentoRepository.findById(idLancamento);

		if (!lancamentoOriginal.isPresent()) {
			throw new ServiceException("lancamento.inexistente");
		}
	}
}
