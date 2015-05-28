package br.com.rf17.cleanwork.model.pcp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.rf17.cleanwork.model.cadastro.Produto;

@Entity
@Table(name = "producao", schema = "cleanwork")
@SequenceGenerator(name = "SEQ_PRODUCAO", initialValue = 1, allocationSize = 1, sequenceName = "SEQ_PRODUCAO")
public class Producao {

	@Id
	@Column(name = "id_producao")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCAO")
	private Long id_producao;

	@Column(name = "data", columnDefinition="date", nullable = false)
	private Date data;

	@Column(name = "situacao", nullable = false)
	private int situacao;

	@Column(name = "qtd", precision = 12, scale = 3, nullable = false)
	private double qtd;

	@ManyToOne
	@JoinColumn(name = "id_produto", referencedColumnName = "id_produto")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "id_fichatecnica", referencedColumnName = "id_fichatecnica")
	private FichaTecnica fichaTecnica;

	public Long getId_producao() {
		return id_producao;
	}

	public void setId_producao(Long id_producao) {
		this.id_producao = id_producao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public double getQtd() {
		return qtd;
	}

	public void setQtd(double qtd) {
		this.qtd = qtd;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public FichaTecnica getFichaTecnica() {
		return fichaTecnica;
	}

	public void setFichaTecnica(FichaTecnica fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}

	/**
	 * Retorna a descrição da Situação
	 * 
	 * @return String
	 */
	public String getSituacaoDescricao() {
		if (this.situacao == 1) {
			return "Pendente";
		} else if (this.situacao == 2) {
			return "Em Produção";
		} else if (this.situacao == 3) {
			return "Concluída";
		} else {
			return "Cancelada";
		}
	}

	public String getSituacaoCor() {
		switch (this.situacao) {
			case 1:// Pendente
				return "#E0BB26";// Amarelo
			case 2:// Em Produção
				return "#629B58";// Verde
			case 3:// Concluida
				return "#3399CC";// Azul
			case 4:// Cancelada
				return "#D15B47";// Vermelho
			default:
				return "";
		}

	}

}
