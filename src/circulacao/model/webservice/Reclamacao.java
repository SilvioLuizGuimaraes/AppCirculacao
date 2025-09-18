package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Reclamacao implements Serializable
{
      
      
      private String descricaoMotivoDeNaoEntrega;
      private String idReposicao;
      private String descricaoMeioDeEntrega;
      private String observacao;
      
      
      
      private List<ExceptionEntregador> listaErro;
      
      public static Reclamacao reclamacao(String descricaoMotivoDeNaoEntrega,
                  String idReposicao, String descricaoMeioDeEntrega, String observacao)
      {
            
            Reclamacao model = new Reclamacao();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
            if(descricaoMotivoDeNaoEntrega ==null)descricaoMotivoDeNaoEntrega = "";
            model.setDescricaoMotivoDeNaoEntrega(descricaoMotivoDeNaoEntrega);
            
            if(idReposicao == null) idReposicao = "";
            model.setIdReposicao(idReposicao);
            
            if(descricaoMeioDeEntrega == null) descricaoMeioDeEntrega = "";
            model.setDescricaoMeioDeEntrega(descricaoMeioDeEntrega);
            
            if(observacao == null) observacao = "";
            model.setObservacao(observacao);
            
            
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;
      }
      public String getDescricaoMotivoDeNaoEntrega()
      {
            return descricaoMotivoDeNaoEntrega;
      }

      public void setDescricaoMotivoDeNaoEntrega(String descricaoMotivoDeNaoEntrega)
      {
            this.descricaoMotivoDeNaoEntrega = descricaoMotivoDeNaoEntrega;
      }

      public String getIdReposicao()
      {
            return idReposicao;
      }

      public void setIdReposicao(String idReposicao)
      {
            this.idReposicao = idReposicao;
      }

      public String getDescricaoMeioDeEntrega()
      {
            return descricaoMeioDeEntrega;
      }

      public void setDescricaoMeioDeEntrega(String descricaoMeioDeEntrega)
      {
            this.descricaoMeioDeEntrega = descricaoMeioDeEntrega;
      }
      
      public String getObservacao()
      {
            return observacao;
      }
      public void setObservacao(String observacao)
      {
            this.observacao = observacao;
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
