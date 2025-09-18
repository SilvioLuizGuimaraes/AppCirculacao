package circulacao.control.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.JarURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import circulacao.control.seguranca.Conexao;
import circulacao.control.seguranca.Log;


@SuppressWarnings("serial")
public class Utilitarios implements Serializable
{
      private Log log = new Log();
      private GregorianCalendar dataHoje = new GregorianCalendar();
      
      public Utilitarios()
      {

      }
      
      @SuppressWarnings("unused")
      public int diaDaSemanaSql(String data) throws IOException, SQLException, Exception
      {
            
            try
            {
                  String[] strDiaDaSemana = new String[7];
                  String descDiaDaSemana = "";
                  int dia = 0;
      
                  strDiaDaSemana[0] = "Domingo";
                  strDiaDaSemana[1] = "Segunda";
                  strDiaDaSemana[2] = "Terca";
                  strDiaDaSemana[3] = "Quarta";
                  strDiaDaSemana[4] = "Quinta";
                  strDiaDaSemana[5] = "Sexta";
                  strDiaDaSemana[6] = "Sabado";
      
                  String sqlDia = "";
                  
                  sqlDia = "SET DATEFIRST 7; select\tDATEPART(dw, '" + data + "') ";
                  
                  
                  ResultSet rs = Conexao.executeQuery(sqlDia);
                  if (rs.next())
                  {
                        rs.next();
                        dia = Integer.parseInt(rs.getString(1));
                        
                        descDiaDaSemana = strDiaDaSemana[(dia - 1)];
                        
                        
                        return dia;
                  }
                  
            
            }
            catch (Exception e)
            {
                  geraLog(e.getMessage(), e);
            }
            
            return -1;
      }
      
      public String trataCampo(ResultSet rs, int indice)
      {
            try
            {
                  Utilitarios num = new Utilitarios();
                  
                  ResultSetMetaData rsmd = rs.getMetaData();
                  int tipoColuna = rsmd.getColumnType(indice);
                  String valorColuna = "";
                  if (tipoColuna == 3)
                  {
                        try
                        {
                              double valorDouble = rs.getDouble(indice);
                              valorColuna = ""+valorDouble;
                              if (valorColuna.indexOf(".") < 0)
                              {
                                    
                                    valorColuna = num.formatoDouble(valorColuna, rsmd.getPrecision(indice));
                              }
                              if (valorColuna.indexOf("E") > -1)
                              {
                                    NumberFormat form = NumberFormat.getInstance(Locale.US);
                                    valorColuna = form.format(valorDouble);
                                    valorColuna = num.replace(valorColuna, ",", "");
                              }
                        }
                        catch (NullPointerException eNull)
                        {
                              valorColuna = "0";
                        }
                  }
                  return rs.getString(indice);
            }
            catch (SQLException e)
            {
                  geraLog("Erro no trata campo da classe banco", e);
            }
            return null;
      }

      @SuppressWarnings("unused")
      public String preencheCampo(String txt, String caract, int total, String lado)
      {
            int len = 0;
            if (txt == null)
            {
                  return "";
            }
            for (int i = txt.length(); i < total; i++)
            {
                  if (lado.equals("E"))
                  {
                        txt = caract + txt;
                  }
                  else
                  {
                        txt = txt + caract;
                  }
            }
            return txt;
      }
      
      public String formatoDouble(String valor, int numDecimais)
      {
            String decimais = "";
            if (valor.length() < 3)
            {
                  valor = preencheCampo(valor, "0", 3, "E");
            }
            decimais = valor.substring(valor.length() - numDecimais, valor.length());
            valor = valor.substring(0, valor.length() - numDecimais);
            valor = valor + "." + decimais;
            return valor;
      }
      
      public double arredonda(String valor, int numCasas, int opcaoArredondamento)
      {
            BigDecimal valorDec = new BigDecimal(valor);
            if (opcaoArredondamento == 1)
            {
                  opcaoArredondamento = 1;
            }
            else
                  if (opcaoArredondamento == 2)
                  {
                        numCasas = 0;
                        opcaoArredondamento = 1;
                  }
                  else
                        if (opcaoArredondamento == 3)
                        {
                              opcaoArredondamento = 4;
                        }
            valorDec = valorDec.divide(BigDecimal.valueOf(1L), numCasas, opcaoArredondamento);
            return valorDec.doubleValue();
      }
      
      public String ajustaDataUSA2(String dataDDMMAAAA)
      {
            String dia = dataDDMMAAAA.substring(0, 2);
            String mes = dataDDMMAAAA.substring(3, 5);
            String ano = dataDDMMAAAA.substring(6, 10);
            return mes + "/" + dia + "/" + ano;
      }
      
      @SuppressWarnings("unused")
      public String somaMeses(String s, int i) throws Exception
      {
            try
            {
                  String s1 = "";
                  String s2 = "";
                  String s3 = "";
                  int j = 0;
                  boolean flag = false;
                  boolean flag1 = false;
                  s1 = s.substring(0, 2);
                  s2 = s.substring(3, 5);
                  s3 = s.substring(6, 10);
                  j = Integer.parseInt(s2) + i;
                  if (j > 12)
                  {
                        do
                        {
                              int k = Integer.parseInt(s3) + 1;
                              s3 =""+k;
                              j -= 12;
                        }
                        while (j > 12);
                  }
                  if (j < 10)
                  {
                        s2 = "0" + j;
                  }
                  else
                  {
                        s2 = ""+j;
                  }
                  if ((s2.equals("04")) || (s2.equals("06")) || (s2.equals("09")) || (s2.equals("11")))
                  {
                        if (s1.equals("31"))
                        {
                              s1 = "30";
                        }
                  }
                  else
                        if (s2.equals("02"))
                        {
                              int i1 = Integer.parseInt(s1);
                              if (i1 >= 29)
                              {
                                    int l = Integer.parseInt(s3);
                                    if (!anoBisexto(l))
                                    {
                                          s1 = "28";
                                    }
                                    else
                                          if (i1 > 29)
                                          {
                                                s1 = "29";
                                          }
                              }
                        }
                  return s1 + "/" + s2 + "/" + s3;
            }
            catch (Exception e)
            {
                  log.geraLogErro("Erro no somaMeses: "+e.getMessage(), e, "gestor-on");
            }
            return "";
      }
      
      public boolean anoBisexto(int yyyy)
      {
            if (yyyy % 4 != 0)
            {
                  return false;
            }
            if (yyyy % 400 == 0)
            {
                  return true;
            }
            if (yyyy % 100 == 0)
            {
                  return false;
            }
            return true;
      }
      
      public String dataSistema()
      {
            Integer mesData = this.dataHoje.get(2) + 1;
            
            String dia = ""+this.dataHoje.get(5);
            String mes = ""+mesData;
            String ano = ""+this.dataHoje.get(1);
            String data = ajustaDataBrasil(dia, mes, ano);
            return data;
      }
      
      public String ajustaDataBrasil(String dia, String mes, String ano)
      {
            String dataFormatada = formataZeros(dia, 2) + "/";
            dataFormatada = dataFormatada + formataZeros(mes, 2) + "/" + ano;
            return dataFormatada;
      }
      
      public String formataZeros(String txt, int quant)
      {
            int len = 0;
            for (int i = 0; i < quant; i++)
            {
                  txt = "0" + txt;
            }
            len = txt.length();
            return txt.substring(len - quant, len);
      }
      
      /**
       * Metodo utilizado para criptografar senha 32 bits md5
       * 
       * @param senhaInformada
       * @return
       * @throws FileNotFoundException
       * @throws IOException
       * @throws Exception
       */
      public String criptografaSenha(String senhaInformada) throws FileNotFoundException, IOException, Exception
      {
            String senha = "";

            try
            {
                  Conexao.openConnection();
                  
                  String sql = "SELECT SUBSTRING(sys.fn_sqlvarbasetostr(HASHBYTES('MD5', '"+senhaInformada+"')),3,32)   as senha";

                  ResultSet rs = Conexao.executeQuery(sql);

                  while (rs.next())
                  {
                        senha = rs.getString("senha");
                  }

            }
            catch (Exception e)
            {
                  log.geraLogErro(e.getMessage(), e, "gestor-on");
                  senha = e.getMessage();
                  return senha;
            }
            finally
            {
                        
                  Conexao.closeConnection();
            }

            senha = senha.trim();

            return senha;
      }
          
      /**
       * Metodo utilizado para gerar senha aleatoria com 8 caracteres numeros e letras
       * 
       * @return
       */ 
      public String getGeradorSenha()
      {
            String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            String numero = "0123456789";
            Random gerador = new Random();
            String imprimiValor = "";
            Integer valor = -1;

            for (int i = 0; i < 4; i++)
            {
                  valor = gerador.nextInt(letras.length());
                  imprimiValor += letras.substring(valor, valor + 1);
            }

            for (int i = 0; i < 4; i++)
            {
                  valor = gerador.nextInt(numero.length());
                  imprimiValor += numero.substring(valor, valor + 1);
            }

            List<String> letters = Arrays.asList(imprimiValor.split(""));
            Collections.shuffle(letters);
            StringBuilder t = new StringBuilder(imprimiValor.length());
            for (String k : letters)
            {
                  t.append(k);
            }

            imprimiValor = t.toString();

            return imprimiValor;
      }

      private static final int[] pesoCPF =
      { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
      private static final int[] pesoCNPJ =
      { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

      public String getAppPathByRuntime(String className)
      {
            String pathRuntime = getPathRuntime(className);
            pathRuntime = pathRuntime.substring(0, pathRuntime.indexOf("WEB-INF") - 1);
            return pathRuntime;
      }

      private String getPathRuntime(String className)
      {
            try
            {

                  Class<?> sourceClass = null;

                  try
                  {
                        sourceClass = Class.forName(className);
                  }
                  catch (ClassNotFoundException e)
                  {
                        throw new RuntimeException(e);
                  }
                  // monta um String com o nome da classe que voce quer verificar
                  className = className.substring(className.lastIndexOf(".") + 1, className.length());
                  className += ".class";
                  className = new String(className);

                  // pesquisa e obtem o URL para esta classe
                  URL url = sourceClass.getResource(className);

                  // verifica se esta classe está rodando dentro de um jar
                  if (url.getProtocol().equals("jar"))
                  {
                        // le a url.
                        JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
                        url = jarConnection.getJarFileURL();
                  }

                  // cria um objeto file
                  File f = new File(url.getPath());

                  return new String(f.getParent());
            }
            catch (IOException ioe)
            {
                  throw new Error(ioe);
            }
      }

      private static int calcularDigitoCPFCNPJ(String str, int[] peso)
      {
            int soma = 0;
            for (int indice = str.length() - 1, digito; indice >= 0; indice--)
            {
                  digito = Integer.parseInt(str.substring(indice, indice + 1));
                  soma += digito * peso[peso.length - str.length() + indice];
            }
            soma = 11 - soma % 11;
            return soma > 9 ? 0 : soma;
      }

      public static boolean isValidCPF(String cpf)
      {
            if ((cpf == null) || (cpf.length() != 11))
                  return false;

            Integer digito1 = calcularDigitoCPFCNPJ(cpf.substring(0, 9), pesoCPF);
            Integer digito2 = calcularDigitoCPFCNPJ(cpf.substring(0, 9) + digito1, pesoCPF);
            return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
      }

      public static boolean isValidCNPJ(String cnpj)
      {
            if ((cnpj == null) || (cnpj.length() != 14))
                  return false;

            Integer digito1 = calcularDigitoCPFCNPJ(cnpj.substring(0, 12), pesoCNPJ);
            Integer digito2 = calcularDigitoCPFCNPJ(cnpj.substring(0, 12) + digito1, pesoCNPJ);
            return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
      }

      @SuppressWarnings({ "resource", "deprecation" })
      public String obtemTemplates(String endereco) throws IOException, Exception
      {
            try
            {
                  String retorno = "";
                  String linhaArq = "";
                  FileInputStream arq = new FileInputStream(endereco);
                  DataInputStream dataInput = new DataInputStream(arq);

                  while ((linhaArq = dataInput.readLine()) != null)
                  {
                        retorno = (new StringBuilder(String.valueOf(retorno))).append(linhaArq).toString();
                  }

                  return retorno;
            }
            catch (Exception e)
            {
                  e.printStackTrace();

                  geraLog("", e);

                  throw new Exception();
            }
      }

      private void geraLog(String msgErro, Exception e)
      {
            try
            {
                  log.geraLogErro(msgErro, e, "gestor");
            }
            catch (Exception eErro)
            {
                  System.out.println("Erro geração do log - " + eErro.getStackTrace());
            }
      }

      public String replace(String texto, String stringDe, String stringPara)
      {
            String retorno = "";
            int posInicial;
            if (texto == null)
            {
                  return "";
            }
            if (stringPara == null)
            {
                  stringPara = "";
            }
            try
            {
                  if (stringDe.equals("") && texto.equals(""))
                  {
                        texto = stringPara;
                  }
                  else
                        if (!stringDe.equals(""))
                        {
                              while ((posInicial = texto.indexOf(stringDe)) != -1)
                              {
                                    retorno += texto.substring(0, posInicial) + stringPara;
                                    texto = texto.substring(posInicial + stringDe.length());
                              }
                        }
                  retorno += texto;
            }
            catch (NullPointerException e)
            {
                  e.printStackTrace();
            }
            return retorno;
      }

      @SuppressWarnings("unused")
      public static Boolean validaData(String dataTexto, String formato)
      {
            Date data = null;
            SimpleDateFormat format = new SimpleDateFormat(formato);
            try 
            {
                  format.setLenient(false);
                  data = format.parse(dataTexto);
                  
                  return true;
            } 
            catch (Exception e) 
            {
                  return false;
            }
      }
      
      /**
       * Metodo utilizado para retornar a data e hr do sistema formato dd/MM/yyyy
       * 
       * @return
       */
      public static String getDate()
      {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            return dateFormat.format(date);
      }

      /**
       * Metodo utilizado para retornar a data e hr do sistema formato MM/dd/yyyy
       * 
       * @return
       */
      public static String getDateMMDDYYY()
      {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            return dateFormat.format(date);
      }

      /**
       * Metodo utilizado para retornar a hora do sistema formato HH:mm:ss
       * 
       * @return
       */
      public static String getTime()
      {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            return dateFormat.format(date);
      }

      public static String adicionaDias(String data, int dias)
      {

            Calendar dataAdiciona = montaCalendario(data);
            adicionaDias(dataAdiciona, dias);
            return montaDataBrasil(dataAdiciona);
      }

      public static Calendar adicionaDias(Calendar data, int dias)
      {
            data.add(5, dias);
            return data;
      }

      public static String montaDataBrasil(Calendar data)
      {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(data.getTime());
            return dateString;
      }

      private static Calendar montaCalendario(String data)
      {
            Calendar dataNova = Calendar.getInstance();
            String diaS = data.substring(0, 2);
            String mesS = data.substring(3, 5);
            String anoS = data.substring(6, 10);
            int mes = Integer.parseInt(mesS);
            int dia = Integer.parseInt(diaS);
            int ano = Integer.parseInt(anoS);
            dataNova.set(1, ano);
            dataNova.set(2, mes - 1);
            dataNova.set(5, dia);
            return dataNova;
      }

      public static String formata(String valor, String strFormatoOrigem, String strFormatoDestino)
      {
            String retorno = "";

            if (valor != null)
            {
                  if (strFormatoOrigem.equals("timestamp") || strFormatoOrigem.equals("T"))
                  {

                        if (strFormatoDestino.toUpperCase().equals("DDMMAA"))
                        {
                              String dia = valor.substring(8, 10);
                              String mes = valor.substring(5, 7);
                              String ano = valor.substring(2, 4);
                              retorno = (new StringBuilder(String.valueOf(dia))).append(mes).append(ano).toString();
                        }
                        else
                              if (strFormatoDestino.toUpperCase().equals("AAAAMM"))
                              {
                                    String mes = valor.substring(5, 7);
                                    String ano = valor.substring(0, 4);
                                    retorno = (new StringBuilder(String.valueOf(ano))).append(mes).toString();
                              }
                              else
                                    if (strFormatoDestino.toUpperCase().equals("AAAAMMDD"))
                                    {
                                          String dia = valor.substring(8, 10);
                                          String mes = valor.substring(5, 7);
                                          String ano = valor.substring(0, 4);
                                          retorno = (new StringBuilder(String.valueOf(ano))).append(mes).append(dia)
                                                      .toString();
                                    }
                                    else
                                          if (strFormatoDestino.toUpperCase().equals("MM/DD/AAAA")
                                                      || strFormatoDestino.toUpperCase().equals("MM/DD/YYYY"))
                                          {
                                                String dia = valor.substring(8, 10);
                                                String mes = valor.substring(5, 7);
                                                String ano = valor.substring(0, 4);
                                                retorno = (new StringBuilder(String.valueOf(mes))).append("/")
                                                            .append(dia).append("/").append(ano).toString();
                                          }
                                          else
                                                if (strFormatoDestino.toUpperCase().equals("HHMMSS"))
                                                {
                                                      String hora = valor.substring(0, 2);
                                                      String minuto = valor.substring(3, 5);
                                                      String segundo = valor.substring(6, 8);
                                                      retorno = (new StringBuilder(String.valueOf(hora))).append(minuto)
                                                                  .append(segundo).toString();
                                                }
                                                else
                                                      if (strFormatoDestino.toUpperCase().equals("DDMMAAAA"))
                                                      {
                                                            String dia = valor.substring(8, 10);
                                                            String mes = valor.substring(5, 7);
                                                            String ano = valor.substring(0, 4);
                                                            retorno = (new StringBuilder(String.valueOf(dia)))
                                                                        .append(mes).append(ano).toString();
                                                      }
                                                      else
                                                      {
                                                            if (strFormatoDestino.toUpperCase()
                                                                        .equals("DD/MM/AAAA HH:MM:SS"))
                                                            {
                                                                  String mes = valor.substring(5, 7);
                                                                  String dia = valor.substring(8, 10);
                                                                  String ano = valor.substring(0, 4);

                                                                  String hora = valor.substring(11, 13);
                                                                  String minuto = valor.substring(14, 16);
                                                                  String segundo = valor.substring(17, 19);

                                                                  retorno = (new StringBuilder(String.valueOf(dia)))
                                                                              .append("/").append(mes).append("/")
                                                                              .append(ano).append(" ").append(hora)
                                                                              .append(":").append(minuto).append(":")
                                                                              .append(segundo).toString();
                                                            }
                                                            else
                                                            {
                                                                  if (strFormatoDestino.toUpperCase()
                                                                              .equals("DD/MM/AAAA"))
                                                                  {
                                                                        String mes = valor.substring(5, 7);
                                                                        String dia = valor.substring(8, 10);
                                                                        String ano = valor.substring(0, 4);

                                                                        retorno = (new StringBuilder(
                                                                                    String.valueOf(dia))).append("/")
                                                                                                .append(mes).append("/")
                                                                                                .append(ano).toString();
                                                                  }
                                                            }
                                                      }
                  }
                  else
                        if (strFormatoOrigem.toUpperCase().equals("DD/MM/AAAA")
                                    || strFormatoOrigem.toUpperCase().equals("DD/MM/YYYY"))
                        {
                              if (strFormatoDestino.toUpperCase().equals("MM/DD/AAAA")
                                          || strFormatoDestino.toUpperCase().equals("MM/DD/YYYY"))
                              {
                                    String dia = valor.substring(0, 2);
                                    String mes = valor.substring(3, 5);
                                    String ano = valor.substring(6, 10);
                                    retorno = (new StringBuilder(String.valueOf(mes))).append("/").append(dia)
                                                .append("/").append(ano).toString();
                              }
                              else
                                    if (strFormatoDestino.toUpperCase().equals("AAAAMMDD"))
                                    {
                                          String dia = valor.substring(0, 2);
                                          String mes = valor.substring(3, 5);
                                          String ano = valor.substring(6, 10);
                                          retorno = (new StringBuilder(String.valueOf(ano))).append(mes).append(dia)
                                                      .toString();
                                    }
                                    else
                                          if (strFormatoDestino.toUpperCase().equals("AAAAMM"))
                                          {
                                                String mes = valor.substring(3, 5);
                                                String ano = valor.substring(6, 10);
                                                retorno = (new StringBuilder(String.valueOf(ano))).append(mes)
                                                            .toString();
                                          }
                                          else
                                                if (strFormatoDestino.toUpperCase().equals("DDMMAA"))
                                                {
                                                      String dia = valor.substring(0, 2);
                                                      String mes = valor.substring(3, 5);
                                                      String ano = valor.substring(8, 10);
                                                      retorno = (new StringBuilder(String.valueOf(dia))).append(mes)
                                                                  .append(ano).toString();
                                                }
                                                else
                                                      if (strFormatoDestino.toUpperCase().equals("DDMMAAAA"))
                                                      {
                                                            String dia = valor.substring(0, 2);
                                                            String mes = valor.substring(3, 5);
                                                            String ano = valor.substring(6, 10);
                                                            retorno = (new StringBuilder(String.valueOf(dia)))
                                                                        .append(mes).append(ano).toString();
                                                      }
                                                      else
                                                            if (strFormatoDestino.toUpperCase().equals("YYYY-MM-DD"))
                                                            {
                                                                  String dia = valor.substring(0, 2);
                                                                  String mes = valor.substring(3, 5);
                                                                  String ano = valor.substring(6, 10);
                                                                  retorno = (new StringBuilder(String.valueOf(ano)))
                                                                              .append("-").append(mes).append("-")
                                                                              .append(dia).toString();
                                                            }
                        }
                        else
                              if (strFormatoOrigem.toUpperCase().equals("MM/DD/AAAA")
                                          || strFormatoOrigem.toUpperCase().equals("MM/DD/YYYY"))
                              {
                                    if (strFormatoDestino.toUpperCase().equals("DD/MM/AAAA")
                                                || strFormatoDestino.toUpperCase().equals("DD/MM/YYYY"))
                                    {
                                          String mes = valor.substring(0, 2);
                                          String dia = valor.substring(3, 5);
                                          String ano = valor.substring(6, 10);
                                          retorno = (new StringBuilder(String.valueOf(dia))).append("/").append(mes)
                                                      .append("/").append(ano).toString();
                                    }
                                    else
                                          if (strFormatoDestino.toUpperCase().equals("TIMESTAMP")
                                                      || strFormatoDestino.toUpperCase().equals("T"))
                                          {
                                                String mes = valor.substring(0, 2);
                                                String dia = valor.substring(3, 5);
                                                String ano = valor.substring(6, 10);
                                                retorno = (new StringBuilder(String.valueOf(ano))).append("-")
                                                            .append(mes).append("-").append(dia).append(" ")
                                                            .append("00:00:00 0.").toString();
                                          }
                              }
                              else
                                    if (strFormatoOrigem.toUpperCase().equals("AAAAMMDD")
                                                || strFormatoOrigem.toUpperCase().equals("YYYYMMDD"))
                                    {
                                          if (strFormatoDestino.toUpperCase().equals("DDMMAA")
                                                      || strFormatoDestino.toUpperCase().equals("DDMMYY"))
                                          {
                                                String ano = valor.substring(2, 4);
                                                String mes = valor.substring(4, 6);
                                                String dia = valor.substring(6, 8);
                                                retorno = (new StringBuilder(String.valueOf(dia))).append(mes)
                                                            .append(ano).toString();
                                          }
                                          if (strFormatoDestino.toUpperCase().equals("DD/MM/AAAA")
                                                      || strFormatoDestino.toUpperCase().equals("DD/MM/YYYY"))
                                          {
                                                String ano = valor.substring(0, 4);
                                                String mes = valor.substring(4, 6);
                                                String dia = valor.substring(6, 8);
                                                retorno = (new StringBuilder(String.valueOf(dia))).append("/")
                                                            .append(mes).append("/").append(ano).toString();
                                          }
                                    }
                                    else
                                          if (strFormatoOrigem.toUpperCase().equals("AAMMDD")
                                                      || strFormatoOrigem.toUpperCase().equals("YYMMDD"))
                                          {
                                                if (strFormatoDestino.toUpperCase().equals("DDMMAA")
                                                            || strFormatoDestino.toUpperCase().equals("DDMMYY"))
                                                {
                                                      String ano = valor.substring(0, 2);
                                                      String mes = valor.substring(2, 4);
                                                      String dia = valor.substring(4, 6);
                                                      retorno = (new StringBuilder(String.valueOf(dia))).append(mes)
                                                                  .append(ano).toString();
                                                }
                                          }
                                          else
                                                if (strFormatoOrigem.toUpperCase().equals("DDMMAAAA")
                                                            || strFormatoOrigem.toUpperCase().equals("DDMMYYYY"))
                                                {
                                                      if (strFormatoDestino.toUpperCase().equals("DDMMAA")
                                                                  || strFormatoDestino.toUpperCase().equals("DDMMYY"))
                                                      {
                                                            String dia = valor.substring(0, 2);
                                                            String mes = valor.substring(2, 4);
                                                            String ano = valor.substring(6, 8);
                                                            retorno = (new StringBuilder(String.valueOf(dia)))
                                                                        .append(mes).append(ano).toString();
                                                      }
                                                      else
                                                            if (strFormatoDestino.toUpperCase().equals("MM/DD/AAAA")
                                                                        || strFormatoDestino.toUpperCase()
                                                                                    .equals("MM/DD/AAAA"))
                                                            {
                                                                  String dia = valor.substring(0, 2);
                                                                  String mes = valor.substring(2, 4);
                                                                  String ano = valor.substring(6, 8);
                                                                  retorno = (new StringBuilder(String.valueOf(mes)))
                                                                              .append("/").append(dia).append("/")
                                                                              .append(ano).toString();
                                                            }
                                                }
                                                else
                                                      if (strFormatoOrigem.toUpperCase().equals("HH:MM:SS")
                                                                  || strFormatoOrigem.toUpperCase().equals("hh:mm:ss"))
                                                      {
                                                            if (strFormatoDestino.toUpperCase().equals("HH:MM")
                                                                        || strFormatoDestino.toUpperCase()
                                                                                    .equals("hh:mm"))
                                                            {
                                                                  String hora = valor.substring(0, 2);
                                                                  String minuto = valor.substring(3, 5);
                                                                  retorno = (new StringBuilder(String.valueOf(hora)))
                                                                              .append(":").append(minuto).toString();
                                                            }
                                                            else
                                                                  if (strFormatoDestino.toUpperCase().equals("HHMMSS")
                                                                              || strFormatoDestino.toUpperCase()
                                                                                          .equals("hhmmss"))
                                                                  {
                                                                        String hora = valor.substring(0, 2);
                                                                        String minuto = valor.substring(3, 5);
                                                                        String segundo = valor.substring(6, 8);
                                                                        retorno = (new StringBuilder(
                                                                                    String.valueOf(hora)))
                                                                                                .append(minuto)
                                                                                                .append(segundo)
                                                                                                .toString();
                                                                  }
                                                      }

            }
            else
            {
                  if (valor == null)
                  {
                        retorno = "";
                  }
            }

            return retorno;
      }

      /**
       * recebe duas datas noformato dia mes a ano e calcula os dias existentes entre
       * elas
       */
      public int diasEntreDatas(String s, String s1)
      {
            int i = Integer.parseInt(s.substring(6, 10));
            int j = Integer.parseInt(s.substring(3, 5));
            int k = Integer.parseInt(s.substring(0, 2));
            int l = Integer.parseInt(s1.substring(6, 10));
            int i1 = Integer.parseInt(s1.substring(3, 5));
            int j1 = Integer.parseInt(s1.substring(0, 2));
            GregorianCalendar gregoriancalendar = new GregorianCalendar(i, j, k);
            GregorianCalendar gregoriancalendar1 = new GregorianCalendar(l, i1, j1);
            long l1 = gregoriancalendar1.getTimeInMillis() - gregoriancalendar.getTimeInMillis();
            long l2 = l1 / 0x5265c00L;
            return (int) l2;
      }

      @SuppressWarnings("unused")
      private Calendar dataNova = Calendar.getInstance();
      private Calendar dataNova1 = Calendar.getInstance();
      private Calendar dataNova2 = Calendar.getInstance();

      @SuppressWarnings("static-access")
      public int calculaDiferenca(String data1, String data2)
      {
            int x = 0;

            // transforma as datas
            Utilitarios montar1 = new Utilitarios();
            dataNova1 = montar1.montaCalendario(data1);
            Utilitarios montar2 = new Utilitarios();
            dataNova2 = montar2.montaCalendario(data2);

            int operador = 1;
            // Verifica se as datas estao invertidas
            if (dataNova1.after(dataNova2))
            {
                  Calendar aux = dataNova1;
                  dataNova1 = dataNova2;
                  dataNova2 = aux;
                  operador = -1;
            }

            // Faz o for ate que uma data seja igual a outra
            for (x = 1; !dataNova1.getTime().toString().equals(dataNova2.getTime().toString()); x++)
            {
                  adicionaDias(dataNova1, +1);
            }

            // retorna o numero de dias em diferenca
            return x * operador;
      }

      public int comparaDatas(String data1, String data2)
      {
            String auxData1 = "";
            String auxData2 = "";
            String dia = "";
            String mes = "";
            String ano = "";
            int data1Num = 0;
            int data2Num = 0;

            dia = data1.substring(0, 2);
            mes = data1.substring(3, 5);
            ano = data1.substring(6, 10);
            auxData1 = ano + mes + dia;

            dia = data2.substring(0, 2);
            mes = data2.substring(3, 5);
            ano = data2.substring(6, 10);
            auxData2 = ano + mes + dia;

            data1Num = Integer.parseInt(auxData1);
            data2Num = Integer.parseInt(auxData2);
            return data2Num - data1Num;
      }

      public static String formataDecimal(String numero)
      {
            double valorDouble = Double.parseDouble(numero);
            DecimalFormat fmt = new DecimalFormat("0.00");
            String valorFormat = fmt.format(valorDouble);
            return valorFormat;
      }
}

