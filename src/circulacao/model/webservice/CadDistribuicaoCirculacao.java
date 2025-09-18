package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CadDistribuicaoCirculacao implements Serializable
{
      
      private Distribuicao distribuicao;
      private Circulacao circulacao;
      private ProdutosServicos produtosServicos;
      
    
      private List<ExceptionEntregador> listaErro;
      
      

      public static CadDistribuicaoCirculacao cadDistribuicaoCirculacao(Distribuicao distribuicao,
                  Circulacao circulacao, ProdutosServicos produtosServicos)
      {
            
            CadDistribuicaoCirculacao model = new CadDistribuicaoCirculacao();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
            model.setDistribuicao(distribuicao);
            model.setCirculacao(circulacao);
            model.setProdutosServicos(produtosServicos);
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;
      }
      
      public Distribuicao getDistribuicao()
      {
            return distribuicao;
      }

      public void setDistribuicao(Distribuicao distribuicao)
      {
            this.distribuicao = distribuicao;
      }

      public Circulacao getCirculacao()
      {
            return circulacao;
      }

      public void setCirculacao(Circulacao circulacao)
      {
            this.circulacao = circulacao;
      }
      
      public ProdutosServicos getProdutosServicos()
      {
            return produtosServicos;
      }

      public void setProdutosServicos(ProdutosServicos produtosServicos)
      {
            this.produtosServicos = produtosServicos;
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
