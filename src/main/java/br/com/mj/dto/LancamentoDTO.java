package br.com.mj.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.mj.model.Tipo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LancamentoDTO implements Serializable {

	private Long id;
	private Tipo tipo;
	private String descricao;
	private BigDecimal valor;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private String observacao;
}
