package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@SuppressWarnings("serial")
public class Usuario implements Serializable
{
      
      private String login;
      private String senha;
      private Integer codigoDaPessoa;
      private String mensagem;
      private Boolean validaLogin;

      List<ExceptionEntregador> listaErro;
      
       public static Usuario usuario(String login, String senha, Integer codigoDaPessoa, String mensagem,Boolean validaLogin )
       {
             
             Usuario model = new Usuario();
             List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
             ExceptionEntregador exception = new ExceptionEntregador();
             
             if(login == null) login = "";
             model.setLogin(login);
             
             if(senha ==null)senha = "";
             model.setSenha(senha);
             
             if(codigoDaPessoa == null)codigoDaPessoa = 0;
             model.setCodigoDaPessoa(codigoDaPessoa);
             
             if(mensagem ==null)mensagem = "";
             model.setMensagem(mensagem);
             
             
             model.setValidaLogin(validaLogin);
             
             exception.setMensagem("");
             exception.setMetodo("");
             exception.setCodigo(0);
             exception.setStatus(false);
             listaErro.add(exception);
             model.setListaErro(listaErro);
             
             return model;
       }
       
       
            public Boolean getValidaLogin()
      {
            return validaLogin;
      }
      
      
      public void setValidaLogin(Boolean validaLogin)
      {
            this.validaLogin = validaLogin;
      }


      public String getLogin()
      {
            return login;
      }

      public void setLogin(String login)
      {
            this.login = login;
      }

      public String getSenha()
      {
            return senha;
      }

      public void setSenha(String senha)
      {
            this.senha = senha;
      }

      public Integer getCodigoDaPessoa()
      {
            return codigoDaPessoa;
      }

      public void setCodigoDaPessoa(Integer codigoDaPessoa)
      {
            this.codigoDaPessoa = codigoDaPessoa;
      }
      
      public String getMensagem()
      {
            return mensagem;
      }
      public void setMensagem(String mensagem)
      {
            this.mensagem = mensagem;
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
