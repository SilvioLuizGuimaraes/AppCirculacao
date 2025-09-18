package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SetorDeEntrega implements Serializable
{
      
      private String qtd;
      
      private Circulacao movimentacao;
      
      private List<ExceptionEntregador> listaErro;

 
      public static SetorDeEntrega setor(String qtd, 
                                         Circulacao movimentacao)
      {
           
            SetorDeEntrega model = new SetorDeEntrega();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
             if(qtd ==null)qtd = "";
            model.setQtd(qtd);
            
            model.setMovimentacao(movimentacao);
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;
            
      }
      
      public String getQtd()
      {
            return qtd;
      }

      public void setQtd(String qtd)
      {
            this.qtd = qtd;
      }
      
      public Circulacao getMovimentacao()
      {
            return movimentacao;
      }

      public void setMovimentacao(Circulacao movimentacao)
      {
            this.movimentacao = movimentacao;
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
