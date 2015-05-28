package br.com.rf17.cleanwork.model.pcp;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.rf17.cleanwork.model.cadastro.Produto;

@Entity
@Table(name = "fichatecnica", schema = "cleanwork")
@SequenceGenerator(name = "SEQ_FICHATECNICA", initialValue = 1, allocationSize = 1, sequenceName = "SEQ_FICHATECNICA")
public class FichaTecnica {

	@Id
	@Column(name = "id_fichatecnica")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FICHATECNICA")
	private Long id_fichatecnica;

	@Column(name = "codigo", nullable = false)
	private Integer codigo;

	@Column(name = "descricao", length = 150, nullable = false)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "id_produto", referencedColumnName = "id_produto")
	private Produto produto;

	@Column(name = "custo_total", precision = 12, scale = 3, nullable = false)
	private double custo_total;

	@OneToMany(mappedBy = "fichaTecnica", targetEntity = FichaTecnicaInsumo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FichaTecnicaInsumo> insumos;

	@Column(name = "custo_maodeobra", precision = 12, scale = 3)
	private double custo_maodeobra;

	@Column(name = "custo_ggf", precision = 12, scale = 3)
	private double custo_ggf;

	@Column(name = "custo_outros", precision = 12, scale = 3)
	private double custo_outros;

	@Transient
	private double custo_materiais;

	public Long getId_fichatecnica() {
		return id_fichatecnica;
	}

	public void setId_fichatecnica(Long id_fichatecnica) {
		this.id_fichatecnica = id_fichatecnica;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getCusto_total() {
		return custo_total;
	}

	public void setCusto_total(double custo_total) {
		this.custo_total = custo_total;
	}

	public double getCusto_maodeobra() {
		return custo_maodeobra;
	}

	public void setCusto_maodeobra(double custo_maodeobra) {
		this.custo_maodeobra = custo_maodeobra;
	}

	/**
	 * Gastos gerais de fabricacao
	 * 
	 * @return
	 */
	public double getCusto_ggf() {
		return custo_ggf;
	}

	public void setCusto_ggf(double custo_ggf) {
		this.custo_ggf = custo_ggf;
	}

	public double getCusto_outros() {
		return custo_outros;
	}

	public void setCusto_outros(double custo_outros) {
		this.custo_outros = custo_outros;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public List<FichaTecnicaInsumo> getInsumos() {
		return insumos;
	}

	public void setInsumos(List<FichaTecnicaInsumo> insumos) {
		this.insumos = insumos;
	}

	public double getCusto_materiais() {
		return custo_materiais;
	}

	public void setCusto_materiais(double custo_materiais) {
		this.custo_materiais = custo_materiais;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
