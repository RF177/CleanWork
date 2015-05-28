package br.com.rf17.cleanwork.model.cadastro;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "parceiros", schema = "cleanwork")
@SequenceGenerator(name="SEQ_PARCEIRO", initialValue=1, allocationSize=1, sequenceName="SEQ_PARCEIRO")
public class Parceiro {

	@Id
	@Column(name = "id_parceiro")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_PARCEIRO")
	private Long id_parceiro;
	
	@Column(name = "nome", length = 60, nullable = false)
	private String nome;
	
	@Column(name = "tipo_pessoa", nullable = false)
	private int tipo_pessoa;
	
	@Column(name = "inscricao", length = 14, nullable = false)
	private String inscricao;
	
	@Column(name = "fornecedor", nullable = false)
	private boolean fornecedor;
	
	@Column(name = "cliente", nullable = false)
	private boolean cliente;
	
	@OneToMany(mappedBy = "parceiro", targetEntity = Endereco.class,  cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)    	  
	private List<Endereco> enderecos;
	
	@OneToMany(mappedBy = "parceiro", targetEntity = Contato.class,  cascade = CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)    	  
	private List<Contato> contatos;

	
	public Parceiro() {
		super();
		this.tipo_pessoa = 1;
		this.contatos = new ArrayList<Contato>();
		this.enderecos = new ArrayList<Endereco>();
	}
	

	public Long getId_parceiro() {
		return id_parceiro;
	}

	public void setId_parceiro(Long id_parceiro) {
		this.id_parceiro = id_parceiro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTipo_pessoa() {
		return tipo_pessoa;
	}

	public void setTipo_pessoa(int tipo_pessoa) {
		this.tipo_pessoa = tipo_pessoa;
	}

	/**
	 * 1 - Física;
	 * 2 - Juridica;
	 * 
	 * @return
	 */
	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

	public boolean isFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(boolean fornecedor) {
		this.fornecedor = fornecedor;
	}

	public boolean isCliente() {
		return cliente;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}	
	
	public Endereco getEnderecoPrincipal() {
		if(this.enderecos != null){
			for(Endereco endereco : this.enderecos){
				if(endereco.getTipo() == 1){
					return endereco;
				}
			}
		}
		return null;
	}
	
	/**
	 * Busca na lista de contatos, o telefone principal
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getTelefonePrincipal() throws Exception{
		if(this.contatos != null){
			for(Contato contato : this.contatos){
				if (contato.getForma() == 1 && contato.getTipo() ==1) {
					return contato.getContato();
				}
			}
		}
		
		return null;
	}
	
}
