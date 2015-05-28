package br.com.rf17.cleanwork.model.cadastro;

import java.io.Serializable;

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

@Entity
@Table(name = "enderecos", schema = "cleanwork")
@SequenceGenerator(name="SEQ_ENDERECO", initialValue=1, allocationSize=1, sequenceName="SEQ_ENDERECO")
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_endereco")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_ENDERECO")
	private Long id_endereco;

	@ManyToOne
	@JoinColumn(name = "id_parceiro", referencedColumnName = "id_parceiro", nullable = false, foreignKey = @ForeignKey(name = "FK_ENDERECO_PARCEIRO"))
	private Parceiro parceiro;

	@Column(name = "tipo", nullable = false)
	private int tipo;

	@Column(name = "endereco", length = 60, nullable = false)
	private String endereco;

	@Column(name = "num_endereco", length = 10, nullable = false)
	private int num_endereco;

	@Column(name = "bairro", length = 40, nullable = false)
	private String bairro;

	@Column(name = "municipio", length = 60, nullable = false)
	private String municipio;

	@Column(name = "estado", length = 2, nullable = false)
	private String estado;

	@Column(name = "cep", length = 8, nullable = false)
	private String cep;

	@Column(name = "complemento", length = 40)
	private String complemento;

	
	public Endereco() {
		super();
		this.tipo = 1;
	}

	
	public Long getId_endereco() {
		return id_endereco;
	}

	public void setId_endereco(Long id_endereco) {
		this.id_endereco = id_endereco;
	}

	public Parceiro getParceiro() {
		return parceiro;
	}

	public void setParceiro(Parceiro parceiro) {
		this.parceiro = parceiro;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getNum_endereco() {
		return num_endereco;
	}

	public void setNum_endereco(int num_endereco) {
		this.num_endereco = num_endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoDescricao() {
		if(this.tipo ==  1){
			return "Principal";
	    }else if(this.tipo ==  2){
	    	return "Entrega"; 	
	    }else if(this.tipo ==  3){
	    	return "Cobrança";
	    }else{
	    	return "Outros";
	    }
	}
	
}
