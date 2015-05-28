package br.com.rf17.cleanwork.model.compras;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.rf17.cleanwork.model.cadastro.Parceiro;

@Entity
@Table(name = "entradas", schema = "cleanwork")
@SequenceGenerator(name="SEQ_ENTRADA", initialValue=1, allocationSize=1, sequenceName="SEQ_ENTRADA")
public class Entrada {

	@Id
	@Column(name = "id_entrada")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_ENTRADA")
	private Long id_entrada;

	@Column(name = "numero", nullable = false)
	private Integer numero;

	@Column(name = "data_entrada", columnDefinition="date", nullable = false)
	private Date data_entrada;

	@ManyToOne
	@JoinColumn(name = "id_fornecedor", referencedColumnName = "id_parceiro", nullable = false, foreignKey = @ForeignKey(name = "FK_ENTRADA_PARCEIRO"))
	private Parceiro fornecedor;

    @Column(name = "valor_frete", precision = 12, scale = 2, nullable = false)
	private double valor_frete; 	
	
	@Column(name = "valor_total", precision = 12, scale = 2, nullable = false)
	private double valor_total;

	@Column(name = "cond_pagto", nullable = false)
	private int cond_pagto;//A prazo, A vista e A Combinar
	
	@OneToMany(mappedBy = "entrada", targetEntity = EntradaItem.class , fetch = FetchType.LAZY,  cascade = CascadeType.ALL , orphanRemoval=true)	
	private List<EntradaItem> entradaItems;

	public Long getId_entrada() {
		return id_entrada;
	}

	public void setId_entrada(Long id_entrada) {
		this.id_entrada = id_entrada;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Date getData_entrada() {
		return data_entrada;
	}

	public void setData_entrada(Date data_entrada) {
		this.data_entrada = data_entrada;
	}

	public Parceiro getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Parceiro fornecedor) {
		this.fornecedor = fornecedor;
	}

	public double getValor_frete() {
		return valor_frete;
	}

	public void setValor_frete(double valor_frete) {
		this.valor_frete = valor_frete;
	}

	public double getValor_total() {
		return valor_total;
	}

	public void setValor_total(double valor_total) {
		this.valor_total = valor_total;
	}

	public int getCond_pagto() {
		return cond_pagto;
	}

	public void setCond_pagto(int cond_pagto) {
		this.cond_pagto = cond_pagto;
	}

	public List<EntradaItem> getEntradaItems() {
		return entradaItems;
	}

	public void setEntradaItems(List<EntradaItem> entradaItems) {
		this.entradaItems = entradaItems;
	}
	
}
