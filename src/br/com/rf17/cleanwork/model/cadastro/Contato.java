package br.com.rf17.cleanwork.model.cadastro;

import java.text.ParseException;

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

import br.com.rf17.cleanwork.utils.StringFunctions;

@Entity
@Table(name = "contatos", schema = "cleanwork")
@SequenceGenerator(name="SEQ_CONTATO", initialValue=1, allocationSize=1, sequenceName="SEQ_CONTATO")
public class Contato {

	@Id
	@Column(name = "id_contato")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_CONTATO")	
	private Long id_contato;
	
	@ManyToOne
	@JoinColumn(name = "id_parceiro", referencedColumnName = "id_parceiro", nullable = false, foreignKey = @ForeignKey(name = "FK_CONTATO_PARCEIRO"))
	private Parceiro parceiro;	
	
	@Column(name = "tipo", nullable = false)
	private int tipo;
	
	@Column(name = "forma", nullable = false)
	private int forma;	
	
	@Column(name = "contato", length = 50, nullable = false)
	private String contato;
	
	@Column(name = "complemento", length = 40)
	private String complemento;

	
	public Contato() {
		super();
		this.tipo = 1;		
		this.forma = 1;
	}

	
	public Long getId_contato() {
		return id_contato;
	}

	public void setId_contato(Long id_contato) {
		this.id_contato = id_contato;
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

	public int getForma() {
		return forma;
	}

	public void setForma(int forma) {
		this.forma = forma;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	/**
	 * Formata o ddd e telefone, para aparecer na dataTable de contatos
	 * 
	 * @return
	 * @throws ParseException
	 */
	public String getTelefoneFormatado() throws ParseException {
		if(this.forma ==  1){
			if(this.contato != null){
				return StringFunctions.formatString(this.contato, "(##) ####-#######");
			}
		}  
        return null;
	}
	
	public String getTipoDescricao() {
		if(this.tipo ==  1){
			return "Principal";
	    }else if(this.tipo ==  2){
	    	return "Comercial"; 	
	    }else if(this.tipo ==  3){
	    	return "Financeiro";
	    }else{
	    	return "Outros";
	    }
	}
	
	public String getFormaDescricao() {
		if(this.forma ==  1){
			return "Telefone";
	    }else if(this.forma ==  2){
	    	return "Email"; 		    
	    }else{
	    	return "Outra";
	    }
	}
	
}
