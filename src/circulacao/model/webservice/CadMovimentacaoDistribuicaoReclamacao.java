package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CadMovimentacaoDistribuicaoReclamacao implements Serializable
{
      private List<CadMovimentoCirculacao> movimentoCirculacao;
      private List<CadDistribuicaoCirculacao> distribuicaoCirculacao;
      private List<CadReclamacaoCirculacao> reclamacaoCirculacao;
     
      
      private List<ExceptionEntregador> listaErro;
      
      public static CadMovimentacaoDistribuicaoReclamacao MovimentacaoDistribuicaoReclamacao(List<CadMovimentoCirculacao> movimentoCirculacao,
                  List<CadDistribuicaoCirculacao> distribuicaoCirculacao, List<CadReclamacaoCirculacao> reclamacaoCirculacao)
      {
            
            CadMovimentacaoDistribuicaoReclamacao model = new CadMovimentacaoDistribuicaoReclamacao();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
            model.setMovimentoCirculacao(movimentoCirculacao);
            model.setDistribuicaoCirculacao(distribuicaoCirculacao);
            model.setReclamacaoCirculacao(reclamacaoCirculacao);
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;
      }
      
    
      

      public List<CadMovimentoCirculacao> getMovimentoCirculacao()
      {
            return movimentoCirculacao;
      }




      public void setMovimentoCirculacao(
                  List<CadMovimentoCirculacao> movimentoCirculacao)
      {
            this.movimentoCirculacao = movimentoCirculacao;
      }




      public List<CadDistribuicaoCirculacao> getDistribuicaoCirculacao()
      {
            return distribuicaoCirculacao;
      }




      public void setDistribuicaoCirculacao(
                  List<CadDistribuicaoCirculacao> distribuicaoCirculacao)
      {
            this.distribuicaoCirculacao = distribuicaoCirculacao;
      }




      public List<CadReclamacaoCirculacao> getReclamacaoCirculacao()
      {
            return reclamacaoCirculacao;
      }




      public void setReclamacaoCirculacao(
                  List<CadReclamacaoCirculacao> reclamacaoCirculacao)
      {
            this.reclamacaoCirculacao = reclamacaoCirculacao;
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
