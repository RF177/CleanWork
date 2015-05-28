package br.com.rf17.cleanwork.model.pcp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.rf17.cleanwork.model.cadastro.Produto;

@Entity
@Table(name = "fichatecnica_insumo", schema = "cleanwork")
@SequenceGenerator(name = "SEQ_FICHATECNICA_INSUMO", initialValue = 1, allocationSize = 1, sequenceName = "SEQ_FICHATECNICA_INSUMO")
public class FichaTecnicaInsumo {

	@Id
	@Column(name = "id_insumo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FICHATECNICA_INSUMO")
	private Long id_insumo;

	@ManyToOne
	@JoinColumn(name = "id_fichatecnica", referencedColumnName = "id_fichatecnica", nullable = false, foreignKey = @ForeignKey(name = "FK_INSUMO_FICHA"))
	private FichaTecnica fichaTecnica;

	@ManyToOne
	@JoinColumn(name = "id_produto", referencedColumnName = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "FK_INSUMO_PRODUTO"))
	private Produto produto;

	@Column(name = "qtd", precision = 12, scale = 3, nullable = false)
	private double qtd;

	@Column(name = "vl_custo", precision = 12, scale = 2, nullable = false)
	private double vl_custo;

	@Column(name = "vl_total", precision = 12, scale = 2, nullable = false)
	private double vl_total;

	public Long getId_insumo() {
		return id_insumo;
	}

	public void setId_insumo(Long id_insumo) {
		this.id_insumo = id_insumo;
	}

	public FichaTecnica getFichaTecnica() {
		return fichaTecnica;
	}

	public void setFichaTecnica(FichaTecnica fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public double getQtd() {
		return qtd;
	}

	public void setQtd(double qtd) {
		this.qtd = qtd;
	}

	public double getVl_custo() {
		return vl_custo;
	}

	public void setVl_custo(double vl_custo) {
		this.vl_custo = vl_custo;
	}

	public double getVl_total() {
		return vl_total;
	}

	public void setVl_total(double vl_total) {
		this.vl_total = vl_total;
	}

}
