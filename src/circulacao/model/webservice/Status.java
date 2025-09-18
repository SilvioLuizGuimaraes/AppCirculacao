package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class Status implements Serializable{

	private Integer codigo;
	private String dataHora;
	private String status;
	private List<ExceptionEntregador> listaErro;
	
	
	public static Status setStatus(Integer codigo, String dataHora , String status )
    {
		  Status model = new Status();
          List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
          ExceptionEntregador exception = new ExceptionEntregador();
          
          if(codigo == null) codigo = 0;
          model.setCodigo(codigo);
          
          if(dataHora ==null)dataHora = "";
          model.setDataHora(dataHora);
            
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
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getDataHora() {
		return dataHora;
	}
	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
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
