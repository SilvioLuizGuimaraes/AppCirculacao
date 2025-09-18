package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ProdutosServicos implements Serializable
{
      
      private Integer codigoDoProdutoServico;
      private String descricaoDoProdutoServico;
      private List<ExceptionEntregador> listaErro;
      
      public static ProdutosServicos produtosServicos(Integer codigoDoProdutoServico, String descricaoDoProdutoServico )
      {
            ProdutosServicos model = new ProdutosServicos();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
            if(codigoDoProdutoServico == null) codigoDoProdutoServico = 0;
            model.setCodigoDoProdutoServico(codigoDoProdutoServico);
            
            if(descricaoDoProdutoServico ==null)descricaoDoProdutoServico = "";
            model.setDescricaoDoProdutoServico(descricaoDoProdutoServico);
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;
      }
      public Integer getCodigoDoProdutoServico()
      {
            return codigoDoProdutoServico;
      }
      public void setCodigoDoProdutoServico(Integer codigoDoProdutoServico)
      {
            this.codigoDoProdutoServico = codigoDoProdutoServico;
      }
      public String getDescricaoDoProdutoServico()
      {
            return descricaoDoProdutoServico;
      }
      public void setDescricaoDoProdutoServico(String descricaoDoProdutoServico)
      {
            this.descricaoDoProdutoServico = descricaoDoProdutoServico;
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
