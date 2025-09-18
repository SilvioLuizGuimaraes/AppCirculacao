package circulacao.model.webservice;

public class ExceptionEntregador
{
      private Boolean status;
      private String mensagem;
      private Integer codigo;
      private String metodo;
      
      
      
      
      public Boolean getStatus()
      {
            return status;
      }
      public void setStatus(Boolean status)
      {
            this.status = status;
      }
      public String getMensagem()
      {
            return mensagem;
      }
      public void setMensagem(String mensagem)
      {
            this.mensagem = mensagem;
      }
      public Integer getCodigo()
      {
            return codigo;
      }
      public void setCodigo(Integer codigo)
      {
            this.codigo = codigo;
      }
      public String getMetodo()
      {
            return metodo;
      }
      public void setMetodo(String metodo)
      {
            this.metodo = metodo;
      }
      
      
}

