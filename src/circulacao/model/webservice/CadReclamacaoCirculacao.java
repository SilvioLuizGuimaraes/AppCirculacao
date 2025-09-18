package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CadReclamacaoCirculacao implements Serializable
{
      
      private Reclamacao reclamacao;
      private Circulacao circulacao; 
      
      private List<ExceptionEntregador> listaErro;
      
      
      public static CadReclamacaoCirculacao reclamacaoCirculacao(Reclamacao reclamacao,Circulacao circulacao)
      {
            CadReclamacaoCirculacao model = new CadReclamacaoCirculacao();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
            
            model.setReclamacao(reclamacao);
            model.setCirculacao(circulacao);
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
           
            
            return model;
      }
      
      public Reclamacao getReclamacao()
      {
            return reclamacao;
      }
      public void setReclamacao(Reclamacao reclamacao)
      {
            this.reclamacao = reclamacao;
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
