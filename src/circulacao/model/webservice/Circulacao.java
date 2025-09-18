package circulacao.model.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Circulacao implements Serializable
{
      private Integer numeroDoContrato;
      private String nomeRazaoSocial;
      private String enderecoCompleto;
      private String nomeDoSetorDeEntrega;
      private Integer codigoDoSetorDeEntrega;
      private String dataDoJornal;
      private Integer codigoDoProdutoServico;
      private Integer quantidadeDeExemplares;
      private Boolean primeiraEntrega;
      private String siglaTipoLogradouro;
      private String nomeDoLogradouro;
      private String logradouroNumeroDoEndereco;
      private Integer codigoDoLogradouro;
      

      private List<ExceptionEntregador> listaErro;
      
      public static Circulacao circulacao( Integer numeroDoContrato,
                  String nomeRazaoSocial,
                  String enderecoCompleto,
                  String nomeDoSetorDeEntrega,
                  Integer codigoDoSetorDeEntrega,
                  String dataDoJornal, 
                  Integer codigoDoProdutoServico,
                  Integer quantidadeDeExemplares,
                  Boolean primeiraEntrega,
                  String siglaTipoLogradouro,
                  String nomeDoLogradouro,
                  String logradouroNumeroDoEndereco,
                  Integer codigoDoLogradouro)
      {   
            
            Circulacao model = new Circulacao();
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
            
            if(numeroDoContrato == null) numeroDoContrato = 0;
            model.setNumeroDoContrato(numeroDoContrato);
            
            if(nomeRazaoSocial ==null)nomeRazaoSocial = "";
            model.setNomeRazaoSocial(nomeRazaoSocial);
            
            if(enderecoCompleto ==null)enderecoCompleto = "";
            model.setEnderecoCompleto(enderecoCompleto.trim());
            
            if(nomeDoSetorDeEntrega ==null)nomeDoSetorDeEntrega = "";
            model.setNomeDoSetorDeEntrega(nomeDoSetorDeEntrega);
            
            if(codigoDoSetorDeEntrega == null) codigoDoSetorDeEntrega = 0;
            model.setCodigoDoSetorDeEntrega(codigoDoSetorDeEntrega);
            
            if(dataDoJornal ==null)dataDoJornal = "";
            model.setDataDoJornal(dataDoJornal);
            
            if(codigoDoProdutoServico == null) codigoDoProdutoServico = 0;
            model.setCodigoDoProdutoServico(codigoDoProdutoServico);
            
            if(quantidadeDeExemplares == null) quantidadeDeExemplares = 0;
            model.setQuantidadeDeExemplares(quantidadeDeExemplares);
            
            
            if(primeiraEntrega == null) primeiraEntrega = false;
            model.setPrimeiraEntrega(primeiraEntrega);
            
            
            if(siglaTipoLogradouro ==null)siglaTipoLogradouro = "";
            model.setSiglaTipoLogradouro(siglaTipoLogradouro);
            
            if(nomeDoLogradouro ==null)nomeDoLogradouro = "";
            model.setNomeDoLogradouro(nomeDoLogradouro);
            
            if(logradouroNumeroDoEndereco ==null)logradouroNumeroDoEndereco = "";
            model.setLogradouroNumeroDoEndereco(logradouroNumeroDoEndereco);
            
            
            if(codigoDoLogradouro == null) codigoDoLogradouro = 0;
            model.setCodigoDoLogradouro(codigoDoLogradouro);
            
            
            exception.setMensagem("");
            exception.setMetodo("");
            exception.setCodigo(0);
            exception.setStatus(false);
            listaErro.add(exception);
            model.setListaErro(listaErro);
            
            return model;

      }
      
      public String getLogradouroNumeroDoEndereco()
      {
            return logradouroNumeroDoEndereco;
      }

      public void setLogradouroNumeroDoEndereco(String logradouroNumeroDoEndereco)
      {
            this.logradouroNumeroDoEndereco = logradouroNumeroDoEndereco;
      }

      public Integer getNumeroDoContrato()
      {
            return numeroDoContrato;
      }
      public void setNumeroDoContrato(Integer numeroDoContrato)
      {
            this.numeroDoContrato = numeroDoContrato;
      }
      public String getNomeRazaoSocial()
      {
            return nomeRazaoSocial;
      }
      public void setNomeRazaoSocial(String nomeRazaoSocial)
      {
            this.nomeRazaoSocial = nomeRazaoSocial;
      }
      public String getEnderecoCompleto()
      {
            return enderecoCompleto;
      }
      public void setEnderecoCompleto(String enderecoCompleto)
      {
            this.enderecoCompleto = enderecoCompleto;
      }
      
      public String getNomeDoSetorDeEntrega()
      {
            return nomeDoSetorDeEntrega;
      }
      public void setNomeDoSetorDeEntrega(String nomeDoSetorDeEntrega)
      {
            this.nomeDoSetorDeEntrega = nomeDoSetorDeEntrega;
      }
      public Integer getCodigoDoSetorDeEntrega()
      {
            return codigoDoSetorDeEntrega;
      }
      public void setCodigoDoSetorDeEntrega(Integer codigoDoSetorDeEntrega)
      {
            this.codigoDoSetorDeEntrega = codigoDoSetorDeEntrega;
      }
      
      public String getDataDoJornal()
      {
            return dataDoJornal;
      }
      public void setDataDoJornal(String dataDoJornal)
      {
            this.dataDoJornal = dataDoJornal;
      }
      
      public Integer getCodigoDoProdutoServico()
      {
            return codigoDoProdutoServico;
      }
      public void setCodigoDoProdutoServico(Integer codigoDoProdutoServico)
      {
            this.codigoDoProdutoServico = codigoDoProdutoServico;
      }
      
      public Integer getQuantidadeDeExemplares()
      {
            return quantidadeDeExemplares;
      }
      
      public Boolean getPrimeiraEntrega()
      {
            return primeiraEntrega;
      }

      public void setPrimeiraEntrega(Boolean primeiraEntrega)
      {
            this.primeiraEntrega = primeiraEntrega;
      }

      public void setQuantidadeDeExemplares(Integer quantidadeDeExemplares)
      {
            this.quantidadeDeExemplares = quantidadeDeExemplares;
      }
      
      public String getSiglaTipoLogradouro()
      {
            return siglaTipoLogradouro;
      }

      public void setSiglaTipoLogradouro(String siglaTipoLogradouro)
      {
            this.siglaTipoLogradouro = siglaTipoLogradouro;
      }
      
      public String getNomeDoLogradouro()
      {
            return nomeDoLogradouro;
      }
      public void setNomeDoLogradouro(String nomeDoLogradouro)
      {
            this.nomeDoLogradouro = nomeDoLogradouro;
      }
      
      public Integer getCodigoDoLogradouro()
      {
            return codigoDoLogradouro;
      }

      public void setCodigoDoLogradouro(Integer codigoDoLogradouro)
      {
            this.codigoDoLogradouro = codigoDoLogradouro;
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
