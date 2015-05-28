package br.com.rf17.cleanwork.model.vendas;

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
@Table(name = "vendas", schema = "cleanwork")
@SequenceGenerator(name="SEQ_VENDA", initialValue=1, allocationSize=1, sequenceName="SEQ_VENDA")
public class Venda {

	@Id
	@Column(name = "id_venda")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_VENDA")
	private Long id_venda;

	@Column(name = "situacao", nullable = false)
	private int situacao;
	
	@Column(name = "data_cadastro", columnDefinition="date", nullable = false)
	private Date data_cadastro;
	
	@Column(name = "data_emissao", columnDefinition="date")
	private Date data_emissao;

	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_parceiro", nullable = false, foreignKey = @ForeignKey(name = "FK_VENDA_PARCEIRO"))
	private Parceiro cliente;
	
	@Column(name = "cond_recbto", nullable = false)
	private int cond_recbto;//A prazo, A vista e A Combinar
	
	@Column(name = "forma_recbto", length = 50, nullable = false)
	private String forma_recbto;
    
	@Column(name = "valor_total", precision = 12, scale = 2, nullable = false)
	private double valor_total;
	
	@OneToMany(mappedBy = "venda", targetEntity = VendaItem.class , fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)	
	private List<VendaItem> vendaItems;

	public Venda() {
		super();
		this.data_cadastro = new Date();
		this.situacao = 1;//Sem inicia como pendente		
	}
	
	
	public Long getId_venda() {
		return id_venda;
	}

	public void setId_venda(Long id_venda) {
		this.id_venda = id_venda;
	}

	/**
	 * 1-Pendente
	 * 2-Emitido
	 * 3-Cancelado
	 * 
	 * @return
	 */
	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Date getData_emissao() {
		return data_emissao;
	}

	public void setData_emissao(Date data_emissao) {
		this.data_emissao = data_emissao;
	}

	public Parceiro getCliente() {
		return cliente;
	}

	public void setCliente(Parceiro cliente) {
		this.cliente = cliente;
	}

	public int getCond_recbto() {
		return cond_recbto;
	}

	public void setCond_recbto(int cond_recbto) {
		this.cond_recbto = cond_recbto;
	}

	public String getForma_recbto() {
		return forma_recbto;
	}

	public void setForma_recbto(String forma_recbto) {
		this.forma_recbto = forma_recbto;
	}

	public double getValor_total() {
		return valor_total;
	}

	public void setValor_total(double valor_total) {
		this.valor_total = valor_total;
	}

	public List<VendaItem> getVendaItems() {
		return vendaItems;
	}

	public void setVendaItems(List<VendaItem> vendaItems) {
		this.vendaItems = vendaItems;
	}
	
	/**
	 * Retorna a descricao da situaçao da venda
	 * 
	 * @return
	 */
	public String getSituacaoDescricao() {
		switch (this.situacao) {			
			case 1:
				return "Pendente";
			case 2:
				return "Emitido";
			case 3: 
				return "Cancelado";	
			default:
				return "Com Erro";			
		}
	}
	
	/**
	 * Retorna a Cor da Situação da venda
	 * 
	 * @return
	 */
	public String getSituacaoCor(){		
		switch (this.situacao) {			
			case 1:
				return "#E0BB26";//Amarelo
				//return "#3399CC";//Azul
				//return "#A99CE2";//Roxo
			case 2:
				return "#629B58";//Verde				
			case 3: 			
				return "#D15B47";//Vermelho
			default:
				return "";			
		}
	}	
	
}
