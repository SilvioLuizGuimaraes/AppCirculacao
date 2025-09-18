package circulacao.model.webservice;

import java.util.ArrayList;
import java.util.List;

public class ConjuntoEdificiosSetor
{
      private String dataDoJornal;
      private Integer codigoDoSetorDeEntrega;
      private String nomeDoSetorDeEntrega; 
      private String descricaoDoConjunto; 
      private String descricaoDoEdificio; 
      private String enderecoCompleto;
      private Integer codigoDoLogradouro;
      private String nomeDoLogradouro;
      private String nomeRazaoSocial; 
      private Integer qtdJornaisContrato; 
      private String andar; 
      private String apartamento;  
      
      private List<ExceptionEntregador> listaErro;
      
      
      public static ConjuntoEdificiosSetor conjuntoEdificiosSetor(String dataDoJornal
      ,Integer codigoDoSetorDeEntrega
      , String nomeDoSetorDeEntrega 
      , String descricaoDoConjunto 
      , String descricaoDoEdificio 
      , String enderecoCompleto
      , Integer codigoDoLogradouro
      , String nomeDoLogradouro
      , String nomeRazaoSocial 
      , Integer qtdJornaisContrato 
      , String andar 
      , String apartamento){
            
            ConjuntoEdificiosSetor model = new ConjuntoEdificiosSetor();
            
            List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
            ExceptionEntregador exception = new ExceptionEntregador();
                 
            if(dataDoJornal ==null)dataDoJornal = "";
            model.setDataDoJornal(dataDoJornal);
            
            if(codigoDoSetorDeEntrega == null) codigoDoSetorDeEntrega = 0;
            model.setCodigoDoSetorDeEntrega(codigoDoSetorDeEntrega);
            
            if(nomeDoSetorDeEntrega ==null)nomeDoSetorDeEntrega = "";
            model.setNomeDoSetorDeEntrega(nomeDoSetorDeEntrega);
            
            
            if(descricaoDoConjunto ==null)descricaoDoConjunto = "";
            model.setDescricaoDoConjunto(descricaoDoConjunto);
            
            if(descricaoDoEdificio ==null)descricaoDoEdificio = "";
            model.setDescricaoDoEdificio(descricaoDoEdificio);
            
            if(enderecoCompleto ==null)enderecoCompleto = "";
            model.setEnderecoCompleto(enderecoCompleto);
            
            if(codigoDoLogradouro == null) codigoDoLogradouro = 0;
            model.setCodigoDoLogradouro(codigoDoLogradouro);
            
            if(nomeDoLogradouro ==null)nomeDoLogradouro = "";
            model.setNomeDoLogradouro(nomeDoLogradouro);
            
            
            if(qtdJornaisContrato == null) qtdJornaisContrato = 0;
            model.setQtdJornaisContrato(qtdJornaisContrato);
            
            if(nomeRazaoSocial ==null)nomeRazaoSocial = "";
            model.setNomeRazaoSocial(nomeRazaoSocial);
            
            if(andar ==null)andar = "";
            model.setAndar(andar);
            
            if(apartamento ==null)apartamento = "";
            model.setApartamento(apartamento);
            
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
      
      public void setCodigoDoSetorDeEntrega(Integer codigoDoSetorDeEntrega)
      {
            this.codigoDoSetorDeEntrega = codigoDoSetorDeEntrega;
      }
      public String getNomeDoSetorDeEntrega()
      {
            return nomeDoSetorDeEntrega;
      }

      public void setNomeDoSetorDeEntrega(String nomeDoSetorDeEntrega)
      {
            this.nomeDoSetorDeEntrega = nomeDoSetorDeEntrega;
      }

      public String getDescricaoDoConjunto()
      {
            return descricaoDoConjunto;
      }

      public void setDescricaoDoConjunto(String descricaoDoConjunto)
      {
            this.descricaoDoConjunto = descricaoDoConjunto;
      }

      public String getDescricaoDoEdificio()
      {
            return descricaoDoEdificio;
      }

      public void setDescricaoDoEdificio(String descricaoDoEdificio)
      {
            this.descricaoDoEdificio = descricaoDoEdificio;
      }

      public String getEnderecoCompleto()
      {
            return enderecoCompleto;
      }

      public void setEnderecoCompleto(String enderecoCompleto)
      {
            this.enderecoCompleto = enderecoCompleto;
      }
      
      public Integer getCodigoDoLogradouro()
      {
            return codigoDoLogradouro;
      }

      public void setCodigoDoLogradouro(Integer codigoDoLogradouro)
      {
            this.codigoDoLogradouro = codigoDoLogradouro;
      }
      
      public String getNomeDoLogradouro()
      {
            return nomeDoLogradouro;
      }

      public void setNomeDoLogradouro(String nomeDoLogradouro)
      {
            this.nomeDoLogradouro = nomeDoLogradouro;
      }

      public String getNomeRazaoSocial()
      {
            return nomeRazaoSocial;
      }

      public void setNomeRazaoSocial(String nomeRazaoSocial)
      {
            this.nomeRazaoSocial = nomeRazaoSocial;
      }

      public Integer getQtdJornaisContrato()
      {
            return qtdJornaisContrato;
      }

      public void setQtdJornaisContrato(Integer qtdJornaisContrato)
      {
            this.qtdJornaisContrato = qtdJornaisContrato;
      }

      public String getAndar()
      {
            return andar;
      }

      public void setAndar(String andar)
      {
            this.andar = andar;
      }

      public String getApartamento()
      {
            return apartamento;
      }

      public void setApartamento(String apartamento)
      {
            this.apartamento = apartamento;
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
