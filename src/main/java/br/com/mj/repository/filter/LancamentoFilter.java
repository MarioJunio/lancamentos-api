package br.com.mj.repository.filter;

import br.com.mj.model.Tipo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LancamentoFilter {
	
	private String descricao;
	private Tipo tipo;
}
