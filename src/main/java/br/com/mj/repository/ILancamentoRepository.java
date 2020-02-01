package br.com.mj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mj.model.Lancamento;

public interface ILancamentoRepository extends JpaRepository<Lancamento, Long> {

}
