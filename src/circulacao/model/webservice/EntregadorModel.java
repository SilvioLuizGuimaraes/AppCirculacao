package circulacao.model.webservice;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class EntregadorModel implements Serializable
{
            private Integer numeroDoContrato;//
            private String nomeRazaoSocial;//
            private String enderecoCompleto;//
            private String descricaoMovimento;//
            private String nomeDoSetorDeEntrega;
            private Integer codigoDoSetorDeEntrega;//
            private String qtd;
            private String descricaoMotivoDeNaoEntrega;
            private String descricaoDoProdutoServico;
            private Integer codigoDoProdutoServico;//
            private String dataDoJornal;
            private String idReposicao;
            private String descricaoMeioDeEntrega;
            private Integer quantidadeDeExemplares;
            private Integer sequenciamentoDeEntrega;
            private Integer qtdJornaisContrato;
            private Integer codigoDaPessoaDistribuidor;//
            private String nomeDistribuidor;
            private Integer codigoDaPessoa;
            private Integer qtdExemplares;
            
            private List<ExceptionEntregador> listaErro;
            
      

      public static EntregadorModel entregador(Integer numeroDoContrato, 
                  String nomeRazaoSocial, 
                  String enderecoCompleto, 
                  String descricaoMovimento,
                  String nomeDoSetorDeEntrega, 
                  Integer codigoDoSetorDeEntrega, 
                  String qtd, 
                  String descricaoMotivoDeNaoEntrega,
                  String descricaoDoProdutoServico,
                  Integer codigoDoProdutoServico, 
                  String dataDoJornal, 
                  String idReposicao, 
                  String descricaoMeioDeEntrega, 
                  Integer quantidadeDeExemplares, 
                  Integer sequenciamentoDeEntrega, 
                  Integer qtdJornaisContrato, 
                  Integer codigoDaPessoaDistribuidor, 
                  String nomeDistribuidor, 
                  Integer codigoDaPessoa, 
                  Integer qtdExemplares)
      
            {
                 EntregadorModel model = new EntregadorModel();
                 List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                 ExceptionEntregador exception = new ExceptionEntregador();
                 
                 if(numeroDoContrato == null) numeroDoContrato = 0;
                 model.setNumeroDoContrato(numeroDoContrato);
                 
                 if(nomeRazaoSocial ==null)nomeRazaoSocial = "";
                 model.setNomeRazaoSocial(nomeRazaoSocial);
                 
                 if(enderecoCompleto ==null)enderecoCompleto = "";
                 model.setEnderecoCompleto(enderecoCompleto);
                 
                 if(descricaoMovimento ==null)descricaoMovimento = "";
                 model.setDescricaoMovimento(descricaoMovimento);
                 
                 if(nomeDoSetorDeEntrega ==null)nomeDoSetorDeEntrega = "";
                 model.setNomeDoSetorDeEntrega(nomeDoSetorDeEntrega);
                 
                 if(codigoDoSetorDeEntrega == null) codigoDoSetorDeEntrega = 0;
                 model.setCodigoDoSetorDeEntrega(codigoDoSetorDeEntrega);
                 
                 if(qtd ==null)qtd = "";
                 model.setQtd(qtd);
                 
                 if(descricaoMotivoDeNaoEntrega ==null)descricaoMotivoDeNaoEntrega = "";
                 model.setDescricaoMotivoDeNaoEntrega(descricaoMotivoDeNaoEntrega);
                 
                 if(descricaoDoProdutoServico ==null)descricaoDoProdutoServico = "";
                 model.setDescricaoDoProdutoServico(descricaoDoProdutoServico);
                 
                 if(codigoDoProdutoServico == null) codigoDoProdutoServico = 0;
                 model.setCodigoDoProdutoServico(codigoDoProdutoServico);
                 
                 if(dataDoJornal ==null)dataDoJornal = "";
                 model.setDataDoJornal(dataDoJornal);
                 
                 if(idReposicao ==null)idReposicao = "";
                 model.setIdReposicao(idReposicao);
                 
                 if(descricaoMeioDeEntrega ==null)descricaoMeioDeEntrega = "";
                 model.setDescricaoMeioDeEntrega(descricaoMeioDeEntrega);
                 
                 if(quantidadeDeExemplares == null) quantidadeDeExemplares = 0;
                 model.setQuantidadeDeExemplares(quantidadeDeExemplares);
                 
                 if(sequenciamentoDeEntrega == null) sequenciamentoDeEntrega = 0;
                 model.setSequenciamentoDeEntrega(sequenciamentoDeEntrega);
                 
                 if(qtdJornaisContrato == null) qtdJornaisContrato = 0;
                 model.setQtdJornaisContrato(qtdJornaisContrato);
                 
                 if(codigoDaPessoaDistribuidor == null) codigoDaPessoaDistribuidor = 0;
                 model.setCodigoDaPessoaDistribuidor(codigoDaPessoaDistribuidor);
                 
                 if(nomeDistribuidor ==null)nomeDistribuidor = "";
                 model.setNomeDistribuidor(nomeDistribuidor);
                 
                 if(codigoDaPessoa == null) codigoDaPessoa = 0;
                 model.setCodigoDaPessoa(codigoDaPessoa);
                 
                 if(qtdExemplares == null) qtdExemplares = 0;
                 model.setQtdExemplares(qtdExemplares);
                 
                 
                 exception.setMensagem("");
                 exception.setMetodo("");
                 exception.setCodigo(0);
                 exception.setStatus(false);
                 listaErro.add(exception);
                 model.setListaErro(listaErro);
            
                 return model;
            }
      
            
            public EntregadorModel getSetoresModel(Integer codigoDoSetorDeEntrega, String nomeDoSetorDeEntrega, String qtd/*Integer codigoDoProdutoServico,String descricaoDoProdutoServico*/)
            {
                        EntregadorModel model = new EntregadorModel();
                        
                        model.setCodigoDoSetorDeEntrega(codigoDoSetorDeEntrega);
                        model.setNomeDoSetorDeEntrega(nomeDoSetorDeEntrega);
                        model.setQtd(qtd);
                        //model.setCodigoDoProdutoServico(codigoDoProdutoServico);
                        //model.setDescricaoDoProdutoServico(descricaoDoProdutoServico);
                        
                        return model;
            }
            
            public EntregadorModel getMovimentacaoModel(Integer numeroDoContrato, String nomeRazaoSocial, String enderecoCompleto, String descricaoMovimento)
            {
                        EntregadorModel model = new EntregadorModel();
                        
                        if(numeroDoContrato == null) numeroDoContrato = 0;
                        model.setNumeroDoContrato(numeroDoContrato);
                        
                        if(nomeRazaoSocial ==null)nomeRazaoSocial = "";
                        model.setNomeRazaoSocial(nomeRazaoSocial);
                        
                        if(enderecoCompleto ==null)enderecoCompleto = "";
                        model.setEnderecoCompleto(enderecoCompleto);
                        
                        if(descricaoMovimento ==null)descricaoMovimento = "";
                        model.setDescricaoMovimento(descricaoMovimento);
                        
                        return model;
            }
      
            
            public EntregadorModel  getReclamacaoModel(Integer numeroDoContrato, String nomeRazaoSocial , String descricaoMotivoDeNaoEntrega, String enderecoCompleto,
                        String descricaoDoProdutoServico, String idReposicao, String descricaoMeioDeEntrega, Integer quantidadeDeExemplares)
            {
                        EntregadorModel model = new EntregadorModel();
                        
                        model.setNumeroDoContrato(numeroDoContrato);
                        model.setNomeRazaoSocial(nomeRazaoSocial);
                        model.setDescricaoMotivoDeNaoEntrega(descricaoMotivoDeNaoEntrega);
                        model.setEnderecoCompleto(enderecoCompleto);
                        model.setDescricaoDoProdutoServico(descricaoDoProdutoServico);
                        model.setIdReposicao(idReposicao);
                        model.setDescricaoMeioDeEntrega(descricaoMeioDeEntrega);
                        model.setQuantidadeDeExemplares(quantidadeDeExemplares);
                        
                        return model;
            }
            
            public EntregadorModel getDistribuicaoModel(Integer sequenciamentoDeEntrega, String nomeRazaoSocial, Integer qtdJornaisContrato,
                        String enderecoCompleto, String descricaoDoProdutoServico, String nomeDoSetorDeEntrega, String distribuidor,
                        Integer numeroDoContrato)
            {
                        EntregadorModel model = new EntregadorModel();
                        
                        model.setSequenciamentoDeEntrega(sequenciamentoDeEntrega);
                        model.setNomeRazaoSocial(nomeRazaoSocial);
                        model.setQtdJornaisContrato(qtdJornaisContrato);
                        model.setEnderecoCompleto(enderecoCompleto);
                        model.setDescricaoDoProdutoServico(descricaoDoProdutoServico);
                        model.setNomeDoSetorDeEntrega(nomeDoSetorDeEntrega);
                        model.setNomeDistribuidor(distribuidor);
                        model.setNumeroDoContrato(numeroDoContrato);
                        
                        return model;
            }
            
            public EntregadorModel getProdutosServicosModel(Integer codigoDoProdutoServico, String descricaoDoProdutoServico )
            {
                        EntregadorModel model = new EntregadorModel();
                        
                        model.setCodigoDoProdutoServico(codigoDoProdutoServico);
                        model.setDescricaoDoProdutoServico(descricaoDoProdutoServico);
                        
                        return model;
            }
            
            public EntregadorModel getDistribuidorModel(Integer codigoDaPessoa, String nomeRazaoSocial )
            {
                        EntregadorModel model = new EntregadorModel();
                        
                        model.setCodigoDaPessoaDistribuidor(codigoDaPessoa);
                        model.setNomeRazaoSocial(nomeRazaoSocial);
                        
                        return model;
            }
            
            
            public EntregadorModel getPessoaModel(String nomeRazaoSocial, Integer codigoDaPessoa )
            {
                        EntregadorModel model = new EntregadorModel();
                        
                        model.setNomeRazaoSocial(nomeRazaoSocial);
                        model.setCodigoDaPessoa(codigoDaPessoa);
                        
                        return model;
            }
            
            public String getNomeDistribuidor() {
                  return nomeDistribuidor;
            }

            public void setNomeDistribuidor(String nomeDistribuidor) {
                  this.nomeDistribuidor = nomeDistribuidor;
            }

            public Integer getNumeroDoContrato() {
                  return numeroDoContrato;
            }
            public void setNumeroDoContrato(Integer numeroDoContrato) {
                  this.numeroDoContrato = numeroDoContrato;
            }
            public String getNomeRazaoSocial() {
                  return nomeRazaoSocial;
            }
            public void setNomeRazaoSocial(String nomeRazaoSocial) {
                  this.nomeRazaoSocial = nomeRazaoSocial;
            }
            public String getEnderecoCompleto() {
                  return enderecoCompleto;
            }
            public void setEnderecoCompleto(String enderecoCompleto) {
                  this.enderecoCompleto = enderecoCompleto;
            }
            public String getDescricaoMovimento() {
                  return descricaoMovimento;
            }
            public void setDescricaoMovimento(String descricaoMovimento) {
                  this.descricaoMovimento = descricaoMovimento;
            }
            public String getNomeDoSetorDeEntrega() {
                  return nomeDoSetorDeEntrega;
            }
            public void setNomeDoSetorDeEntrega(String nomeDoSetorDeEntrega) {
                  this.nomeDoSetorDeEntrega = nomeDoSetorDeEntrega;
            }
            public Integer getCodigoDoSetorDeEntrega() {
                  return codigoDoSetorDeEntrega;
            }
            public void setCodigoDoSetorDeEntrega(Integer codigoDoSetorDeEntrega) {
                  this.codigoDoSetorDeEntrega = codigoDoSetorDeEntrega;
            }
            public String getQtd() {
                  return qtd;
            }
            public void setQtd(String qtd) {
                  this.qtd = qtd;
            }
            public String getDescricaoMotivoDeNaoEntrega() {
                  return descricaoMotivoDeNaoEntrega;
            }
            public void setDescricaoMotivoDeNaoEntrega(String descricaoMotivoDeNaoEntrega) {
                  this.descricaoMotivoDeNaoEntrega = descricaoMotivoDeNaoEntrega;
            }
            public String getDescricaoDoProdutoServico() {
                  return descricaoDoProdutoServico;
            }
            public void setDescricaoDoProdutoServico(String descricaoDoProdutoServico) {
                  this.descricaoDoProdutoServico = descricaoDoProdutoServico;
            }
            public Integer getCodigoDoProdutoServico() {
                  return codigoDoProdutoServico;
            }
            public void setCodigoDoProdutoServico(Integer codigoDoProdutoServico) {
                  this.codigoDoProdutoServico = codigoDoProdutoServico;
            }
            public String getDataDoJornal() {
                  return dataDoJornal;
            }
            public void setDataDoJornal(String dataDoJornal) {
                  this.dataDoJornal = dataDoJornal;
            }
            public String getIdReposicao() {
                  return idReposicao;
            }
            public void setIdReposicao(String idReposicao) {
                  this.idReposicao = idReposicao;
            }
            public String getDescricaoMeioDeEntrega() {
                  return descricaoMeioDeEntrega;
            }
            public void setDescricaoMeioDeEntrega(String descricaoMeioDeEntrega) {
                  this.descricaoMeioDeEntrega = descricaoMeioDeEntrega;
            }
            public Integer getQuantidadeDeExemplares() {
                  return quantidadeDeExemplares;
            }
            public void setQuantidadeDeExemplares(Integer quantidadeDeExemplares) {
                  this.quantidadeDeExemplares = quantidadeDeExemplares;
            }
            public Integer getSequenciamentoDeEntrega() {
                  return sequenciamentoDeEntrega;
            }
            public void setSequenciamentoDeEntrega(Integer sequenciamentoDeEntrega) {
                  this.sequenciamentoDeEntrega = sequenciamentoDeEntrega;
            }
            public Integer getQtdJornaisContrato() {
                  return qtdJornaisContrato;
            }
            public void setQtdJornaisContrato(Integer qtdJornaisContrato) {
                  this.qtdJornaisContrato = qtdJornaisContrato;
            }
            public Integer getCodigoDaPessoaDistribuidor() {
                  return codigoDaPessoaDistribuidor;
            }
            public void setCodigoDaPessoaDistribuidor(Integer codigoDaPessoaDistribuidor) {
                  this.codigoDaPessoaDistribuidor = codigoDaPessoaDistribuidor;
            }
            public Integer getCodigoDaPessoa() {
                  return codigoDaPessoa;
            }

            public void setCodigoDaPessoa(Integer codigoDaPessoa) {
                  this.codigoDaPessoa = codigoDaPessoa;
            }

            public Integer getQtdExemplares()
            {
                  return qtdExemplares;
            }
            public void setQtdExemplares(Integer qtdExemplares)
            {
                  this.qtdExemplares = qtdExemplares;
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

