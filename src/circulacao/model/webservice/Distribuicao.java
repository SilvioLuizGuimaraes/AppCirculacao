package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Distribuicao implements Serializable
{
      
      private Integer codigoDaPessoaDistribuidor;
      private Integer sequenciamentoDeEntrega;
      private Integer sequenciamento;
      private Integer codigoMotivoDeNaoEntrega;
      private String dataHoraEntrega;
      
      private List<ExceptionEntregador> listaErro;
      
      
      public static Distribuicao distribuicao(Integer codigoDaPessoaDistribuidor,
                  Integer sequenciamentoDeEntrega, Integer sequenciamento, Integer codigoMotivoDeNaoEntrega,
                  String dataHoraEntrega)
      {
            Distribuicao model = new Distribuicao();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
                        
            if(codigoDaPessoaDistribuidor == null) codigoDaPessoaDistribuidor = 0;
            model.setCodigoDaPessoaDistribuidor(codigoDaPessoaDistribuidor);
            
            
            if(sequenciamentoDeEntrega == null) sequenciamentoDeEntrega = 0;
            model.setSequenciamentoDeEntrega(sequenciamentoDeEntrega);
            
            if(sequenciamento == null) sequenciamento = 0;
            model.setSequenciamento(sequenciamento);
            
            if(codigoMotivoDeNaoEntrega == null) codigoMotivoDeNaoEntrega = 0;
            model.setCodigoMotivoDeNaoEntrega(codigoMotivoDeNaoEntrega);
            
            if(dataHoraEntrega ==null)dataHoraEntrega = "";
            model.setDataHoraEntrega(dataHoraEntrega);
            

            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;
      }
       
      public Integer getCodigoDaPessoaDistribuidor()
      {
            return codigoDaPessoaDistribuidor;
      }

      public void setCodigoDaPessoaDistribuidor(Integer codigoDaPessoaDistribuidor)
      {
            this.codigoDaPessoaDistribuidor = codigoDaPessoaDistribuidor;
      }

      public Integer getSequenciamentoDeEntrega()
      {
            return sequenciamentoDeEntrega;
      }

      public void setSequenciamentoDeEntrega(Integer sequenciamentoDeEntrega)
      {
            this.sequenciamentoDeEntrega = sequenciamentoDeEntrega;
      }
      
      public Integer getSequenciamento()
      {
            return sequenciamento;
      }

      public void setSequenciamento(Integer sequenciamento)
      {
            this.sequenciamento = sequenciamento;
      }
      
      public Integer getCodigoMotivoDeNaoEntrega() {
  		return codigoMotivoDeNaoEntrega;
  	  }

  	  public void setCodigoMotivoDeNaoEntrega(Integer codigoMotivoDeNaoEntrega) {
  		this.codigoMotivoDeNaoEntrega = codigoMotivoDeNaoEntrega;
  	  }

  	  public String getDataHoraEntrega() {
  		return dataHoraEntrega;
  	  }

  	  public void setDataHoraEntrega(String dataHoraEntrega) {
  		this.dataHoraEntrega = dataHoraEntrega;
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
