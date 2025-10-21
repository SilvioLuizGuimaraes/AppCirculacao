package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DisSetorDeEntregaProdutoServicoHorarios implements Serializable
{
      private String dataDoJornal;
      private Integer codigoDoSetorDeEntrega;
      private Integer codigoDoDoProdutoServico ;
      private String horaInicioDaDistribuicao;
      private String horaTerminoDaDistribuicao;
      private String codigoDoUsuario;
      private String dataDeCadastramento;  
      private Boolean desabilitarInsersao;
      private List<ExceptionEntregador> listaErro;
  
      public static DisSetorDeEntregaProdutoServicoHorarios disSetorDeEntregaProdutoServicoHorarios
      ( String dataDoJornal,
        Integer codigoDoSetorDeEntrega,
        Integer codigoDoDoProdutoServico,
        String horaInicioDaDistribuicao,
        String horaTerminoDaDistribuicao,
        String codigoDoUsuario,
        String dataDeCadastramento, Boolean desabilitarInsersao)
      {
            DisSetorDeEntregaProdutoServicoHorarios model = new DisSetorDeEntregaProdutoServicoHorarios();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
            if(dataDoJornal ==null)dataDoJornal = "";
            model.setDataDoJornal(dataDoJornal);
            
            if(codigoDoSetorDeEntrega == null)codigoDoSetorDeEntrega = 0;
            model.setCodigoDoSetorDeEntrega(codigoDoSetorDeEntrega);
            

            if(codigoDoDoProdutoServico == null)codigoDoDoProdutoServico = 0;
            model.setCodigoDoDoProdutoServico(codigoDoDoProdutoServico);
            
            if(horaInicioDaDistribuicao ==null)horaInicioDaDistribuicao = "";
            model.setHoraInicioDaDistribuicao(horaInicioDaDistribuicao);
            
            if(horaTerminoDaDistribuicao ==null)horaTerminoDaDistribuicao = "";
            model.setHoraTerminoDaDistribuicao(horaTerminoDaDistribuicao);
            
            if(codigoDoUsuario ==null)codigoDoUsuario = "";
            model.setCodigoDoUsuario(codigoDoUsuario);
            
            if(dataDeCadastramento ==null)dataDeCadastramento = "";
            model.setDataDeCadastramento(dataDeCadastramento);
            
            
            model.setDesabilitarInsersao(desabilitarInsersao);
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;
            
      }
      public String getDataDoJornal()
      {
            return dataDoJornal;
      }
      public void setDataDoJornal(String dataDoJornal)
      {
            this.dataDoJornal = dataDoJornal;
      }
      public Integer getCodigoDoSetorDeEntrega()
      {
            return codigoDoSetorDeEntrega;
      }
      public void setCodigoDoSetorDeEntrega(int codigoDoSetorDeEntrega)
      {
            this.codigoDoSetorDeEntrega = codigoDoSetorDeEntrega;
      }
      public Integer getCodigoDoDoProdutoServico()
      {
            return codigoDoDoProdutoServico;
      }
      public void setCodigoDoDoProdutoServico(int codigoDoDoProdutoServico)
      {
            this.codigoDoDoProdutoServico = codigoDoDoProdutoServico;
      }
      public String getHoraInicioDaDistribuicao()
      {
            return horaInicioDaDistribuicao;
      }
      public void setHoraInicioDaDistribuicao(String horaInicioDaDistribuicao)
      {
            this.horaInicioDaDistribuicao = horaInicioDaDistribuicao;
      }
      public String getHoraTerminoDaDistribuicao()
      {
            return horaTerminoDaDistribuicao;
      }
      public void setHoraTerminoDaDistribuicao(String horaTerminoDaDistribuicao)
      {
            this.horaTerminoDaDistribuicao = horaTerminoDaDistribuicao;
      }
      public String getCodigoDoUsuario()
      {
            return codigoDoUsuario;
      }
      public void setCodigoDoUsuario(String codigoDoUsuario)
      {
            this.codigoDoUsuario = codigoDoUsuario;
      }
      public String getDataDeCadastramento()
      {
            return dataDeCadastramento;
      }
      public void setDataDeCadastramento(String dataDeCadastramento)
      {
            this.dataDeCadastramento = dataDeCadastramento;
      }
      
      public Boolean getDesabilitarInsersao()
      {
            return desabilitarInsersao;
      }
      public void setDesabilitarInsersao(Boolean desabilitarInsersao)
      {
            this.desabilitarInsersao = desabilitarInsersao;
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
