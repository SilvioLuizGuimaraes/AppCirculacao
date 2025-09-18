package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CadMotivoDeNaoEntrega implements Serializable{

	private Integer codigoMotivoDeNaoEntrega;
    private String descricaoMotivoDeNaoEntrega;
    private Integer quantidadeMaximaDeOcorrencias;
    private Integer pesoDaOcorrencia;
    private Double multaDeEntrega;
    private Boolean idAtivo;
    private String idApp;
    private List<ExceptionEntregador> listaErro;
    
    public static CadMotivoDeNaoEntrega cadMotivoDeNaoEntrega(Integer codigoMotivoDeNaoEntrega, String descricaoMotivoDeNaoEntrega,
    		Integer quantidadeMaximaDeOcorrencias, Integer pesoDaOcorrencia, Double multaDeEntrega, Boolean idAtivo, String idApp)
    {
    	CadMotivoDeNaoEntrega model = new CadMotivoDeNaoEntrega();
          List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
          ExceptionEntregador exception = new ExceptionEntregador();
          
          if(codigoMotivoDeNaoEntrega == null) codigoMotivoDeNaoEntrega = 0;
          model.setCodigoMotivoDeNaoEntrega(codigoMotivoDeNaoEntrega);
          
          if(descricaoMotivoDeNaoEntrega ==null)descricaoMotivoDeNaoEntrega = "";
          model.setDescricaoMotivoDeNaoEntrega(descricaoMotivoDeNaoEntrega);
          
          if(quantidadeMaximaDeOcorrencias == null) quantidadeMaximaDeOcorrencias = 0;
          model.setQuantidadeMaximaDeOcorrencias(quantidadeMaximaDeOcorrencias);
          
          if(pesoDaOcorrencia == null) pesoDaOcorrencia = 0;
          model.setPesoDaOcorrencia(pesoDaOcorrencia);
          
          if(multaDeEntrega == null) multaDeEntrega = 0.0;
          model.setMultaDeEntrega(multaDeEntrega);
          
          if(idAtivo ==null)idAtivo = false;
          model.setIdAtivo(idAtivo);
          
          if(idApp ==null)idApp = "";
          model.setIdApp(idApp);
          
          exception.setMensagem("");
          exception.setMetodo("");
          exception.setCodigo(0);
          exception.setStatus(false);
          listaErro.add(exception);
          model.setListaErro(listaErro);
          
          return model;
    }
    
    public Integer getCodigoMotivoDeNaoEntrega() {
		return codigoMotivoDeNaoEntrega;
	}


	public void setCodigoMotivoDeNaoEntrega(Integer codigoMotivoDeNaoEntrega) {
		this.codigoMotivoDeNaoEntrega = codigoMotivoDeNaoEntrega;
	}


	public String getDescricaoMotivoDeNaoEntrega() {
		return descricaoMotivoDeNaoEntrega;
	}


	public void setDescricaoMotivoDeNaoEntrega(String descricaoMotivoDeNaoEntrega) {
		this.descricaoMotivoDeNaoEntrega = descricaoMotivoDeNaoEntrega;
	}


	public Integer getQuantidadeMaximaDeOcorrencias() {
		return quantidadeMaximaDeOcorrencias;
	}


	public void setQuantidadeMaximaDeOcorrencias(Integer quantidadeMaximaDeOcorrencias) {
		this.quantidadeMaximaDeOcorrencias = quantidadeMaximaDeOcorrencias;
	}


	public Integer getPesoDaOcorrencia() {
		return pesoDaOcorrencia;
	}


	public void setPesoDaOcorrencia(Integer pesoDaOcorrencia) {
		this.pesoDaOcorrencia = pesoDaOcorrencia;
	}


	public Double getMultaDeEntrega() {
		return multaDeEntrega;
	}


	public void setMultaDeEntrega(Double multaDeEntrega) {
		this.multaDeEntrega = multaDeEntrega;
	}


	public Boolean getIdAtivo() {
		return idAtivo;
	}


	public void setIdAtivo(Boolean idAtivo) {
		this.idAtivo = idAtivo;
	}


	public String getIdApp() {
		return idApp;
	}


	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}


	public List<ExceptionEntregador> getListaErro()
    {
          return listaErro;
    }

    public void setListaErro(List<ExceptionEntregador> listaErro)
    {
          this.listaErro = listaErro;
    }
}
