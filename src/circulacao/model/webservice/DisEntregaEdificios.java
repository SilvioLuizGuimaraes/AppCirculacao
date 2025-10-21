package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DisEntregaEdificios implements Serializable{

    private String cep;
    private String nomeDoBairro;
    private String nomeDoMunicipio;
    private String nomeDoLogradouro;
    private String numeroDoEndereco;
    private String descricaoDoEdificio;
    private Integer assinantes;
    private String nomeDoSetorDeEntrega;
    private Integer qtdJornaisContrato;
    private List<ExceptionEntregador> listaErro;
    
    public static DisEntregaEdificios disEntregaEdificios(String cep, String nomeDoBairro,String nomeDoMunicipio, 
    		String nomeDoLogradouro, String numeroDoEndereco, String descricaoDoEdificio, Integer assinantes, String nomeDoSetorDeEntrega, Integer qtdJornaisContrato
    		)
    {
    	DisEntregaEdificios model = new DisEntregaEdificios();
          List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
          ExceptionEntregador exception = new ExceptionEntregador();
          
          if(cep == null) cep = "";
          model.setCep(cep);
          
          if(nomeDoBairro ==null)nomeDoBairro = "";
          model.setNomeDoBairro(nomeDoBairro);
          
          if(nomeDoMunicipio == null) nomeDoMunicipio = "";
          model.setNomeDoMunicipio(nomeDoMunicipio);
          
          if(nomeDoLogradouro == null) nomeDoLogradouro = "";
          model.setNomeDoLogradouro(nomeDoLogradouro);
          
          if(numeroDoEndereco == null) numeroDoEndereco = "";
          model.setNumeroDoEndereco(numeroDoEndereco);
          
          if(descricaoDoEdificio ==null)descricaoDoEdificio = "";
          model.setDescricaoDoEdificio(descricaoDoEdificio);
          
          if(assinantes ==null)assinantes = 0;
          model.setAssinantes(assinantes);
          
          if(nomeDoSetorDeEntrega ==null)nomeDoSetorDeEntrega = "";
          model.setNomeDoSetorDeEntrega(nomeDoSetorDeEntrega);
          
          if(qtdJornaisContrato ==null)qtdJornaisContrato = 0;
          model.setQtdJornaisContrato(qtdJornaisContrato);
          
          exception.setMensagem("");
          exception.setMetodo("");
          exception.setCodigo(0);
          exception.setStatus(false);
          listaErro.add(exception);
          model.setListaErro(listaErro);
          
          return model;
    }
    
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getNomeDoBairro() {
		return nomeDoBairro;
	}
	public void setNomeDoBairro(String nomeDoBairro) {
		this.nomeDoBairro = nomeDoBairro;
	}
	public String getNomeDoMunicipio() {
		return nomeDoMunicipio;
	}
	public void setNomeDoMunicipio(String nomeDoMunicipio) {
		this.nomeDoMunicipio = nomeDoMunicipio;
	}
	public String getNomeDoLogradouro() {
		return nomeDoLogradouro;
	}
	public void setNomeDoLogradouro(String nomeDoLogradouro) {
		this.nomeDoLogradouro = nomeDoLogradouro;
	}
	public String getNumeroDoEndereco() {
		return numeroDoEndereco;
	}
	public void setNumeroDoEndereco(String numeroDoEndereco) {
		this.numeroDoEndereco = numeroDoEndereco;
	}
	public String getDescricaoDoEdificio() {
		return descricaoDoEdificio;
	}
	public void setDescricaoDoEdificio(String descricaoDoEdificio) {
		this.descricaoDoEdificio = descricaoDoEdificio;
	}
	public Integer getAssinantes() {
		return assinantes;
	}
	public void setAssinantes(Integer assinantes) {
		this.assinantes = assinantes;
	}
	
	public String getNomeDoSetorDeEntrega() {
		return nomeDoSetorDeEntrega;
	}

	public void setNomeDoSetorDeEntrega(String nomeDoSetorDeEntrega) {
		this.nomeDoSetorDeEntrega = nomeDoSetorDeEntrega;
	}

	public Integer getQtdJornaisContrato() {
		return qtdJornaisContrato;
	}

	public void setQtdJornaisContrato(Integer qtdJornaisContrato) {
		this.qtdJornaisContrato = qtdJornaisContrato;
	}

	public List<ExceptionEntregador> getListaErro() {
		return listaErro;
	}
	public void setListaErro(List<ExceptionEntregador> listaErro) {
		this.listaErro = listaErro;
	}
    
}
