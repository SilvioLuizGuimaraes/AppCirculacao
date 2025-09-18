package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class IDistribuicao implements Serializable{

	private Integer codigoMotivoDeNaoEntrega;
	private String descricaoMotivoDeNaoEntrega;
	private String dataHoraEntrega;
	private String dataDoJornal;
	private Integer numeroDoContrato;
	private Integer codigoDoSetorDeEntrega;
	private Integer codigoDoProdutoServico;
	private String status;
	
	private List<ExceptionEntregador> listaErro;
	
	public static IDistribuicao setIDistribuicao(Integer codigoMotivoDeNaoEntrega,String descricaoMotivoDeNaoEntrega, String dataHoraEntrega,
			String dataDoJornal, Integer numeroDoContrato, Integer codigoDoSetorDeEntrega,Integer codigoDoProdutoServico , String status )
    {
		  IDistribuicao model = new IDistribuicao();
          List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
          ExceptionEntregador exception = new ExceptionEntregador();
          
          if(codigoMotivoDeNaoEntrega == null) codigoMotivoDeNaoEntrega = 0;
          model.setCodigoMotivoDeNaoEntrega(codigoMotivoDeNaoEntrega);
          
          if(descricaoMotivoDeNaoEntrega == null)descricaoMotivoDeNaoEntrega = "";
          model.setDescricaoMotivoDeNaoEntrega(descricaoMotivoDeNaoEntrega);
          
          if(dataHoraEntrega ==null)dataHoraEntrega = "";
          model.setDataHoraEntrega(dataHoraEntrega);
          
          if(dataDoJornal ==null)dataDoJornal = "";
          model.setDataDoJornal(dataDoJornal);
          
          if(numeroDoContrato == null) numeroDoContrato = 0;
          model.setNumeroDoContrato(numeroDoContrato);
          
          if(codigoDoSetorDeEntrega == null) codigoDoSetorDeEntrega = 0;
          model.setCodigoDoSetorDeEntrega(codigoDoSetorDeEntrega);
          
          if(codigoDoProdutoServico == null) codigoDoProdutoServico = 0;
          model.setCodigoDoProdutoServico(codigoDoProdutoServico);
          
          if(status ==null)status = "";
          model.setStatus(status);
          
      
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


	public String getDataHoraEntrega() {
		return dataHoraEntrega;
	}

	public void setDataHoraEntrega(String dataHoraEntrega) {
		this.dataHoraEntrega = dataHoraEntrega;
	}
	
	public String getDataDoJornal() {
		return dataDoJornal;
	}

	public void setDataDoJornal(String dataDoJornal) {
		this.dataDoJornal = dataDoJornal;
	}


	public Integer getNumeroDoContrato() {
		return numeroDoContrato;
	}

	public void setNumeroDoContrato(Integer numeroDoContrato) {
		this.numeroDoContrato = numeroDoContrato;
	}

	public Integer getCodigoDoSetorDeEntrega() {
		return codigoDoSetorDeEntrega;
	}

	public void setCodigoDoSetorDeEntrega(Integer codigoDoSetorDeEntrega) {
		this.codigoDoSetorDeEntrega = codigoDoSetorDeEntrega;
	}

	public Integer getCodigoDoProdutoServico() {
		return codigoDoProdutoServico;
	}

	public void setCodigoDoProdutoServico(Integer codigoDoProdutoServico) {
		this.codigoDoProdutoServico = codigoDoProdutoServico;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public List<ExceptionEntregador> getListaErro() {
		return listaErro;
	}

	public void setListaErro(List<ExceptionEntregador> listaErro) {
		this.listaErro = listaErro;
	}
	
}
