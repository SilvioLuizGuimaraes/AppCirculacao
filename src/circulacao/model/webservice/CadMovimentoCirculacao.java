package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CadMovimentoCirculacao implements Serializable
{
      private Movimentacao movimentacao;
      private Circulacao circulacao;
      
      private List<ExceptionEntregador> listaErro;
      
      

      public static CadMovimentoCirculacao cadMomentacaoCirculacao(Movimentacao movimentacao, Circulacao circulacao)
      {
            
            CadMovimentoCirculacao model = new CadMovimentoCirculacao();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
            model.setMovimentacao(movimentacao);
            model.setCirculacao(circulacao);
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;
      }
      
      public Movimentacao getMovimentacao()
      {
            return movimentacao;
      }

      public void setMovimentacao(Movimentacao movimentacao)
      {
            this.movimentacao = movimentacao;
      }

      public Circulacao getCirculacao()
      {
            return circulacao;
      }

      public void setCirculacao(Circulacao circulacao)
      {
            this.circulacao = circulacao;
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
