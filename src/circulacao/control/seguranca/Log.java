package circulacao.control.seguranca;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.GregorianCalendar;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Log
{
      
      private GregorianCalendar gregorianCalendar = new GregorianCalendar();
      private Calendar calendar;

      static Properties props = new Properties();

      public void geraLogErro(String msgErro, Exception msgExcecao, String aplicacao)
                  throws IOException, FileNotFoundException, Exception
      {
            calendar = Calendar.getInstance();

            int cont = 0;
            StackTraceElement[] trace = null;
            String msgExcecaoAux = "";

            if (msgExcecao != null)
            {
                  trace = msgExcecao.getStackTrace();
                  msgExcecaoAux = msgExcecao.toString();
            }

            String dia = "" + gregorianCalendar.get(Calendar.DAY_OF_MONTH);
            if (dia.length() == 1)
            {
                  dia = "0" + dia;
            }

            String mes = (gregorianCalendar.get(Calendar.MONTH) + 1) + "";
            if (mes.length() == 1)
            {
                  mes = "0" + mes;
            }

            String ano = "" + gregorianCalendar.get(Calendar.YEAR);

            String dataSistema = dia + "-" + mes + "-" + ano;
            String horaSistema = calendar.getTime().toString().substring(11, 19);

            String caminhoTemp = "C:/temp";

            InitialContext initCtx = new InitialContext();
            Context context = (Context) initCtx.lookup("java:/comp/env");
            
            if(context.lookup("caminhoLogErroWebService") != null)
                  caminhoTemp = (String) context.lookup("caminhoLogErroWebService");

            if (caminhoTemp == null)
            {
                  caminhoTemp = "C:/temp";
            }

            String caminhoLog = caminhoTemp + "\\Webservice-logs-" + dataSistema + ".txt";

            while ((caminhoLog.indexOf("/")) > -1)
            {
                  caminhoLog = caminhoLog.replace("/", "\\");
            }

            FileOutputStream arqLog = new FileOutputStream(caminhoLog, true);
            msgErro = "\n\n*** " + horaSistema + " - " + msgErro + " ***\n";
            msgErro += "*** " + msgExcecaoAux + " ***\n";

            arqLog.write(msgErro.getBytes());

            String conteudo = "";
            if (trace != null)
            {
                  for (cont = 0; cont < trace.length; cont++)
                  {
                        conteudo = trace[cont].getFileName() + ":" + trace[cont].getClassName() + ":"
                                    + trace[cont].getMethodName() + ":(" + trace[cont].getLineNumber() + ")\n";
                        arqLog.write(conteudo.getBytes());
                  }
            }

            arqLog.close();
      }
}
