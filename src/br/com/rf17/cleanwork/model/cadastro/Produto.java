package br.com.rf17.cleanwork.model.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "produtos", schema = "cleanwork")
@SequenceGenerator(name = "SEQ_PRODUTO", initialValue = 1, allocationSize = 1, sequenceName = "SEQ_PRODUTO")
public class Produto {

	@Id
	@Column(name = "id_produto")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUTO")
	private Long id_produto;

	@Column(name = "descricao", length = 150, nullable = false)
	private String descricao;

	@Column(name = "tipo", nullable = false)
	private int tipo;

	@Column(name = "preco_custo", precision = 12, scale = 3, nullable = false)
	private double preco_custo;

	@Column(name = "preco_custo_indireto", precision = 12, scale = 3, nullable = false)
	private double preco_custo_indireto;

	@Column(name = "taxa_lucro", precision = 12, scale = 3)
	private double taxa_lucro;

	@Column(name = "preco_venda", precision = 12, scale = 3)
	private double preco_venda;

	public Produto() {
		super();
		this.tipo = 1;
	}

	public Long getId_produto() {
		return id_produto;
	}

	public void setId_produto(Long id_produto) {
		this.id_produto = id_produto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * 1 - Produto Final 2 - Insumo
	 * 
	 * @return
	 */
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getPreco_custo() {
		return preco_custo;
	}

	public void setPreco_custo(double preco_custo) {
		this.preco_custo = preco_custo;
	}

	public double getPreco_venda() {
		return preco_venda;
	}

	public void setPreco_venda(double preco_venda) {
		this.preco_venda = preco_venda;
	}

	public double getTaxa_lucro() {
		return taxa_lucro;
	}

	public void setTaxa_lucro(double taxa_lucro) {
		this.taxa_lucro = taxa_lucro;
	}

	public double getPreco_custo_indireto() {
		return preco_custo_indireto;
	}

	public void setPreco_custo_indireto(double preco_custo_indireto) {
		this.preco_custo_indireto = preco_custo_indireto;
	}

	public Double getPreco_custo_total() {
		return preco_custo_indireto + preco_custo;
	}

	public String getTipoDescricao() {
		if (this.tipo == 1) {
			return "Produto Final";
		} else if (this.tipo == 2) {
			return "Matéria-Prima";
		} else {
			return "Outros";
		}
	}

}
