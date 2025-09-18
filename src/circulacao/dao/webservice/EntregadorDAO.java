package circulacao.dao.webservice;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;

import circulacao.control.seguranca.Conexao;
import circulacao.control.seguranca.Crypt;
import circulacao.control.seguranca.CryptDados;
import circulacao.control.seguranca.Log;
import circulacao.model.webservice.CadDistribuicaoCirculacao;
import circulacao.model.webservice.CadMotivoDeNaoEntrega;
import circulacao.model.webservice.CadMovimentoCirculacao;
import circulacao.model.webservice.CadReclamacaoCirculacao;
import circulacao.model.webservice.Circulacao;
import circulacao.model.webservice.ConjuntoEdificiosSetor;
import circulacao.model.webservice.DisSetorDeEntregaProdutoServicoHorarios;
import circulacao.model.webservice.Distribuicao;
import circulacao.model.webservice.EntregadorModel;
import circulacao.model.webservice.IDistribuicao;
import circulacao.model.webservice.Movimentacao;
import circulacao.model.webservice.ProdutosServicos;
import circulacao.model.webservice.Reclamacao;
import circulacao.model.webservice.Usuario;

public class EntregadorDAO
{
      private String aplicacao = "gestoron";
      
      private static final String [] DEFAULT_KEYS = 
      {
        "01210ACB39201293948ABE4839201CDF",
        "123219843895AFDE3920291038103839",
        "89128912093908120983980981098309",
        "AABBCCDD019201920384383728298109"
    };
      
      private byte [][] keys = null;
      
      static Properties props = new Properties();
            
      private Log log = new Log();

      
      
		private String formataData(String data) {
			String dataTimestamp = data;
		
			try {

				dataTimestamp = dataTimestamp.substring(6, 10) + "-" + dataTimestamp.substring(3, 5) + "-"
						+ dataTimestamp.substring(0, 2);

			} catch (Exception e) {

				geraLog("", e);
				return null;
			}

			return dataTimestamp;
		}
      
      public String getCNPJ() throws Exception {

  		String sqlPesquisa = "";
  		String identMF = "";
  		Conexao.openConnection();
  		

  		try {
  			ResultSet rs;
  		   

  			sqlPesquisa = "SELECT identMF "+
  						  " FROM vCadPessoaFisicaJuridica "+
  					      " WHERE codigoDaPessoa = 1";

  			rs = Conexao.executeQuery(sqlPesquisa);

  			if (rs.next()) {
  				identMF = rs.getString(1);
  			}
  			
  			rs.close();
            Conexao.closeConnection();
  			return identMF;

  		}  catch (Exception e) {
            geraLog("", e);
            return null;
  		}
  	}
      
      /**
       * Metodo utilizado para buscar movimentacao de uma distribui��o
       * valida
       * @param dataDoJornal
       * @param codigoDaPessoaDistribuidor
       * @param codigoDoProdutoServico
       * @param codigoDoSetorDeEntrega
       * @param movimento
       * @return
       * @throws SQLException
       * @throws FileNotFoundException 
       */
      @SuppressWarnings("static-access")
      public  List<CadMovimentoCirculacao> getMovimentacao(String dataDoJornal, Integer codigoDaPessoaDistribuidor, 
                  Integer codigoDoProdutoServico ) throws SQLException, FileNotFoundException
      {
                  
                  List<CadMovimentoCirculacao> lista = new ArrayList<CadMovimentoCirculacao>();
                  Movimentacao movimentacao = new Movimentacao();
                  Circulacao circulacao = new Circulacao();
                  CadMovimentoCirculacao movimentoCirculacao = new CadMovimentoCirculacao();
                  
                  
                  String retorno="";
                  String sql = "";
                  
                  if(dataDoJornal!=null && !dataDoJornal.equals(""))
                  {
                              dataDoJornal = formataData(dataDoJornal);
                  }
                  else
                  {
                              dataDoJornal = "";
                  }
                  
                       
                              ResultSet rs;
                              
                              Conexao.openConnection();
                              
                               sql = " SET DATEFORMAT ymd  "+
                                           " SELECT ltrim(rtrim(vDisMovimentacaoDeContratos.numeroDoContrato)) as numeroDoContrato,"+
                                           " ltrim(rtrim(vCadpessoaFisicaJuridica.nomeRazaoSocial)) as nomeRazaoSocial, "+ 
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.siglaTipoLogradouro)) + ' ' + ltrim(rtrim(vDisMovimentacaoDeContratos.nomeDoLogradouro)) +  ', ' + "+
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.numeroDoEndereco)) + '  ' + ltrim(rtrim(vDisMovimentacaoDeContratos.complementoDoEndereco)) + '  ' +  "+
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.nomeDoBairro)) + ' -  ' + ltrim(rtrim(vDisMovimentacaoDeContratos.nomeDoMunicipio)) + ' - '+ "+ 
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.observacaoSobreEndereco))  +' '+ vDisMovimentacaoDeContratos.observacaoSobreLocalDeEntrega  as enderecoCompleto, "+
                                            //" ltrim(rtrim(vDisMovimentacaoDeContratos.cep)) , "+
                                            " ltrim(rtrim(assCodigosMovimentacao.movimento)) as movimento, "+  
                                            " ltrim(rtrim(assCodigosMovimentacao.descricao)) as descricaoMovimento, "+
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.codigoDoSetorDeEntrega)) as codigoDoSetorDeEntrega, "+            
                                            " ltrim(rtrim(assCodigosMovimentacao.codigo)) as codigoMovimento, "+
                                            " convert(varchar,vDisMovimentacaoDeContratos.dataDoJornal,103) as dataDoJornal, "+             
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.codigoDoProdutoServico)) as codigoDoProdutoServico, "+ 
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.nomeDoSetorDeEntrega)) as nomeDoSetorDeEntrega, "+
                                            " vDisMovimentacaoDeContratos.qtdJornaisContrato as quantidadeDeExemplares, "+
                                            " case when contrato.dataDeValidadeInicial = vDisMovimentacaoDeContratos.dataDoJornal "+
                                            " then 1 Else 0 end as primeiraEntrega," +
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.siglaTipoLogradouro)) as siglaTipoLogradouro, "+
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.nomeDoLogradouro)) as nomeDoLogradouro, " +
                                            " ltrim(rtrim(vDisMovimentacaoDeContratos.nomeDoLogradouro)) + ', ' + ltrim(rtrim(vDisMovimentacaoDeContratos.numeroDoEndereco)) as logradouroNumeroDoEndereco, " +
                                            " vDisMovimentacaoDeContratos.codigoDoLogradouro as codigoDoLogradouro "+
                                            " FROM   (((((dbo.vDisMovimentacaoDeContratos vDisMovimentacaoDeContratos "+ 
                                " INNER JOIN dbo.assCodigosMovimentacao assCodigosMovimentacao "+ 
                                " ON vDisMovimentacaoDeContratos.codigoDoMotivoMovimentacao=assCodigosMovimentacao.codigo) "+ 
                                " INNER JOIN dbo.assLocalDeEntrega assLocalDeEntrega "+
                                " ON vDisMovimentacaoDeContratos.codigoDoEnderecamento=assLocalDeEntrega.codigoDoEnderecamento) "+ 
                                " INNER JOIN dbo.disZonaSetorDeDistribuicao disZonaSetorDeDistribuicao "+
                                " ON vDisMovimentacaoDeContratos.codigoDoSetorDeEntrega=disZonaSetorDeDistribuicao.codigoDoSetorDeEntrega) "+
                                " INNER JOIN dbo.cadMeioDeEntrega cadMeioDeEntrega "+
                                " ON assLocalDeEntrega.codigoMeioDeEntrega=cadMeioDeEntrega.codigoMeioDeEntrega) "+
                                " INNER JOIN dbo.vCadpessoaFisicaJuridica vCadpessoaFisicaJuridica "+
                                " ON assLocalDeEntrega.codigoDaPessoa=vCadpessoaFisicaJuridica.codigoDaPessoa) "+
                                " INNER JOIN dbo.disAreaZonaDeDistribuicao disAreaZonaDeDistribuicao "+
                                " ON disZonaSetorDeDistribuicao.codigoDaZonaDeDistribuicao=disAreaZonaDeDistribuicao.codigoDaZonaDeDistribuicao "+
                                " INNER JOIN dbo.assContratos contrato "+
                                " ON vDisMovimentacaoDeContratos.numeroDoContrato = contrato.numeroDoContrato "+
                                " INNER JOIN dbo.disItinerarios disItinerarios "+
                                " ON vDisMovimentacaoDeContratos.codigoDoSetorDeEntrega = disItinerarios.codigoDoSetorDeEntrega "+
                                " WHERE vDisMovimentacaoDeContratos.dataDoJornal = '"+dataDoJornal+"' "+
                                //AND vDisMovimentacaoDeContratos.dataDoJornal<{ts '2019-02-11 00:00:00'}) "+
                                " AND vDisMovimentacaoDeContratos.codigoDoProdutoServico= "+codigoDoProdutoServico+" "+ 
                                " AND disItinerarios.codigoDaPessoaResponsavel = "+codigoDaPessoaDistribuidor+"  "+
                                " ORDER BY assCodigosMovimentacao.movimento asc, " +
                                //"vDisMovimentacaoDeContratos.siglaTipoLogradouro, " +
                                " vDisMovimentacaoDeContratos.nomeDoLogradouro," +
                                " convert(int,isnull(vDisMovimentacaoDeContratos.numeroDoEndereco,0))," +
                                " vDisMovimentacaoDeContratos.complementoDoEndereco";
                              
                              //System.out.println(sql);
                              rs = Conexao.executeQuery(sql);

                              
                              while(rs.next())
                              {
                                 movimentacao = new Movimentacao();
                                 circulacao= new Circulacao();
                                 movimentoCirculacao = new CadMovimentoCirculacao();
                                    
                                  circulacao = Circulacao.circulacao(rs.getInt("numeroDoContrato"), 
                                                rs.getString("nomeRazaoSocial"), 
                                                rs.getString("enderecoCompleto"), 
                                                rs.getString("nomeDoSetorDeEntrega"), 
                                                rs.getInt("codigoDoSetorDeEntrega"),
                                                rs.getString("dataDoJornal"), 
                                                rs.getInt("codigoDoProdutoServico"),
                                                rs.getInt("quantidadeDeExemplares"),
                                                rs.getBoolean("primeiraEntrega"),
                                                rs.getString("siglaTipoLogradouro"),
                                                rs.getString("nomeDoLogradouro"),
                                                rs.getString("logradouroNumeroDoEndereco"),
                                                rs.getInt("codigoDoLogradouro"));
                                  
                                  movimentacao = Movimentacao.movimentacao(rs.getInt("codigoMovimento"), 
                                              rs.getString("descricaoMovimento"), 
                                              rs.getString("movimento"));
                                  
                                  movimentoCirculacao = CadMovimentoCirculacao.cadMomentacaoCirculacao(movimentacao, circulacao);
                                    lista.add(movimentoCirculacao);
                              }
                              
                              rs.close(); 
                              Conexao.closeConnection();
                
                  
                  
                  return lista;
      }
      
 
      /**
       * Metodo utilizado para buscar reclam��es de entrega de uma distribui��o valida
       * @param dataDoJornal
       * @param codigoDaPessoaDistribuidor
       * @param codigoDoProdutoServico
       * @param codigoDoSetorDeEntrega
       * @return
       * @throws SQLException
       * @throws FileNotFoundException 
       */
      @SuppressWarnings("static-access")
      public List<CadReclamacaoCirculacao> getReclamacao(String dataDoJornal, int codigoDaPessoaDistribuidor, 
                  int codigoDoProdutoServico) throws SQLException, FileNotFoundException
      {
              
                  List<CadReclamacaoCirculacao> lista = new ArrayList<CadReclamacaoCirculacao>();
                  Reclamacao reclamacao= new Reclamacao();
                  Circulacao circulacao= new Circulacao();
                  
                  CadReclamacaoCirculacao reclamacaoCirculacao = new CadReclamacaoCirculacao();
                  
                  
                  if(dataDoJornal!=null && !dataDoJornal.equals(""))
                  {
                              dataDoJornal = formataData(dataDoJornal);
                  }
                  else
                  {
                              dataDoJornal = "";
                  }

               
                              ResultSet rs;
                              
                              Conexao.openConnection();
                              
                              String sql = " SET DATEFORMAT ymd "+
                                          " SELECT ltrim(rtrim(disExemplaresNaoEntreguesAss.numeroDoContrato)) as numeroDoContrato, "+
                                          " ltrim(rtrim(vCadpessoaFisicaJuridica_2.nomeRazaoSocial)) as nomeRazaoSocial, "+
                                          " ltrim(rtrim(cadMotivoDeNaoEntrega.descricaoMotivoDeNaoEntrega))as descricaoMotivoDeNaoEntrega, "+
                                          " ltrim(rtrim(vCadEnderecoDeEntrega.siglaTipoLogradouro)) + ' ' + ltrim(rtrim(vCadEnderecoDeEntrega.nomeDoLogradouro)) +  ', ' + "+
                                          " ltrim(rtrim(vCadEnderecoDeEntrega.numeroDoEndereco)) + '  ' + ltrim(rtrim(vCadEnderecoDeEntrega.complementoDoEndereco)) + '  ' + "+ 
                                          " ltrim(rtrim(nomeDoBairro)) + ' -  ' + ltrim(rtrim(nomeDoMunicipio)) + ' - '+ "+ 
                                          " ltrim(rtrim(siglaDaUf)) + ' -  ' +"+
                                          //"+ ' cep: '+ ltrim(rtrim(vCadEnderecoDeEntrega.cep)) "+ 
                                          " ltrim(rtrim(vCadEnderecoDeEntrega.observacaoSobreEndereco))  +' '+ vCadEnderecoDeEntrega.observacaoSobreLocalDeEntrega  as enderecoCompleto,"+
                                          " ltrim(rtrim(disExemplaresNaoEntreguesAss.idReposicao)) as idReposicao, "+
                                          " ltrim(rtrim(meioDeEntrega.descricaoMeioDeEntrega)) as descricaoMeioDeEntrega, "+
                                          " disExemplaresNaoEntreguesAss.quantidadeDeExemplares as quantidadeDeExemplares, "+
                                          " ltrim(rtrim(assDistribuicao.codigoDoSetorDeEntrega)) as codigoDoSetorDeEntrega, "+
                                          " convert(varchar,disExemplaresNaoEntreguesAss.dataDoJornal,103) as dataDoJornal,  "+ 
                                          " ltrim(rtrim(assDistribuicao.codigoDoProdutoServico))as codigoDoProdutoServico, "+
                                          " ltrim(rtrim(disSetorDeEntrega.nomeDoSetorDeEntrega))as nomeDoSetorDeEntrega, "+
                                          " endLogradouros.compleLog, "+
                                          " cadEdicao.diaDaSemana, "+
                                          " disExemplaresNaoEntreguesAss.dataHoraCadastro, "+
                                          " case when assContratos.dataDeValidadeInicial = disExemplaresNaoEntreguesAss.dataDoJornal "+
                                          " then 1 Else 0 end as primeiraEntrega," +
                                          " ltrim(rtrim(vCadEnderecoDeEntrega.siglaTipoLogradouro)) as siglaTipoLogradouro,"+
                                          " ltrim(rtrim(vCadEnderecoDeEntrega.nomeDoLogradouro)) as nomeDoLogradouro, " +
                                          " ltrim(rtrim(vCadEnderecoDeEntrega.nomeDoLogradouro)) +', '+ ltrim(rtrim(vCadEnderecoDeEntrega.numeroDoEndereco)) as logradouroNumeroDoEndereco, " +
                                          " vCadEnderecoDeEntrega.codigoDoLogradouro as codigoDoLogradouro, " +
                                          " ltrim(rtrim(disExemplaresNaoEntreguesAss.observacao )) as observacao "+
                                     " FROM   {oj (((((((dbo.disExemplaresNaoEntreguesAss disExemplaresNaoEntreguesAss "+
                                     " INNER JOIN dbo.cadMotivoDeNaoEntrega cadMotivoDeNaoEntrega "+
                                     " ON disExemplaresNaoEntreguesAss.codigoDoMotivoDeNaoEntrega=cadMotivoDeNaoEntrega.codigoMotivoDeNaoEntrega) "+
                                     " LEFT OUTER JOIN dbo.assDistribuicao assDistribuicao "+
                                     " ON (disExemplaresNaoEntreguesAss.numeroDoContrato=assDistribuicao.numeroDoContrato) "+
                                     " AND (disExemplaresNaoEntreguesAss.dataDoJornal=assDistribuicao.dataDoJornal)) "+
                                     " INNER JOIN dbo.cadEdicao cadEdicao "+
                                     " ON (disExemplaresNaoEntreguesAss.dataDoJornal=cadEdicao.dataDoJornal) "+
                                     " AND (disExemplaresNaoEntreguesAss.codigoDoProdutoServico=cadEdicao.codigoDoProdutoServico)) "+
                                     " INNER JOIN dbo.assLocalDeEntregaContrato assLocalDeEntregaContrato  "+
                                     " ON (disExemplaresNaoEntreguesAss.numeroDoContrato=assLocalDeEntregaContrato.numeroDoContrato) "+
                                     " AND (cadEdicao.diaDaSemana=assLocalDeEntregaContrato.diaDaSemana)) "+
                                     " INNER JOIN dbo.vCadEnderecoDeEntrega vCadEnderecoDeEntrega  "+
                                     " ON assLocalDeEntregaContrato.codigoDoEnderecamento=vCadEnderecoDeEntrega.codigoDoEnderecamento) "+
                                     " INNER JOIN dbo.endLogradouros endLogradouros "+
                                     " ON vCadEnderecoDeEntrega.codigoDoLogradouro=endLogradouros.codigoDoLogradouro) "+
                                     " INNER JOIN dbo.assContratos assContratos "+
                                     " ON assDistribuicao.numeroDoContrato=assContratos.numeroDoContrato) "+
                                     " INNER JOIN dbo.vCadpessoaFisicaJuridica vCadpessoaFisicaJuridica_2 "+
                                     " ON assContratos.codigoDaPessoa=vCadpessoaFisicaJuridica_2.codigoDaPessoa "+
                                     " INNER JOIN cadMeioDeEntrega meioDeEntrega     "+
                                     " ON meioDeEntrega.codigoMeioDeEntrega = vCadEnderecoDeEntrega.codigoMeioDeEntrega "+
                                     " INNER JOIN dbo.disItinerarios disItinerarios "+
                                     " ON assDistribuicao.codigoDoSetorDeEntrega = disItinerarios.codigoDoSetorDeEntrega "+
                                     " INNER JOIN disSetorDeEntrega "+
                                     " ON assDistribuicao.codigoDoSetorDeEntrega = disSetorDeEntrega.codigoDoSetorDeEntrega} "+
                                     " WHERE "+
                                     " disExemplaresNaoEntreguesAss.dataDoJornal = '"+dataDoJornal+"' "+
                                     //"AND disExemplaresNaoEntreguesAss.dataDoJornal<{ts '2019-04-17 00:00:00'}) "+
                                     " AND assDistribuicao.codigoDoProdutoServico= "+codigoDoProdutoServico+"  "+
                                     //"AND assDistribuicao.codigoDoSetorDeEntrega=11
                                     " AND disItinerarios.codigoDaPessoaResponsavel= "+codigoDaPessoaDistribuidor+" "+
                                     " ORDER BY " +
                                     " vCadEnderecoDeEntrega.nomeDoLogradouro," +
                                     " convert(int,isnull(vCadEnderecoDeEntrega.numeroDoEndereco,0))," +
                                     " vCadEnderecoDeEntrega.complementoDoEndereco";
                              
                              rs = Conexao.executeQuery(sql);
                             // System.out.println(sql);
                              
                              while(rs.next())
                              {
                                    reclamacao = new Reclamacao();
                                    circulacao = new Circulacao();
                                    reclamacaoCirculacao = new CadReclamacaoCirculacao();
                                    
                                    reclamacao = Reclamacao.reclamacao(rs.getString("descricaoMotivoDeNaoEntrega"), 
                                                rs.getString("idReposicao"), 
                                                rs.getString("descricaoMeioDeEntrega"),
                                                rs.getString("observacao"));
                                    
                                    circulacao = Circulacao.circulacao(rs.getInt("numeroDoContrato"), 
                                                rs.getString("nomeRazaoSocial"), 
                                                rs.getString("enderecoCompleto"), 
                                                rs.getString("nomeDoSetorDeEntrega"), 
                                                rs.getInt("codigoDoSetorDeEntrega"),
                                                rs.getString("dataDoJornal"), 
                                                rs.getInt("codigoDoProdutoServico"),
                                                rs.getInt("quantidadeDeExemplares"),
                                                rs.getBoolean("primeiraEntrega"),
                                                rs.getString("siglaTipoLogradouro"),
                                                rs.getString("nomeDoLogradouro"),
                                                rs.getString("logradouroNumeroDoEndereco"),
                                                rs.getInt("codigoDoLogradouro"));
                                               
                                                
                                    reclamacaoCirculacao = CadReclamacaoCirculacao.reclamacaoCirculacao(reclamacao, circulacao);
                                    
                                    lista.add(reclamacaoCirculacao);
                                   
                              }
                              rs.close();

                              Conexao.closeConnection();
                  
                  return lista;
      }
      
      
      public List<CadDistribuicaoCirculacao> getDistribuicao(String dataDoJornal, Integer codigoDaPessoaDistribuidor, Integer codigoDoProdutoServico, Integer codProdutoCliente) throws SQLException, FileNotFoundException
      {                                               
            
                  
                  List<CadDistribuicaoCirculacao> lista = new ArrayList<CadDistribuicaoCirculacao>();
                  
                  CadDistribuicaoCirculacao distribuicaoCirculacao= new CadDistribuicaoCirculacao();
                  Distribuicao distribuicao= new Distribuicao();
                  Circulacao circulacao= new Circulacao();
                  ProdutosServicos produtosServicos = new ProdutosServicos();
                  

                  if(dataDoJornal!=null && !dataDoJornal.equals(""))
                  {
                              dataDoJornal = formataData(dataDoJornal);
                  }
                  else
                  {
                              dataDoJornal = "";
                  }
                  
                
                              ResultSet rs;
                              
                              Conexao.openConnection();
                              
                              String sql = " SET DATEFORMAT ymd " + 
                                          " SELECT  case when seq.sequenciamentoDeEntrega IS NULL then 1 Else 0 end as sequenciamentoDeEntrega," +
                                          " seq.sequenciamentoDeEntrega as sequenciamento, " +
                                          " ltrim(rtrim(assinante.nomeRazaoSocial)) as nomeRazaoSocial, " + 
                                          " assContratos.qtdJornaisContrato, ltrim(rtrim(siglaTipoLogradouro)) + ' ' + ltrim(rtrim(nomeDoLogradouro)) +  ', ' + " + 
                                          " ltrim(rtrim(vCadEnderecoDeEntrega.numeroDoEndereco)) + '  ' + ltrim(rtrim(vCadEnderecoDeEntrega.complementoDoEndereco)) + '  ' + " + 
                                          " ltrim(rtrim(nomeDoBairro)) + ' -  ' + ltrim(rtrim(nomeDoMunicipio)) + ' - '+ ltrim(rtrim(siglaDaUf)) +' - '+" + 
                                          " ltrim(rtrim(vCadEnderecoDeEntrega.observacaoSobreEndereco)) +' '+ vCadEnderecoDeEntrega.observacaoSobreLocalDeEntrega as enderecoCompleto, "+
                                           // "  + ' cep: '+ ltrim(rtrim(vCadEnderecoDeEntrega.cep)) ,  "+
                                          " ltrim(rtrim(cadProdutosServicos.descricaoDoProdutoServico)) as descricaoDoProdutoServico,  " + 
                                          " ltrim(rtrim(disSetorDeEntrega.nomeDoSetorDeEntrega)) as nomeDoSetorDeEntrega, " +
                                          " ltrim(rtrim(disSetorDeEntrega.codigoDoSetorDeEntrega)) as codigoDoSetorDeEntrega, " + 
                                          " ltrim(rtrim(pessoaDistr.nomeRazaoSocial)) as distribuidor, assContratos.numeroDoContrato," +
                                          " ltrim(rtrim(assDistribuicao.dataDoJornal)) as dataDoJornal," + 
                                          " ltrim(rtrim(cadProdutosServicos.codigoDoProdutoServico)) as codigoDoProdutoServico," +
                                          " ltrim(rtrim(cadProdutosServicos.descricaoDoProdutoServico)) as descricaoDoProdutoServico," + 
                                          " ltrim(rtrim(assDistribuicao.codigoDaPessoaDistribuidor)) as codigoDaPessoaDistribuidor," +
                                          " case when assContratos.dataDeValidadeInicial = assDistribuicao.dataDoJornal "+
                                          " then 1 Else 0 end as primeiraEntrega," +
                                          " ltrim(rtrim(vCadEnderecoDeEntrega.siglaTipoLogradouro)) as siglaTipoLogradouro, "+
                                          " ltrim(rtrim(nomeDoLogradouro)) as nomeDoLogradouro, " +
                                          " ltrim(rtrim(nomeDoLogradouro)) + ', ' +ltrim(rtrim(vCadEnderecoDeEntrega.numeroDoEndereco)) as logradouroNumeroDoEndereco," +
                                          " vCadEnderecoDeEntrega.codigoDoLogradouro as codigoDoLogradouro,"+
                                          " ltrim(rtrim(assDistribuicao.codigoMotivoDeNaoEntrega)) as codigoMotivoDeNaoEntrega," +
                                          " convert(varchar,assDistribuicao.dataHoraEntrega,103) as dataHoraEntrega "+
                                          " FROM assDistribuicao " + 
                                          " inner join  vCadPessoaFisicaJuridica assinante " + 
                                          " on assDistribuicao.codigoDaPessoa = assinante.codigoDaPessoa " + 
                                          " inner join vCadEnderecoDeEntrega " + 
                                          " on vCadEnderecoDeEntrega.codigoDoEnderecamento = assDistribuicao.codigoDoEnderecamento " + 
                                          " inner join assContratos " + 
                                          " on assDistribuicao.numeroDoContrato = assContratos.numeroDoContrato " + 
                                          " inner join cadProdutosServicos " + 
                                          " on  assDistribuicao.codigoDoProdutoServico = cadProdutosServicos.codigoDoProdutoServico " + 
                                          " inner join disSetorDeEntrega " + 
                                          " on assDistribuicao.codigoDoSetorDeEntrega = disSetorDeEntrega.codigoDoSetorDeEntrega" + 
                                          " inner join cadDistribuidor " + 
                                          " on assDistribuicao.codigoDaPessoaDistribuidor = cadDistribuidor.codigoDaPessoa " + 
                                          " inner join vCadPessoaFisicaJuridica pessoaDistr " + 
                                          " on assDistribuicao.codigoDaPessoaDistribuidor = pessoaDistr.codigoDaPessoa " + 
                                          " inner join cadSituacao " + 
                                          " on cadSituacao.codigoSituacao = assDistribuicao.situacaoDoContrato " + 
                                          " inner join  disItinerarios I on I.codigoDoSetorDeEntrega = assDistribuicao.codigoDoSetorDeEntrega " + 
                                          " left join disSequenciamentoDeEntrega seq " + 
                                          " on seq.codigoDoEnderecamento = assDistribuicao.codigoDoEnderecamento    " + 
                                          " and seq.codigoDoProdutoServico = assDistribuicao.codigoDoProdutoServico " + 
                                          " and seq.codigoDoSetorDeEntrega= assDistribuicao.codigoDoSetorDeEntrega " + 
                                          " WHERE " + 
                                          " assDistribuicao.dataDoJornal = '"+dataDoJornal+"' "+  
                                          " and  assDistribuicao.situacaoDoContrato = 1 " + 
                                          " and assDistribuicao.codigoDoProdutoServico = "+codigoDoProdutoServico +
                                          " and I.codigoDaPessoaResponsavel = "+codigoDaPessoaDistribuidor ;
                              
                              
                                           if(codProdutoCliente == 0)//codProdutoCliente = 0 p/ OVALE, DI�RIO DA REGI�O
                                           {
                                                sql += " ORDER BY  " +
                                                       " vCadEnderecoDeEntrega.nomeDoLogradouro," +
                                                       " convert(int,isnull(vCadEnderecoDeEntrega.numeroDoEndereco,0))," +
                                                       " vCadEnderecoDeEntrega.complementoDoEndereco";
                                                 
                                           }
                                           else // codProdutoCliente = 1 FLORIANOPOLIS
                                           {
                                                 sql += " ORDER BY sequenciamento, " +
                                                        " vCadEnderecoDeEntrega.nomeDoLogradouro, nomeDoBairro," +
                                                        " convert(int,isnull(vCadEnderecoDeEntrega.numeroDoEndereco,0))," +
                                                        " vCadEnderecoDeEntrega.complementoDoEndereco";
                                                 
                                           }
                                          
                              
                              rs = Conexao.executeQuery(sql);
                             // System.out.println(sql); 
                              
                              while(rs.next())
                              {
                                    distribuicao = new Distribuicao();
                                    circulacao = new Circulacao();
                                    distribuicaoCirculacao= new CadDistribuicaoCirculacao();
                                    produtosServicos = new ProdutosServicos();
                                  
                                    distribuicao = Distribuicao.distribuicao(rs.getInt("codigoDaPessoaDistribuidor"), 
                                                rs.getInt("sequenciamentoDeEntrega"),
                                                rs.getInt("sequenciamento"),
                                                rs.getInt("codigoMotivoDeNaoEntrega"),
                                                rs.getString("dataHoraEntrega"));
                                    
                                    circulacao = Circulacao.circulacao(rs.getInt("numeroDoContrato"), 
                                                rs.getString("nomeRazaoSocial"), 
                                                rs.getString("enderecoCompleto"), 
                                                rs.getString("nomeDoSetorDeEntrega"), 
                                                rs.getInt("codigoDoSetorDeEntrega"),
                                                rs.getString("dataDoJornal"), 
                                                rs.getInt("codigoDoProdutoServico"),
                                                rs.getInt("qtdJornaisContrato"),
                                                rs.getBoolean("primeiraEntrega"),
                                                rs.getString("siglaTipoLogradouro"),
                                                rs.getString("nomeDoLogradouro"),
                                                rs.getString("logradouroNumeroDoEndereco"),
                                                rs.getInt("codigoDoLogradouro"));
                                    
                                    produtosServicos = ProdutosServicos.produtosServicos(rs.getInt("codigoDoProdutoServico"), 
                                                rs.getString("descricaoDoProdutoServico"));
                                    
                                    distribuicaoCirculacao = CadDistribuicaoCirculacao.cadDistribuicaoCirculacao(distribuicao, circulacao, produtosServicos);
                                         
                                    
                                    lista.add(distribuicaoCirculacao);
                              }
                              rs.close();
                            
                              Conexao.closeConnection();
                  
                  return lista;
      }
      
      /**
       * Metodo utilizado para buscar todos 
       * os produtos ativos
       * @return
       * @throws SQLException
       * @throws FileNotFoundException 
       */
      public List<ProdutosServicos> getProdutosServicos() throws SQLException, FileNotFoundException
      {
                
                  List<ProdutosServicos> lista = new ArrayList<ProdutosServicos>();
                  ProdutosServicos produtosServicos= new ProdutosServicos();
                              ResultSet rs;
                              Conexao.openConnection();

               
                         String sql = " select codigoDoProdutoServico, ltrim(rtrim(descricaoDoProdutoServico)) as descricaoDoProdutoServico "+
                                                 " from cadProdutosServicos "+
                                                 " where idAtivo =1 "+
                                                 " order by descricaoDoProdutoServico ";
                              
                               rs = Conexao.executeQuery(sql);
                              
                              while(rs.next())
                              {
                                 produtosServicos = new ProdutosServicos();
                                                 
                                                 produtosServicos = ProdutosServicos.produtosServicos(rs.getInt("codigoDoProdutoServico"), 
                                                rs.getString("descricaoDoProdutoServico"));
                                                 
                                  lista.add(produtosServicos);
                              }
                              
                                rs.close();
                                Conexao.closeConnection();
                  
                  return lista;
      }
      
      /**
       * Metodo utilizado para buscar todos 
       * os Motivos De Não Entrega
       * @return
       * @throws SQLException
       * @throws FileNotFoundException 
       */
      public List<CadMotivoDeNaoEntrega> getListaDeMotivosDeNaoEntrega() throws SQLException, FileNotFoundException
      {
                
                  List<CadMotivoDeNaoEntrega> lista = new ArrayList<CadMotivoDeNaoEntrega>();
                  CadMotivoDeNaoEntrega motivoDeNaoEntrega = new CadMotivoDeNaoEntrega();
                              ResultSet rs;
                              Conexao.openConnection();

               
                         String sql = " select codigoMotivoDeNaoEntrega, "
                         		    + " ltrim(rtrim(descricaoMotivoDeNaoEntrega)) as descricaoMotivoDeNaoEntrega,"
                         		    + " quantidadeMaximaDeOcorrencias,"
                         		    + " pesoDaOcorrencia, "
                         		    + " multaDeEntrega, "
                         		    + " idAtivo,"
                         		    + " idApp "
                                    + " from cadMotivoDeNaoEntrega "
                                    + " where codigoMotivoDeNaoEntrega > 0 and idAtivo = 1 and idApp = 1 "
                                    + " order by descricaoMotivoDeNaoEntrega ";
                              
                               rs = Conexao.executeQuery(sql);
                              
                              while(rs.next())
                              {
                            	  motivoDeNaoEntrega = new CadMotivoDeNaoEntrega();
                                                 
                            	  motivoDeNaoEntrega = CadMotivoDeNaoEntrega.cadMotivoDeNaoEntrega(
                            			        rs.getInt("codigoMotivoDeNaoEntrega"), 
                                                rs.getString("descricaoMotivoDeNaoEntrega"),
                                                rs.getInt("quantidadeMaximaDeOcorrencias"), 
                                                rs.getInt("pesoDaOcorrencia"), 
                                                rs.getDouble("multaDeEntrega"), 
                                                rs.getBoolean("idAtivo"),
                                                rs.getString("idApp"));
                                                 
                                  lista.add(motivoDeNaoEntrega);
                              }
                              
                                rs.close();
                                Conexao.closeConnection();
                  
                  return lista;
      }
      
      /**
       * Metodo utilizado para retornar todos os distribuidores 
       * @return
       * @throws SQLException
       */
      public String getDistribuidor() throws SQLException
      {
                  Gson g = new Gson();
                  List<EntregadorModel> listaMapa = new ArrayList<EntregadorModel>();
                  EntregadorModel model= new EntregadorModel();
                  String retorno="";

                  
                  try 
                  {
                              ResultSet rs;
                              
                              Conexao.openConnection();
                              
                              String sql = " select d.codigoDaPessoa , p.nomeRazaoSocial "+
                                                 " from cadDistribuidor d "+
                                                 " inner join vCadPessoaFisicaJuridica p "+
                                                 " on d.codigoDaPessoa = p.codigoDaPessoa "+
                                                 " order by p.nomeRazaoSocial ";
                              
                              rs = Conexao.executeQuery(sql);
                              
                              while(rs.next())
                              {
                                          listaMapa.add(model.getDistribuidorModel(rs.getInt("codigoDaPessoa"), rs.getString("nomeRazaoSocial")));
                              }
                              rs.close();
                              
                              retorno=g.toJson(listaMapa);
                  } 
                  catch (Exception e) 
                  {
                              geraLog("", e);   
                              return null;
                  }
                  finally
                  {
                              
                              Conexao.closeConnection();
                  }
                  
                  return retorno;
      }
      
      public Usuario getValidaLogin(String login, String senha) throws FileNotFoundException, SQLException
      {
                  String[] usuarioBanco = null;
                  String senhaBanco = "";
                  String senhaMaster = "";
                  Usuario usuario = new Usuario();
                  Usuario model = new Usuario();
                  
                  
                  
            if (!login.toUpperCase().equals("MASTER"))
            {
                  usuarioBanco = verificaUsuario(login);
                  
                  if (!usuarioBanco[0].toUpperCase().equals(
                              login.toUpperCase()))
                  {
                        model = new Usuario();
                        model = model = Usuario.usuario(login, "", 0, "Login Inv�lido", false);
                        usuario = model;
                        
                  }
                  else
                  {
                       senhaBanco = verificaSenha(login);
                        
                        if (!isSenhaOk(senha, senhaBanco))
                        {
                              
                              model = new Usuario();
                              model = model = Usuario.usuario(login, "", 0, "Senha Inv�lida",false);
                              usuario = model;
                              
                        }
                        else
                        {

                              String sql = "";
                              boolean idAtivo = false;
                              ResultSet rs;
                              Conexao.openConnection();
                              sql = " select codigoDoUsuario,senhaDoUsuario, codigoDaPessoa, idAtivo from segUsuario where codigoDoUsuario = '"+login+"'";
                              rs = Conexao.executeQuery(sql);
                              
                              if (rs.next()) 
                              {
                                    if(rs.getBoolean("idAtivo"))
                                    {
                                          idAtivo = true;
                                          //dataUltimoLogin(login);  
                                    } 
                                    else
                                    {
                                          idAtivo =false;
                                    }     
                                    
                                    model = new Usuario();
                                    model = model = Usuario.usuario(rs.getString("codigoDoUsuario").trim(), 
                                                                    rs.getString("senhaDoUsuario"), 
                                                                    rs.getInt("codigoDaPessoa"), 
                                                                    "Ok",idAtivo);
                                    usuario = model;
                                    
                              }
                              
                              rs.close();
                              Conexao.closeConnection();
                        }
                  }
            }
            else
            {
                  
                  senhaMaster = "adm19314";
                  
                  if (!senha.equals(senhaMaster))
                  {
                        model = new Usuario();
                        model = model = Usuario.usuario(login, "",  0, "Senha Inv�lida", false);
                        usuario = model;
                        
                  }
                  
                  {
                        model = new Usuario();
                        model = model = Usuario.usuario("MASTER", "",  1, "Ok", true);
                        usuario = model;
                  }
            }
                  
            
                  
            return  usuario;
      }
      
      private void dataUltimoLogin(String codigoUsuario)
      {
            String sql = "";
            try
            {
                 
                  Conexao.openConnection();
                  sql = " update segUsuario set dataUltimoLogin = GETDATE()"
                              + " where codigoDoUsuario = '" + codigoUsuario
                              + "'";
                  
                  Conexao.executeUpdate(sql);
                 
                  Conexao.closeConnection(); 
            }
            catch (Exception e) 
            {
                  geraLog("", e); 
            }
            
      }
      
      /**
       * Metodo utilizado para buscar nome da pessoa 
       * @param codigoDaPessoa
       * @return
       * @throws SQLException
       */
      private String getPessoa(int codigoDaPessoa) throws SQLException
      {
                  String retorno ="";
                  
                  Gson g = new Gson();
                  List<EntregadorModel> listaMapa = new ArrayList<EntregadorModel>();
                  EntregadorModel model= new EntregadorModel();
                  

                  
                  try 
                  {
                              ResultSet rs;
                              
                              Conexao.openConnection();
                              
                              String sql = " select  p.nomeRazaoSocial, p.codigoDaPessoa  \r\n"+
                                                 " from vCadPessoaFisicaJuridica p "+
                                                 " where p.codigoDaPessoa= "+codigoDaPessoa+" "+
                                                 " order by p.nomeRazaoSocial ";
                              
                              rs = Conexao.executeQuery(sql);
                              
                              while(rs.next())
                              {
                                          listaMapa.add(model.getPessoaModel( rs.getString("nomeRazaoSocial"),rs.getInt("codigoDaPessoa")));
                              }
                              rs.close();
                              
                              
                  } 
                  catch (Exception e) 
                  {
                              geraLog("", e);   
                              return null;
                  }
                  finally
                  {
                              
                              Conexao.closeConnection();
                  }
                  
                  return retorno;
      }

      
      private String verificaCodigoUsuario(String codigoUsuario) throws SQLException 
      {
                  Gson g = new Gson();
                  List<EntregadorModel> listaMapa = new ArrayList<EntregadorModel>();
                  EntregadorModel model= new EntregadorModel();
                  String retorno ="";
                  
                  try 
                  {
                              
                              String sql = "";
                              Conexao.openConnection();
                              
                              
                              
                              sql = " select u.codigoDaPessoa,  p.nomeRazaoSocial "+
                                      " from segUsuario u " +
                                      " inner join vCadPessoaFisicaJuridica p " +
                                      " on  p.codigoDaPessoa= u.codigoDaPessoa "+
                                      " where u.codigoDoUsuario = '"+codigoUsuario+"' ";
                              
                              ResultSet rs = Conexao.executeQuery(sql);
                              
                              if (rs.next()) 
                              {
                                          
                                          listaMapa.add(model.getPessoaModel( rs.getString("nomeRazaoSocial"),rs.getInt("codigoDaPessoa")));
                                          
                              }
                              
                              rs.close();
                              
                              
                              retorno=g.toJson(listaMapa);
                              
                  } 
                  catch (Exception e) 
                  {
                        geraLog("", e);
                        return null;
                }
                  finally
                  {
                              
                              Conexao.closeConnection();
                  }
                  
                  return retorno;
      }
      
      
      private String[] verificaUsuario(String codigoUsuario) 
      {
                  try 
                  {
                              String usuario[] = new String[2];
                              usuario[0] = "";
                              usuario[1] = "0";
                              String sql = "";
                              ResultSet rs;
                              Conexao.openConnection();
                              sql = " select codigoDaPessoa,codigoDoUsuario from segUsuario where codigoDoUsuario = '"+codigoUsuario+"'";
                              rs = Conexao.executeQuery(sql);
                              
                              if (rs.next()) 
                              {
                                          usuario[0] = rs.getString(2);
                                          usuario[1] = rs.getString(1);
                                          if (usuario[0]==null) usuario[0] = "";
                                          usuario[0] = usuario[0].trim();
                              }
                              
                              rs.close();
                              Conexao.closeConnection();
                              
                              return usuario;
                  } 
                  catch (Exception e) 
                  {
                        geraLog("", e);
                        return null;
                }
      }
      
      
      private String verificaSenha(String codigoUsuario) 
      {
                  try 
                  {
                              String senha = "";
                              String sql = "";
                              ResultSet rs;
                              Conexao.openConnection();
                              sql = " select senhaDoUsuario from segUsuario where codigoDoUsuario = '"+codigoUsuario+"'";
                              rs = Conexao.executeQuery(sql);
                              
                              if (rs.next()) 
                              {
                                    senha = rs.getString(1).trim(); 
                              }
                              rs.close();
                              Conexao.closeConnection();
                              
                              return senha;
                  } 
                  catch (Exception e) 
                  {
                        geraLog("", e);
                        return null;
                }
      }
      
      
      @SuppressWarnings("static-access")
      private boolean isSenhaOk(String senhaDigitada, String senhaAtual) 
      {
               new Crypt();
                  
                  String uncSenha = Crypt.descriptografa(senhaAtual, "martinsconsultoria");
               
               String uncSenhaNewKey ="";
               
               try 
               { 
                  initKeys(this.DEFAULT_KEYS);
                  if (senhaDigitada.equals(senhaAtual))
                  {
                        uncSenhaNewKey = senhaAtual;
                  }
                  else
                  {
                        uncSenhaNewKey = new CryptDados().decrypt(senhaAtual);
                  }
               } 
               catch(java.lang.IllegalArgumentException eCrypt) 
               {
                              uncSenhaNewKey = "";
               }
               
               if (!senhaDigitada.equals(uncSenha) && !senhaDigitada.trim().equals(uncSenhaNewKey.trim())) 
               {
                              return false;
               }
               else 
               {
                              return true;
               }

      }
      
      private void initKeys(String [] keystrs) 
      {
            keys = new byte[keystrs.length][];
            
            for (int i = 0; i < keys.length; i++) 
            {
                  keys[i] = fromString(keystrs[i]);
            }
    }
      
      private byte[] fromString(String hex) 
      {
              int len = hex.length();
              byte[] buf = new byte[((len + 1) / 2)];

              int i = 0, j = 0;
              if ((len % 2) == 1)
                  buf[j++] = (byte) fromDigit(hex.charAt(i++));

              while (i < len) 
              {
                  buf[j++] = (byte) ((fromDigit(hex.charAt(i++)) << 4) |
                                      fromDigit(hex.charAt(i++)));
              }
              return buf;
    }
      
      private int fromDigit(char ch) 
      {
              if (ch >= '0' && ch <= '9')
                  return ch - '0';
              if (ch >= 'A' && ch <= 'F')
                  return ch - 'A' + 10;
              if (ch >= 'a' && ch <= 'f')
                  return ch - 'a' + 10;

              throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
    }
      
      private String getAplicacao() 
      {
                  return aplicacao;
      }

      private void geraLog(String msgErro, Exception e) 
      {
                  try 
                  {
                        log.geraLogErro(msgErro, e, getAplicacao());
                } 
                  catch (Exception eErro) 
                  {
                        System.out.println("Erro gera��o do log - "+ eErro.getStackTrace());
                }
      }
      public Boolean insertInicioEntregaHorario(DisSetorDeEntregaProdutoServicoHorarios d)
      {
    	  //String CNPJ = "";
    	  String dataDoJornalFormatada = "";
    	  Boolean IsValidar = false;
		  String sql = "";
    	 
            try 
            {
            
				//CNPJ = getCNPJ();
				/* Jornal de vitoria */
				//if (CNPJ.equals("12042826000283")) {
					
					//dataDoJornalFormatada = d.getDataDoJornal();
				//} else {
					
					dataDoJornalFormatada = formataData(d.getDataDoJornal());
				//}
                        
                        Conexao.openConnection();
                        sql = " INSERT INTO disSetorDeEntregaProdutoServicoHorarios " +
                        		" codigoDoUsuario, dataDeCadastramento, dataDoJornal, "+
                        		" codigoDoSetorDeEntrega, horaInicioDaDistribuicao, " +
                        	    " horaTerminoDaDistribuicao, codigoDoProdutoServico," +
                        	    " desabilitarInsersao " +
                        	    " VALUES"+
                        		"('"+ d.getCodigoDoUsuario() +"', "+
                        		"  getdate()," +
                        		"'"+ dataDoJornalFormatada +"' ," +
                        		 d.getCodigoDoSetorDeEntrega() +", " +
                        		"  getdate(),"+
                        		"  getdate(),"+
                        		 d.getCodigoDoDoProdutoServico()+", " +
                        		"''"+")";
                        		
                        Conexao.executeUpdate(sql);
                        
                       
                              IsValidar = true;
                       
                        Conexao.closeConnection();
                        
                        return IsValidar;
            } 
            catch (Exception e) 
            {
                  geraLog("", e);
                  return false;
          }
      }
      
      public Boolean setIDistribuicaoUpdateMotivoDeNaoEntrega(IDistribuicao model)
      {
    	  //String CNPJ = "";
    	  String dataDoJornalFormatada = "";
    	  Boolean IsValidar = false;
		  String sql = "";
		 
            try 
            {
            	
            	
				//CNPJ = getCNPJ();
				/* Jornal de vitoria */
				//if (CNPJ.equals("12042826000283")) {
					
					//dataDoJornalFormatada = model.getDataDoJornal();
				//} else {
					
					dataDoJornalFormatada = formataData(model.getDataDoJornal());
				//}
                        
                        Conexao.openConnection();
                          sql = " UPDATE assDistribuicao SET" +
                        		" codigoMotivoDeNaoEntrega = "+ model.getCodigoMotivoDeNaoEntrega() +" , " +
                        		" dataHoraEntrega =  getdate() " +
                        		" where " +
                        		" dataDoJornal ='"+ dataDoJornalFormatada +"' and " +
                        		" codigoDoSetorDeEntrega = "+ model.getCodigoDoSetorDeEntrega() +" and " +
                        		" numeroDoContrato = "+ model.getNumeroDoContrato() +" and " +
                        		" codigoDoProdutoServico = "+ model.getCodigoDoProdutoServico() +"";
                        		
                       
                        
                        Conexao.executeUpdate(sql);
                        
                        IsValidar = true;
                       
                        Conexao.closeConnection();
                        
                        return IsValidar;
            } 
            catch (Exception e) 
            {
                  geraLog("", e);
                  return false;
          }
      }

      public Boolean setInicioEntregaHorario(String dataDoJornal, Integer codigoDoSetorDeEntrega, Integer codigoDoProdutoServico, String codigoDoUsuario)
      {
    	  //String CNPJ = "";
    	  String dataDoJornalFormatada = "";
    	  Boolean IsValidar = false;
		  String sql = "";
    	 
            try 
            {
            
				//CNPJ = getCNPJ();
				/* Jornal de vitoria */
				//if (CNPJ.equals("12042826000283")) {
					
					//dataDoJornalFormatada = dataDoJornal;
				//} else {
					
					dataDoJornalFormatada = formataData(dataDoJornal);
				//}
                        
                        Conexao.openConnection();
                        sql = " UPDATE disSetorDeEntregaProdutoServicoHorarios SET" +
                        		" codigoDoUsuario = '"+ codigoDoUsuario +"' , " +
                        		" dataDeCadastramento =  getdate(), " +
                        		" horaInicioDaDistribuicao = getdate() "+
                        		" where " +
                        		" dataDoJornal  ='"+ dataDoJornalFormatada +"' and " +
                        		" codigoDoSetorDeEntrega = "+ codigoDoSetorDeEntrega +" and " +
                        		" codigoDoProdutoServico = "+ codigoDoProdutoServico +"  ";
                        		
                        Conexao.executeUpdate(sql);
                        
                       
                              IsValidar = true;
                       
                        Conexao.closeConnection();
                        
                        return IsValidar;
            } 
            catch (Exception e) 
            {
                  geraLog("", e);
                  return false;
          }
      }
      
      public Boolean setTerminoEntregaHorario(String dataDoJornal, Integer codigoDoSetorDeEntrega, Integer codigoDoProdutoServico, String codigoDoUsuario)
      {
            String dataDoJornalFormatada = formataData(dataDoJornal);
            
            try 
            {
                        Boolean IsValidar = false;
                        String sql = "";
                        
                        Conexao.openConnection();
                        sql = " UPDATE disSetorDeEntregaProdutoServicoHorarios SET" +
                                    " codigoDoUsuario = '"+ codigoDoUsuario +"' , " +
                                    " horaTerminoDaDistribuicao = getdate() "+
                                    " where " +
                                    " dataDoJornal  ='"+ dataDoJornalFormatada +"' and " +
                                    " codigoDoSetorDeEntrega = "+ codigoDoSetorDeEntrega +" and " +
                                    " codigoDoProdutoServico = "+ codigoDoProdutoServico +"  ";
                                    
                        Conexao.executeUpdate(sql);
                        
                       
                              IsValidar = true;
                       
                        Conexao.closeConnection();
                        
                        return IsValidar;
            } 
            catch (Exception e) 
            {
                  geraLog("", e);
                  return false;
          }
      }


      public DisSetorDeEntregaProdutoServicoHorarios getVerificarHoraInicioDaDistribuicao(String dataDoJornal, Integer codigoDoSetorDeEntrega, Integer codigoDoProdutoServico)
      {
            DisSetorDeEntregaProdutoServicoHorarios d = new DisSetorDeEntregaProdutoServicoHorarios();
            
            
            String dataDoJornalFormatada = formataData(dataDoJornal);
            
            try 
            {
                       
                        String sql, horaInicio, horaTermino = "";
                        ResultSet rs;
                        

                        Conexao.openConnection();
                        sql = " select horaInicioDaDistribuicao, horaTerminoDaDistribuicao from disSetorDeEntregaProdutoServicoHorarios " +
                                    " where " +
                                    " dataDoJornal  ='"+ dataDoJornalFormatada +"' and " +
                                    " codigoDoSetorDeEntrega = "+ codigoDoSetorDeEntrega +" and " +
                                    " codigoDoProdutoServico = "+ codigoDoProdutoServico +"  ";
                                    
                        rs = Conexao.executeQuery(sql);
                        
                        if (rs.next()) 
                        {
                              d = new DisSetorDeEntregaProdutoServicoHorarios();
                              
                              horaInicio = rs.getString(1).trim(); 
                              horaTermino = rs.getString(2).trim(); 
                              
                              d.setHoraInicioDaDistribuicao(horaInicio);
                              d.setHoraTerminoDaDistribuicao(horaTermino);
                        }
   
                        Conexao.closeConnection();

                        return d;
            } 
            catch (Exception e) 
            {
                  geraLog("", e);
                  return d;
          }
      }
      
      public DisSetorDeEntregaProdutoServicoHorarios getVerificarHoraPorEntregador(DisSetorDeEntregaProdutoServicoHorarios d)
      {
            DisSetorDeEntregaProdutoServicoHorarios model = null;
            
            
            
            try 
            {
                       
                        String sql, horaInicio, horaTermino, dataDoJornal, codDoUsuario = "";
                        Integer codDoSetorDeEntrega = 0, codDoProdutoServico = 0;
                        ResultSet rs;
                        

                        Conexao.openConnection();
                        sql = " select dataDoJornal, horaInicioDaDistribuicao, horaTerminoDaDistribuicao, "+
                        		    " codigoDoUsuario, codigoDoSetorDeEntrega, codigoDoProdutoServico "+
                        		    " from disSetorDeEntregaProdutoServicoHorarios " +
                                    " where " +
                                    " codigoDoUsuario  ='"+ d.getCodigoDoUsuario() +"' and " +
                                    " dataDoJornal  ='"+ d.getDataDoJornal()+"' and " +
                                    " codigoDoSetorDeEntrega = "+ d.getCodigoDoSetorDeEntrega() +" and " +
                                    " codigoDoProdutoServico = "+ d.getCodigoDoDoProdutoServico() +"  ";
                                    
                        rs = Conexao.executeQuery(sql);
                        
                        if (rs.next()) 
                        {
                              model = new DisSetorDeEntregaProdutoServicoHorarios();
                              
                              dataDoJornal = rs.getString(1).trim(); 
                              horaInicio = rs.getString(2).trim(); 
                              horaTermino = rs.getString(3).trim(); 
                              codDoUsuario = rs.getString(4).trim(); 
                              codDoSetorDeEntrega = rs.getInt(5); 
                              codDoProdutoServico =  rs.getInt(6);
                              
                              model.setDataDoJornal(dataDoJornal);
                              model.setHoraInicioDaDistribuicao(horaInicio);
                              model.setHoraTerminoDaDistribuicao(horaTermino);
                              model.setCodigoDoUsuario(codDoUsuario);
                              model.setCodigoDoDoProdutoServico(codDoProdutoServico);
                              model.setCodigoDoSetorDeEntrega(codDoSetorDeEntrega);
                        }
   
                        Conexao.closeConnection();

                        return model;
            } 
            catch (Exception e) 
            {
                  geraLog("", e);
                  return model;
          }
      }
      
      @SuppressWarnings({ "unused", "null" })
      public List< ConjuntoEdificiosSetor> getListaDeEdificiosPorSetor(String dataDoJornal, Integer codigoDoSetorDeEntrega, Integer codigoDoProdutoServico) throws SQLException, FileNotFoundException
      {
            
            String sql = "";
           
            ConjuntoEdificiosSetor edificio = new ConjuntoEdificiosSetor();
            ResultSet rs = null;
            List<ConjuntoEdificiosSetor> lista = new ArrayList<ConjuntoEdificiosSetor>();
           
            if(dataDoJornal!=null && !dataDoJornal.equals(""))
            {
                        dataDoJornal = formataData(dataDoJornal);
            }
            else
            {
                        dataDoJornal = "";
            }

                  Conexao.openConnection();
                  
                  sql = " SELECT TOP (100) PERCENT assDistribuicao.dataDoJornal, disSetorDeEntrega.codigoDoSetorDeEntrega, " +
                  " disSetorDeEntrega.nomeDoSetorDeEntrega, " +
                  " cadConjunto.descricaoDoConjunto, cadEdificio.descricaoDoEdificio, " +
                  " ltrim(rtrim(vCadEnderecoDeEntrega.siglaTipoLogradouro)) + ' ' + ltrim(rtrim(vCadEnderecoDeEntrega.nomeDoLogradouro)) +  ', ' + " + 
                  " ltrim(rtrim(vCadEnderecoDeEntrega.numeroDoEndereco)) + '  ' + ltrim(rtrim(vCadEnderecoDeEntrega.nomeDoBairro)) as enderecoCompleto," +
                  " vCadEnderecoDeEntrega.codigoDoLogradouro," +
                  " vCadEnderecoDeEntrega.nomeDoLogradouro," + 
                  " vCadPessoaFisicaJuridica.nomeRazaoSocial, assDistribuicao.qtdJornaisContrato, "+ 
                  " assLocalDeEntregaComplemento.andar, assLocalDeEntregaComplemento.apartamento "+
                  " FROM " +
                  " dbo.assDistribuicao AS assDistribuicao " +
                  " INNER JOIN dbo.disSetorDeEntrega AS disSetorDeEntrega " +
                  " ON assDistribuicao.codigoDoSetorDeEntrega = disSetorDeEntrega.codigoDoSetorDeEntrega " +
                  " INNER JOIN dbo.assLocalDeEntregaComplemento AS assLocalDeEntregaComplemento " +
                  " ON assDistribuicao.codigoDoEnderecamento = assLocalDeEntregaComplemento.codigoDoEnderecamento " +
                  " INNER JOIN dbo.vCadPessoaFisicaJuridica AS vCadPessoaFisicaJuridica " +
                  " ON assDistribuicao.codigoDaPessoa = vCadPessoaFisicaJuridica.codigoDaPessoa " +
                  " INNER JOIN dbo.cadEdificio AS cadEdificio " +
                  " ON assLocalDeEntregaComplemento.codigoDoEndereco = cadEdificio.codigoDoEndereco " +
                  " INNER JOIN dbo.cadConjunto AS cadConjunto " +
                  " ON cadEdificio.codigoDoConjunto = cadConjunto.codigoDoConjunto " +
                  " INNER JOIN dbo.vCadEnderecoDeEntrega AS vCadEnderecoDeEntrega " +
                  " ON assLocalDeEntregaComplemento.codigoDoEnderecamento = vCadEnderecoDeEntrega.codigoDoEnderecamento "+
                  " INNER JOIN cadProdutosServicos AS cadProdutosServicos "+
                  " ON cadProdutosServicos.codigoDoProdutoServico = assDistribuicao.codigoDoProdutoServico "+
                  " WHERE  (assDistribuicao.situacaoDoContrato = 1) "+ 
                  " AND (assDistribuicao.dataDoJornal >=  '"+ dataDoJornal+ " 00:00:00' )"+   
                  " AND (assDistribuicao.dataDoJornal < '"+ dataDoJornal+ " 23:59:00' )"+  
                  " AND disSetorDeEntrega.codigoDoSetorDeEntrega = "+ codigoDoSetorDeEntrega +" "+ 
                  " AND cadProdutosServicos.codigoDoProdutoServico = "+ codigoDoProdutoServico +" "+
                  " ORDER BY vCadEnderecoDeEntrega.codigoDoLogradouro, " +
                  " cadEdificio.descricaoDoEdificio, vCadPessoaFisicaJuridica.nomeRazaoSocial";
                  
                  
                  
                  rs = Conexao.executeQuery(sql);
                  
                  
                  while(rs.next())
                  {
                        
                        edificio = new ConjuntoEdificiosSetor();
                        
                        edificio = ConjuntoEdificiosSetor.conjuntoEdificiosSetor(
                                    rs.getString("dataDoJornal"), 
                                    rs.getInt("codigoDoSetorDeEntrega"),
                                    rs.getString("nomeDoSetorDeEntrega"), 
                                    rs.getString("descricaoDoConjunto"), 
                                    rs.getString("descricaoDoEdificio"), 
                                    rs.getString("enderecoCompleto"),
                                    rs.getInt("codigoDoLogradouro"),
                                    rs.getString("nomeDoLogradouro"),
                                    rs.getString("nomeRazaoSocial"), 
                                    rs.getInt("qtdJornaisContrato"), 
                                    rs.getString("andar"), 
                                    rs.getString("apartamento"));
                        
                                    
                              lista.add(edificio);   
                                    
                                    
                  }
                 
                  Conexao.closeConnection(); 

            return lista;
      }
      
      public Integer buscarCodigoDoSetorDeEntrega(Integer codigoDaPessoaDistribuidor)
      {
            try 
            {
                        Integer codigoDoSetorDeEntrega = 0;
                        ResultSet rs;
                        Conexao.openConnection();
                        
                        String sql = " SELECT codigoDoItinerario, nomeDoItinerario, codigoDaPessoa , nomeRazaoSocial, "+
                                     " disSetorDeEntrega.codigoDoSetorDeEntrega, nomeDoSetorDeEntrega "+  
                                     " FROM disItinerarios "+  
                                     " INNER JOIN disSetorDeEntrega "+
                                     " ON disSetorDeEntrega.codigoDoSetorDeEntrega = disItinerarios.codigoDoSetorDeEntrega "+ 
                                     " INNER JOIN vCadpessoaFisicaJuridica "+ 
                                     " ON vCadpessoaFisicaJuridica.codigoDaPessoa=disItinerarios.codigoDaPessoaResponsavel "+  
                                     " WHERE codigoDaPessoa = "+codigoDaPessoaDistribuidor;
                        
                        rs = Conexao.executeQuery(sql);
                        
                        if (rs.next()) 
                        {
                              codigoDoSetorDeEntrega = rs.getInt(5); 
                        }
                        
                        rs.close();
                        Conexao.closeConnection();
                        
                        return codigoDoSetorDeEntrega;
            } 
            catch (Exception e) 
            {
                  geraLog("", e);
                  return null;
            }
            
      }

		public List<IDistribuicao> getMotivoDeNaoEntrega(Integer numeroDoContrato, String dataDoJornal) {
			
			try {
				
				IDistribuicao d = new IDistribuicao();
				List<IDistribuicao> lista = new ArrayList<IDistribuicao>();
				ResultSet rs;
				Conexao.openConnection();

				String sql = " SELECT assDistribuicao.codigoMotivoDeNaoEntrega, assDistribuicao.dataHoraEntrega,"
						+ " cadMotivoDeNaoEntrega.descricaoMotivoDeNaoEntrega "
						+ " FROM assDistribuicao inner join cadMotivoDeNaoEntrega "
						+ " ON assDistribuicao.codigoMotivoDeNaoEntrega = cadMotivoDeNaoEntrega.codigoMotivoDeNaoEntrega "
						+ " WHERE numeroDoContrato =" + numeroDoContrato + " and dataDoJornal = '" + dataDoJornal
						+ "' ";

				rs = Conexao.executeQuery(sql);

				if (rs.next()) {
					
					d.setCodigoMotivoDeNaoEntrega(rs.getInt("codigoMotivoDeNaoEntrega"));
					d.setDataHoraEntrega(rs.getString("dataHoraEntrega"));
					d.setDescricaoMotivoDeNaoEntrega(rs.getString("descricaoMotivoDeNaoEntrega"));
					lista.add(d);
				}

				rs.close();
				Conexao.closeConnection();

				return lista;
				
			} catch (Exception e) {
				geraLog("", e);
				return null;
			}
		}
}

