package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movimentacao implements Serializable
{
      
      private Integer codigoMovimento;
      private String descricaoMovimento; 
      private String movimento; 
      
      private List<ExceptionEntregador> listaErro;
      
      public static Movimentacao movimentacao(Integer codigoMovimento, String descricaoMovimento, String movimento )
      {
            
            Movimentacao model = new Movimentacao();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
            if(codigoMovimento == null) codigoMovimento = 0;
            model.setCodigoMovimento(codigoMovimento);
            
            if(descricaoMovimento ==null)descricaoMovimento = "";
            model.setDescricaoMovimento(descricaoMovimento);
            
            if(movimento ==null)movimento = "";
            model.setMovimento(movimento);
            
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;
            
      }
      
      
      public Integer getCodigoMovimento()
      {
            return codigoMovimento;
      }
      public void setCodigoMovimento(Integer codigoMovimento)
      {
            this.codigoMovimento = codigoMovimento;
      }
      public String getDescricaoMovimento()
      {
            return descricaoMovimento;
      }
      public void setDescricaoMovimento(String descricaoMovimento)
      {
            this.descricaoMovimento = descricaoMovimento;
      }
      public String getMovimento()
      {
            return movimento;
      }

      public void setMovimento(String movimento)
      {
            this.movimento = movimento;
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
