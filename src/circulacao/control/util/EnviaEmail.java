package circulacao.control.util;

import javax.mail.*;
import javax.mail.internet.*;

import circulacao.control.seguranca.Log;

import java.util.Properties;

public class EnviaEmail
{
      public String aplicacao = "gestor";
      public String codErro = "";
      public String msgErro = "";
      private Log log = new Log();

      public String conteudoEmail = "";

      @SuppressWarnings("static-access")
      public void sendSimpleMailCPorta(String servidorSMTP, String assuntoEmail, String[] emails, String emailOrigem,
                  String mensagem, String usuarioAutenticacao, String senhaAutenticacao, String porta)
                  throws Exception, AddressException, MessagingException
      {
            try
            {
                  Address[] enderecoDestino = new Address[emails.length];

                  Properties props = new Properties();
                  props.put("mail.smtp.host", servidorSMTP);

                  if (usuarioAutenticacao.trim().equals("") && senhaAutenticacao.trim().equals(""))
                  {
                        props.put("mail.smtp.auth", "false");
                  }
                  else
                  {
                        props.put("mail.smtp.auth", "true");
                  }

                  props.put("mail.smtp.port", porta);
                  props.put("mail.smtp.starttls.enable", "true");

                  if (servidorSMTP.equals("smtp.gmail.com"))
                  {
                        props.put("mail.smtp.socketFactory.port", porta);
                        props.put("mail.smtp.socketFactory.fallback", "false");
                        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                  }

                  Authenticator auth = new autenticacao(usuarioAutenticacao, senhaAutenticacao);

                  Session sessao = Session.getInstance(props, auth);
                  sessao.setDebug(true);

                  MimeMessage message = new MimeMessage(sessao);

                  message.setFrom(new InternetAddress(emailOrigem));

                  int i = 0;
                  for (i = 0; i < emails.length; i++)
                  {
                        if (emails[i] != null)
                        {
                              enderecoDestino[i] = new InternetAddress(emails[i]);
                              message.addRecipient(Message.RecipientType.TO, enderecoDestino[i]);
                        }
                  }

                  message.setSubject(assuntoEmail);
                  message.setContent(mensagem, "text/html");

                  Transport tp = sessao.getTransport("smtp");

                  tp.connect(servidorSMTP, usuarioAutenticacao, senhaAutenticacao);

                  tp.send(message);
            }
            catch (AddressException e)
            {
                  System.out.println("Erro ao enviar o email: " + e);
                  msgErro = e.getMessage();
                  geraLog("", e);
                  throw new AddressException();
            }
            catch (javax.mail.AuthenticationFailedException eAut)
            {
                  System.out.println("Erro ao enviar o email: " + eAut.getMessage());
                  msgErro = eAut.getMessage();
                  geraLog("", eAut);

                  throw new javax.mail.AuthenticationFailedException();
            }
      }

      @SuppressWarnings("static-access")
      public void sendSimpleMail(String servidorSMTP, String assuntoEmail, String[] emails, String emailOrigem,
                  String mensagem, String usuarioAutenticacao, String senhaAutenticacao)
                  throws Exception, AddressException, MessagingException
      {
            try
            {
                  Address[] enderecoDestino = new Address[emails.length];

                  Properties props = new Properties();
                  props.put("mail.smtp.host", servidorSMTP);

                  if (usuarioAutenticacao.trim().equals("") && senhaAutenticacao.trim().equals(""))
                  {
                        props.put("mail.smtp.auth", "false");
                  }
                  else
                  {
                        props.put("mail.smtp.auth", "true");
                  }

                  Authenticator auth = new autenticacao(usuarioAutenticacao, senhaAutenticacao);

                  Session sessao = Session.getInstance(props, auth);
                  sessao.setDebug(true);

                  MimeMessage message = new MimeMessage(sessao);

                  message.setFrom(new InternetAddress(emailOrigem));

                  int i = 0;
                  for (i = 0; i < emails.length; i++)
                  {
                        if (emails[i] != null)
                        {
                              enderecoDestino[i] = new InternetAddress(emails[i]);
                              message.addRecipient(Message.RecipientType.TO, enderecoDestino[i]);
                        }
                  }

                  message.setSubject(assuntoEmail);
                  message.setContent(mensagem, "text/html");

                  Transport tp = sessao.getTransport("smtp");

                  tp.connect(servidorSMTP, usuarioAutenticacao, senhaAutenticacao);

                  tp.send(message);
            }
            catch (AddressException e)
            {
                  System.out.println("Erro ao enviar o email: " + e);
                  msgErro = e.getMessage();

                  geraLog("", e);
                  throw new AddressException();
            }
            catch (javax.mail.AuthenticationFailedException eAut)
            {
                  System.out.println("Erro ao enviar o email: " + eAut.getMessage());
                  msgErro = eAut.getMessage();

                  geraLog("", eAut);
                  throw new javax.mail.AuthenticationFailedException();
            }
      }

      private void geraLog(String msgErro, Exception e)
      {
            try
            {
                  log.geraLogErro(msgErro, e, aplicacao);
            }
            catch (Exception eErro)
            {
                  System.out.println("Erro geração do log - " + eErro.getStackTrace());
            }
      }
}

class autenticacao extends Authenticator 
{   
    
          private String username;   
          private String password;   
            
          /**  
          * @param user  
          * @param pass  
          */  
          public autenticacao(String user, String pass) 
          {   
                  this.username = user;   
                  this.password = pass;   
          }   
            
          /**  
          *   
          * @return  
          *  
          * @see javax.mail.Authenticator#getPasswordAuthentication()  
          */  
          public PasswordAuthentication getPasswordAuthentication() 
          {   
                        return new PasswordAuthentication(username, password);   
          }   
} 