 package circulacao.control.webservice;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import circulacao.control.seguranca.Log;
import circulacao.control.util.Utilitarios;
import circulacao.dao.webservice.EntregadorDAO;
import circulacao.model.webservice.CadDistribuicaoCirculacao;
import circulacao.model.webservice.CadMotivoDeNaoEntrega;
import circulacao.model.webservice.CadMovimentacaoDistribuicaoReclamacao;
import circulacao.model.webservice.CadMovimentoCirculacao;
import circulacao.model.webservice.CadReclamacaoCirculacao;
import circulacao.model.webservice.Circulacao;
import circulacao.model.webservice.ConjuntoEdificiosSetor;
import circulacao.model.webservice.DisEntregaEdificios;
import circulacao.model.webservice.DisSetorDeEntregaProdutoServicoHorarios;
import circulacao.model.webservice.ExceptionEntregador;
import circulacao.model.webservice.IDistribuicao;
import circulacao.model.webservice.ProdutosServicos;
import circulacao.model.webservice.Status;
import circulacao.model.webservice.Usuario;


@SuppressWarnings("unused")
@Path("/circulacao")
public class Entregador
{
private String aplicacao = "gestoron";
      
      private Log log = new Log();
      
      
      
      
      @GET
      @Produces("application/json; charset=UTF-8")
      @Path("/enviaToken/{token}")
      public String getToken(@PathParam("token") String token)
      {
            String tokenEnviado = "";
            try
            {
                  if(token != null)
                  {
                     tokenEnviado = token;
                     System.out.print(tokenEnviado);
                  }
                  
            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);

                  tokenEnviado = null;
                  List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                  ExceptionEntregador  exception = new ExceptionEntregador();
                  exception.setMensagem(e.getMessage());
                  exception.setMetodo("getToken");
                  exception.setCodigo(e.hashCode());
                  exception.setStatus(true);
                  listaErro.add(exception);
                  
                  return tokenEnviado;
            }
            
            return  tokenEnviado;
        
     }
      

      @POST
      @Produces("application/json; charset=UTF-8")
      @Consumes("application/*")    
      @Path("/usuario")
      public Usuario setUsuario(Usuario d) 
      {
            String dados = "teste";
         return null;   
      }
      @GET
      @Produces("application/json; charset=UTF-8")
      @Path("/consultarHorario/{dataDoJornal}/{codigoDoSetorDeEntrega}/{codigoDoProdutoServico}")
      public DisSetorDeEntregaProdutoServicoHorarios getConsultarHorario(@PathParam("dataDoJornal") String dataDoJornal, @PathParam("codigoDoSetorDeEntrega") Integer codigoDoSetorDeEntrega,
      @PathParam("codigoDoProdutoServico") Integer codigoDoProdutoServico)
      {
    	  
           EntregadorDAO  dao = new EntregadorDAO();
           DisSetorDeEntregaProdutoServicoHorarios horario = null;
           
            
            try
            {
            	horario = dao.getVerificarHoraInicioDaDistribuicao(dataDoJornal, codigoDoSetorDeEntrega, codigoDoProdutoServico);
            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);

                  DisSetorDeEntregaProdutoServicoHorarios horarioErro = new DisSetorDeEntregaProdutoServicoHorarios();
                  List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                  ExceptionEntregador  exception = new ExceptionEntregador();
                  exception.setMensagem(e.getMessage());
                  exception.setMetodo("getDisSetorDeEntregaProdutoServicoHorarios");
                  exception.setCodigo(e.hashCode());
                  exception.setStatus(true);
                  listaErro.add(exception);
                  
                  horarioErro.setListaErro(listaErro);
                  
                  return horario;
            }
            
            return horario; 
      
      
      }
      
		@POST
	    @Produces("application/json; charset=UTF-8")
	    @Consumes("application/json; charset=UTF-8")    
	    @Path("/verificarHoraPorEntregador")
		public DisSetorDeEntregaProdutoServicoHorarios getVerificarHoraPorEntregador(DisSetorDeEntregaProdutoServicoHorarios d) {

			boolean retorno = false;
			EntregadorDAO dao = new EntregadorDAO();
			DisSetorDeEntregaProdutoServicoHorarios model = null;
			try {
				model = dao.getVerificarHoraPorEntregador(d);
				
				if (model == null || model.equals("")) {
					retorno = dao.insertInicioEntregaHorario(d);
					if (retorno) {
						model = dao.getVerificarHoraPorEntregador(d);
					}

				}

			} catch (Exception e) {
				geraLog(e.getMessage(), e);

			}

			return model;
		}
		
		@POST
	    @Produces("application/json; charset=UTF-8")
	    @Consumes("application/json; charset=UTF-8")    
	    @Path("/updateMotivoDeNaoEntregaDistribuicao")
		public Status setIDistribuicaoUpdateMotivoDeNaoEntrega(IDistribuicao d) 
		{
			 Boolean IsValidar = false;
			 List<IDistribuicao> lista = new ArrayList<IDistribuicao>();
			 Status model = new Status();
			EntregadorDAO dao = new EntregadorDAO();
			String data = Utilitarios.getDate();

			try {
				
				IsValidar = dao.setIDistribuicaoUpdateMotivoDeNaoEntrega(d);
				if (IsValidar) {

					model = Status.setStatus(1, data, "Sucesso no processo.");

				} else {
					
					model = Status.setStatus(2, data, "Erro no processo.");
				}
				
				
			} catch (Exception e) {
				geraLog(e.getMessage(), e);
				
				Status loginErro = new Status();
				List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
				ExceptionEntregador exception = new ExceptionEntregador();
				exception.setMensagem(e.getMessage());
				exception.setMetodo("getMotivoDeNaoEntrega");
				exception.setCodigo(e.hashCode());
				exception.setStatus(true);
				listaErro.add(exception);

				loginErro.setListaErro(listaErro);
				return model;

			}

			return model;
	   }

      @POST
      @Produces("application/json; charset=UTF-8")
      @Consumes("application/json; charset=UTF-8")    
      @Path("/entrega")
      public Boolean setEntrega(DisSetorDeEntregaProdutoServicoHorarios d)
      {
            Boolean entrega = false;
            EntregadorDAO dao = new EntregadorDAO();
            DisSetorDeEntregaProdutoServicoHorarios horario;
            String dataHoraPatrao = "1900-01-01 00:00:00.0";
            try
            {
                  horario = dao.getVerificarHoraInicioDaDistribuicao( d.getDataDoJornal(), 
                              d.getCodigoDoSetorDeEntrega(), d.getCodigoDoDoProdutoServico());
                  
                  if (horario.getHoraInicioDaDistribuicao().equals(dataHoraPatrao)
                              && horario.getHoraTerminoDaDistribuicao().equals(dataHoraPatrao))
                  {
                        // inicia a inser��o da Entrega/Produto Servi�o - Hor�rios
                        entrega = dao.setInicioEntregaHorario(d.getDataDoJornal(),d.getCodigoDoSetorDeEntrega(),
                                    d.getCodigoDoDoProdutoServico(),d.getCodigoDoUsuario());
                  }
                  else if (horario.getHoraInicioDaDistribuicao().equals( dataHoraPatrao)
                              && !horario.getHoraTerminoDaDistribuicao().equals(dataHoraPatrao))
                  {
                     // inicia a inser��o da Entrega/Produto Servi�o - Hor�rios
                        entrega = dao.setInicioEntregaHorario(d.getDataDoJornal(),d.getCodigoDoSetorDeEntrega(),
                                    d.getCodigoDoDoProdutoServico(),d.getCodigoDoUsuario());
                  }
                  else if (!horario.getHoraInicioDaDistribuicao().equals( dataHoraPatrao)
                                    && horario.getHoraTerminoDaDistribuicao().equals(dataHoraPatrao))
                  {
                        // termina a inser��o da Entrega/Produto Servi�o - Hor�rios
                        entrega = dao.setTerminoEntregaHorario( d.getDataDoJornal(), d.getCodigoDoSetorDeEntrega(),
                                    d.getCodigoDoDoProdutoServico(), d.getCodigoDoUsuario());
                  }
                  else
                  { 
                        // N�o ser� feita a inser��o da Entrega/Produto Servi�o - Hor�rios, pois j� foi realizada.
                        entrega = true;
                  }
                  
            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);
                  
            }
            
            return entrega;
      }
      @GET
      @Produces("application/json; charset=UTF-8")
      @Path("/login/{usuario}/{senha}")
      public Usuario getLogin(@PathParam("usuario") String usuario, @PathParam("senha") String senha)
      {
            Usuario login = new Usuario();
           EntregadorDAO  dao = new EntregadorDAO();
           
            
            try
            {
                  login = dao.getValidaLogin(usuario, senha);
            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);

                  Usuario loginErro = new Usuario();
                  List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                  ExceptionEntregador  exception = new ExceptionEntregador();
                  exception.setMensagem(e.getMessage());
                  exception.setMetodo("getLogin");
                  exception.setCodigo(e.hashCode());
                  exception.setStatus(true);
                  listaErro.add(exception);
                  
                  loginErro.setListaErro(listaErro);

                
                  
                  return login;
            }
            
            return login; 
      
      
      }
      
      @SuppressWarnings("static-access")
      @GET
      @Produces("application/json; charset=UTF-8")
      @Path("/movimentacaoDistribuicaoReclamacao/{dataDoJornal}/{codigoDaPessoaDistribuidor}/{codigoDoProdutoServico}/{codProdutoCliente}")
      public List<CadMovimentacaoDistribuicaoReclamacao>getMovimentacaoDistribuicaoReclamacao(@PathParam("dataDoJornal") String dataDoJornal, @PathParam("codigoDaPessoaDistribuidor") Integer codigoDaPessoaDistribuidor,
                  @PathParam("codigoDoProdutoServico") Integer codigoDoProdutoServico,
                  @PathParam("codProdutoCliente") Integer codProdutoCliente)
      {

            CadMovimentacaoDistribuicaoReclamacao movimentacaoDistribuicaoReclamacao = new CadMovimentacaoDistribuicaoReclamacao();
            List<CadMovimentacaoDistribuicaoReclamacao> lista = new ArrayList<CadMovimentacaoDistribuicaoReclamacao>();
            
            List<CadMovimentoCirculacao> movimentacoes = new ArrayList<CadMovimentoCirculacao>();
            List<CadDistribuicaoCirculacao> distribuicoes = new ArrayList<CadDistribuicaoCirculacao>();
            List<CadReclamacaoCirculacao> reclamacoes = new ArrayList<CadReclamacaoCirculacao>();
            
         
            EntregadorDAO dao = new EntregadorDAO();
            String retorno="";
            
            try
            {
                  movimentacoes = dao.getMovimentacao(dataDoJornal, codigoDaPessoaDistribuidor, codigoDoProdutoServico );
                  reclamacoes = dao.getReclamacao(dataDoJornal, codigoDaPessoaDistribuidor, codigoDoProdutoServico);
                  distribuicoes = dao.getDistribuicao(dataDoJornal, codigoDaPessoaDistribuidor, codigoDoProdutoServico, codProdutoCliente);
                  
                  
                  movimentacaoDistribuicaoReclamacao = CadMovimentacaoDistribuicaoReclamacao.MovimentacaoDistribuicaoReclamacao(movimentacoes, distribuicoes, reclamacoes);
                  lista.add(movimentacaoDistribuicaoReclamacao);
                  
            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);
                  CadMovimentacaoDistribuicaoReclamacao erro = new CadMovimentacaoDistribuicaoReclamacao();
                  List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                  ExceptionEntregador  exception = new ExceptionEntregador();
                  exception.setMensagem(e.getMessage());
                  exception.setMetodo("getMovimentacaoDistribuicaoReclamacao");
                  exception.setCodigo(e.hashCode());
                  exception.setStatus(true);
                  listaErro.add(exception);
                  
                  lista.add(erro);
                  
            }

            return lista;
      }
      
      
      @GET
      @Produces("application/json; charset=UTF-8")
      @Path("/movimentacao/{dataDoJornal}/{codigoDaPessoaDistribuidor}/{codigoDoProdutoServico}")
      public List<CadMovimentoCirculacao> getMovimentacoes(@PathParam("dataDoJornal") String dataDoJornal, @PathParam("codigoDaPessoaDistribuidor") Integer codigoDaPessoaDistribuidor,
                  @PathParam("codigoDoProdutoServico") Integer codigoDoProdutoServico)
      {
            
            List<CadMovimentoCirculacao> movimentacoes = new ArrayList<CadMovimentoCirculacao>();
            EntregadorDAO dao = new EntregadorDAO();
            Circulacao model = new Circulacao();
            
            String retorno="";
            
            try
            {
                  movimentacoes = dao.getMovimentacao(dataDoJornal, codigoDaPessoaDistribuidor, codigoDoProdutoServico );
            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);
                  CadMovimentoCirculacao erro = new CadMovimentoCirculacao();
                  List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                  ExceptionEntregador  exception = new ExceptionEntregador();
                  exception.setMensagem(e.getMessage());
                  exception.setMetodo("getMovimentacoes");
                  exception.setCodigo(e.hashCode());
                  exception.setStatus(true);
                  listaErro.add(exception);
                  
                  movimentacoes.add(erro);
                  
            }
            
            return movimentacoes;
            
      }
      
      @GET
      @Produces("application/json; charset=UTF-8")
      @Path("/reclamacao/{dataDoJornal}/{codigoDaPessoaDistribuidor}/{codigoDoProdutoServico}")
      public List<CadReclamacaoCirculacao> getReclamacao(@PathParam("dataDoJornal") String dataDoJornal, @PathParam("codigoDaPessoaDistribuidor") int codigoDaPessoaDistribuidor, 
                  @PathParam("codigoDoProdutoServico") int codigoDoProdutoServico)
      {
            
            List<CadReclamacaoCirculacao> reclamacao = new ArrayList<CadReclamacaoCirculacao>();
            EntregadorDAO dao = new EntregadorDAO();
            CadReclamacaoCirculacao model = new CadReclamacaoCirculacao();
            
            try
            {
                  
                  reclamacao = dao.getReclamacao(dataDoJornal, codigoDaPessoaDistribuidor, codigoDoProdutoServico);
 
            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);
                  CadReclamacaoCirculacao erro = new CadReclamacaoCirculacao();
                  List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                  ExceptionEntregador  exception = new ExceptionEntregador();
                  exception.setMensagem(e.getMessage());
                  exception.setMetodo("getReclamacao");
                  exception.setCodigo(e.hashCode());
                  exception.setStatus(true);
                  listaErro.add(exception);
                  
                  reclamacao.add(erro);
                  
            }
            
            return reclamacao;
            
      }
      
      @GET
      @Produces("application/json; charset=UTF-8")
      @Path("/distribuicao/{dataDoJornal}/{codigoDaPessoaDistribuidor}/{codigoDoProdutoServico}/{codProdutoCliente}")
      public List<CadDistribuicaoCirculacao> getDistribuicao(@PathParam("dataDoJornal")String dataDoJornal, 
                  @PathParam("codigoDaPessoaDistribuidor")Integer codigoDaPessoaDistribuidor,  @PathParam("codigoDoProdutoServico")Integer codigoDoProdutoServico,
                  @PathParam("codProdutoCliente") Integer codProdutoCliente) throws SQLException, FileNotFoundException
      {
            List<CadDistribuicaoCirculacao> distribuicao = new ArrayList<CadDistribuicaoCirculacao>();
            EntregadorDAO dao = new EntregadorDAO();
            CadDistribuicaoCirculacao model = new CadDistribuicaoCirculacao();
            
            try
            {
                  distribuicao = dao.getDistribuicao(dataDoJornal, codigoDaPessoaDistribuidor, codigoDoProdutoServico, codProdutoCliente);
            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);
                  CadDistribuicaoCirculacao erro = new CadDistribuicaoCirculacao();
                  List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                  ExceptionEntregador  exception = new ExceptionEntregador();
                  exception.setMensagem(e.getMessage());
                  exception.setMetodo("getDistribuicao");
                  exception.setCodigo(e.hashCode());
                  exception.setStatus(true);
                  listaErro.add(exception);
                  
                  distribuicao.add(erro);
                  
            }
            
            return distribuicao;
      }
      
      @GET
      @Produces("application/json; charset=UTF-8")
      @Path("/produtosServicos")
      public List<ProdutosServicos> getProdutosServicos()
        {
             List<ProdutosServicos> produtosServicos = new ArrayList<ProdutosServicos>();
            EntregadorDAO dao = new EntregadorDAO();
                  
                  try
            {
                  produtosServicos = dao.getProdutosServicos();
            }
            catch (Exception e)
            {
                        geraLog(e.getMessage(), e);
                  ProdutosServicos erro = new ProdutosServicos();
                  List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                  ExceptionEntregador  exception = new ExceptionEntregador();
                  exception.setMensagem(e.getMessage());
                  exception.setMetodo("getProdutosServicos");
                  exception.setCodigo(e.hashCode());
                  exception.setStatus(true);
                  listaErro.add(exception);
                  
                  produtosServicos.add(erro);
                  
             }
        
                  return produtosServicos;
        }
      
		@GET
		@Produces("application/json; charset=UTF-8")
		@Path("/listaDeMotivosDeNaoEntrega")
		public List<CadMotivoDeNaoEntrega> getListaDeMotivosDeNaoEntrega() 
		{
			List<CadMotivoDeNaoEntrega> cadMotivoDeNaoEntrega = new ArrayList<CadMotivoDeNaoEntrega>();
			EntregadorDAO dao = new EntregadorDAO();

			try {
				cadMotivoDeNaoEntrega = dao.getListaDeMotivosDeNaoEntrega();
			} catch (Exception e) {
				geraLog(e.getMessage(), e);
				CadMotivoDeNaoEntrega erro = new CadMotivoDeNaoEntrega();
				List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
				ExceptionEntregador exception = new ExceptionEntregador();
				exception.setMensagem(e.getMessage());
				exception.setMetodo("getMotivoDeNaoEntrega");
				exception.setCodigo(e.hashCode());
				exception.setStatus(true);
				listaErro.add(exception);

				cadMotivoDeNaoEntrega.add(erro);

			}

			return cadMotivoDeNaoEntrega;
	   }
		
		@GET
		@Produces("application/json; charset=UTF-8")
		@Path("/motivoDeNaoEntrega/{numeroDoContrato}/{dataDoJornal}")
		public List<IDistribuicao> getMotivoDeNaoEntrega(@PathParam("numeroDoContrato") Integer numeroDoContrato, @PathParam("dataDoJornal") String dataDoJornal ) 
		{
			List<IDistribuicao> lista = new ArrayList<IDistribuicao>();
			EntregadorDAO dao = new EntregadorDAO();

			try {
				lista = dao.getMotivoDeNaoEntrega(numeroDoContrato, dataDoJornal);
			} catch (Exception e) {
				
				geraLog(e.getMessage(), e);
				IDistribuicao erro = new IDistribuicao();
				List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
				ExceptionEntregador exception = new ExceptionEntregador();
				exception.setMensagem(e.getMessage());
				exception.setMetodo("getMotivoDeNaoEntrega");
				exception.setCodigo(e.hashCode());
				exception.setStatus(true);
				listaErro.add(exception);

				lista.add(erro);

			}

			return lista;
	   }
		
      @SuppressWarnings("static-access")
      @GET
      @Produces("application/json; charset=UTF-8")
      @Path("/listaEdificiosPorSetor/{dataDoJornal}/{codigoDaPessoaDistribuidor}/{codigoDoProdutoServico}")
      public List<ConjuntoEdificiosSetor>getListaDeEdificiosPorSetor(@PathParam("dataDoJornal") String dataDoJornal, 
                  @PathParam("codigoDaPessoaDistribuidor") Integer codigoDaPessoaDistribuidor, @PathParam("codigoDoProdutoServico") Integer codigoDoProdutoServico)
      {

            ConjuntoEdificiosSetor edificioPorSetor = new ConjuntoEdificiosSetor();
            List<ConjuntoEdificiosSetor> lista = new ArrayList<ConjuntoEdificiosSetor>();
            
            EntregadorDAO dao = new EntregadorDAO();
            String retorno="";
            
            try
            {
                  Integer codigoDoSetorDeEntrega = dao.buscarCodigoDoSetorDeEntrega(codigoDaPessoaDistribuidor);
                  lista = dao.getListaDeEdificiosPorSetor(dataDoJornal, codigoDoSetorDeEntrega, codigoDoProdutoServico );

            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);
                  ConjuntoEdificiosSetor erro = new ConjuntoEdificiosSetor();
                  List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
                  ExceptionEntregador  exception = new ExceptionEntregador();
                  exception.setMensagem(e.getMessage());
                  exception.setMetodo("getListaDeEdificiosPorSetor");
                  exception.setCodigo(e.hashCode());
                  exception.setStatus(true);
                  listaErro.add(exception);
                  
                  lista.add(erro);
                  
            }

            return lista;
      }
      
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listaDisEntregaEdificios/{dataDoJornal}/{codigoDaPessoaDistribuidor}/{codigoDoProdutoServico}")
	public List<DisEntregaEdificios> getListaDisEntregaEdificios(@PathParam("dataDoJornal") String dataDoJornal, 
			@PathParam("codigoDaPessoaDistribuidor") int codigoDaPessoaDistribuidor, 
            @PathParam("codigoDoProdutoServico") int codigoDoProdutoServico) 
	{
		
		List<DisEntregaEdificios> lista = new ArrayList<DisEntregaEdificios>();
		EntregadorDAO dao = new EntregadorDAO();

		try {

			lista = dao.getDisEntregaEdificios(dataDoJornal, codigoDaPessoaDistribuidor, codigoDoProdutoServico);

		} catch (Exception e) {

			geraLog(e.getMessage(), e);
			DisEntregaEdificios erro = new DisEntregaEdificios();
			List<ExceptionEntregador> listaErro = new ArrayList<ExceptionEntregador>();
			ExceptionEntregador exception = new ExceptionEntregador();
			exception.setMensagem(e.getMessage());
			exception.setMetodo("getListaDisEntregaEdificios");
			exception.setCodigo(e.hashCode());
			exception.setStatus(true);
			listaErro.add(exception);

			lista.add(erro);

		}

		return lista;
	}
      
	private String getAplicacao() {
		return aplicacao;
	}

	private void geraLog(String msgErro, Exception e) {
		try {
			
			log.geraLogErro(msgErro, e, getAplicacao());
			
		} catch (Exception eErro) {
			
			System.out.println("Erro geracao do log - " + eErro.getStackTrace());
		}
	}
      
}
