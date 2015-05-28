package br.com.rf17.cleanwork.model.vendas;

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
@Table(name = "vendaitem", schema = "cleanwork")
@SequenceGenerator(name="SEQ_VENDAITEM", initialValue=1, allocationSize=1, sequenceName="SEQ_VENDAITEM")
public class VendaItem {

	@Id
	@Column(name = "id_vendaitem")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_VENDAITEM")
	private Long id_vendaitem;

	@ManyToOne
	@JoinColumn(name = "id_venda", referencedColumnName = "id_venda", nullable = false, foreignKey = @ForeignKey(name = "FK_VENDAITEM_VENDA"))
	private Venda venda;

	@ManyToOne
	@JoinColumn(name = "id_produto", referencedColumnName = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "FK_VENDAITEM_PRODUTO"))
	private Produto produto;

	@Column(name = "qtd", precision = 12, scale = 3, nullable = false)
	private double qtd;
	
	@Column(name = "vl_unitario", precision = 12, scale = 4, nullable = false)
	private double vl_unitario;
	
	@Column(name = "vl_total", precision = 12, scale = 2, nullable = false)
	private double vl_total;//Equivalente à quantidade x vl_unitario

	
	public Long getId_vendaitem() {
		return id_vendaitem;
	}

	public void setId_vendaitem(Long id_vendaitem) {
		this.id_vendaitem = id_vendaitem;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
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
