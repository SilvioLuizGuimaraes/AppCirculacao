package circulacao.control.seguranca;

import java.io.FileNotFoundException;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import circulacao.control.util.Utilitarios;


public class Conexao
{
      static Properties props = new Properties();
      private static String aplicacao = "";

      public static Connection conexao = null;
      public static Statement stmt = null;
      public static ResultSet rs = null;
      private static Log log = new Log();
      public static Boolean statusConexao = false;

      public static Connection openConnection() throws FileNotFoundException, SQLException
      {
            try
            {

                  InitialContext initCtx = new InitialContext();
                  DataSource ds = (DataSource) initCtx.lookup("java:/comp/env/jdbc/wsgestor-ds");
                  conexao = ds.getConnection();
                  
                  conexao.setTransactionIsolation(1);

                  statusConexao  = true;
                  
                  return conexao;
                  
            }
            catch (Exception e)
            {
                  geraLog("", e);
                  statusConexao  = false;
                  throw new SQLException(); 
            }

      }

      public static ResultSet executeQuery(String query) throws SQLException, FileNotFoundException
      {
            try
            {
                  if (conexao.isClosed())
                  {
                        openConnection();
                  }

                  stmt = conexao.createStatement();

                  query = (String) query;

                  rs = stmt.executeQuery(query);
            }
            catch (SQLException err)
            {
                  System.out.println("Erro Query: " + err.getMessage());
                  geraLog(query, err);
                  throw new SQLException();
            }
            catch (FileNotFoundException e)
            {
                  geraLog(query, e);
            }

            return rs;
      }
      
      
      public static String retornaColuna (String query) throws SQLException, FileNotFoundException
      {
            String retorno ="";
            
            try
            {
                  if (conexao.isClosed())
                  {
                        openConnection();
                  }

                  stmt = conexao.createStatement();

                  query = (String) query;

                  rs = stmt.executeQuery(query);
                  
                  if(rs.next())
                  {
                        retorno = rs.getString(1);
                  }
            }
            catch (SQLException err)
            {
                  System.out.println("Erro Query: " + err.getMessage());
                  geraLog(query, err);
                  throw new SQLException();
            }
            catch (FileNotFoundException e)
            {
                  geraLog(query, e);
            }

            return retorno;
      }
      
      public static Integer retornaColunaInteiro (String query) throws SQLException, FileNotFoundException
      {
            Integer retorno = 0;
            
            try
            {
                  if (conexao.isClosed())
                  {
                        openConnection();
                  }

                  stmt = conexao.createStatement();

                  query = (String) query;

                  rs = stmt.executeQuery(query);
                  
                  if(rs.next())
                  {
                        retorno = rs.getInt(1);
                  }
            }
            catch (SQLException err)
            {
                  System.out.println("Erro Query: " + err.getMessage());
                  geraLog(query, err);
                  throw new SQLException();
            }
            catch (FileNotFoundException e)
            {
                  geraLog(query, e);
            }

            return retorno;
      }
      
      
      public static void rodaSQL(String sql) throws  SQLException, Exception
      {
            
            Utilitarios str = new Utilitarios();
            
            try
            {
                  
                  if (sql.indexOf("^") > 0)
                  {
                        StringTokenizer sqlToken = new StringTokenizer(sql, "^");
                        while (sqlToken.hasMoreTokens())
                        {
                              sql = sqlToken.nextToken();
                              if (!sql.trim().equals(""))
                              {
                                    executeUpdate(str.replace(sql, "^", ""));
                              }
                        }
                  }
                  else
                        if (!sql.trim().equals(""))
                        {
                              executeUpdate(str.replace(sql, "^", ""));
                        }
                  
            }
            catch (SQLException err)
            {
                  geraLog(err.getMessage(), err);
                  throw new SQLException();
            }
            
      }

      public static void executeUpdate(String query) throws SQLException, FileNotFoundException
      {
            try
            {
                  if (conexao.isClosed())
                  {
                        openConnection();
                  }

                  stmt = conexao.createStatement();

                  query = (String) query;
                  stmt.executeUpdate(query);
            }
            catch (SQLException e)
            {
                  System.out.println("Erro Query: " + e.getMessage());
                  geraLog(query, e);
                  throw new SQLException();
            }
            catch (FileNotFoundException e)
            {
                  geraLog(query, e);
            }
      }

      public static void closeConnection() throws SQLException
      {
            if (conexao != null)
            {
                  try
                  {
                        statusConexao  = false;
                        conexao.close();
                  }
                  catch (SQLException e)
                  {
                        statusConexao  = false;
                        geraLog("", e);
                        throw new SQLException();
                  }
            }
      }
      
      

      private static String getAplicacao()
      {
            return aplicacao;
      }

      private static void geraLog(String msgErro, Exception e)
      {
            try
            {
                  log.geraLogErro(msgErro, e, getAplicacao());
            }
            catch (Exception eErro)
            {
                  System.out.println("Erro gera��o do log - " + eErro.getStackTrace());
            }
      }

}
