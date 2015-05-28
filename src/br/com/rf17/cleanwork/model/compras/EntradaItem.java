package br.com.rf17.cleanwork.model.compras;

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
@Table(name = "entradaitem", schema = "cleanwork")
@SequenceGenerator(name="SEQ_ENTRADAITEM", initialValue=1, allocationSize=1, sequenceName="SEQ_ENTRADAITEM")
public class EntradaItem {

	@Id
	@Column(name = "id_entradaitem")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_ENTRADAITEM")
	private Long id_entradaitem;

	@ManyToOne
	@JoinColumn(name = "id_entrada", referencedColumnName = "id_entrada", nullable = false, foreignKey = @ForeignKey(name = "FK_ENTRADAITEM_ENTRADA"))
	private Entrada entrada;

	@ManyToOne
	@JoinColumn(name = "id_produto", referencedColumnName = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "FK_ENTRADAITEM_PRODUTO"))
	private Produto produto;

	@Column(name = "qtd", precision = 12, scale = 3, nullable = false)
	private double qtd;

	@Column(name = "vl_unitario", precision = 12, scale = 4, nullable = false)
	private double vl_unitario;

	@Column(name = "vl_total", precision = 12, scale = 2, nullable = false)
	private double vl_total;// Equivalente à quantidade x vl_unitario

	public Long getId_entradaitem() {
		return id_entradaitem;
	}

	public void setId_entradaitem(Long id_entradaitem) {
		this.id_entradaitem = id_entradaitem;
	}

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
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

	public double getVl_unitario() {
		return vl_unitario;
	}

	public void setVl_unitario(double vl_unitario) {
		this.vl_unitario = vl_unitario;
	}

	public double getVl_total() {
		return vl_total;
	}

	public void setVl_total(double vl_total) {
		this.vl_total = vl_total;
	}

}
