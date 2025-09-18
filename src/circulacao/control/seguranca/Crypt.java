package circulacao.control.seguranca;


public class Crypt
{

      private static String resultado;
      private static String str;
      private static byte[] codifica;
      private static int i;
      private static int cont;

      public static String criptografa(String paramString1, String paramString2)
      {
            if (paramString2.equals(""))
            {
                  paramString2 = "martinsconsultoria";
            }
            resultado = "";
            codifica = paramString1.getBytes();

            for (i = 0; i < codifica.length; i += 1)
            {
                  codifica[i] = ((byte) (codifica[i] ^ Character.getNumericValue(
                              new Character(paramString2.charAt(i % paramString2.length())).charValue())));
            }
            str = new String(codifica);

            if (str.length() % 2 == 0)
            {
                  for (i = 0; i < str.length(); i += 1)
                        resultado = resultado + str.charAt(i) + (char) (int) Math.round(Math.random() * 255.0D);
            }
            else
            {
                  for (i = 0; i < str.length() - 1; i += 1)
                  {
                        resultado = resultado + str.charAt(i) + (char) (int) Math.round(Math.random() * 255.0D);
                  }
                  resultado += str.charAt(str.length() - 1);
            }
            return resultado;
      }

      public boolean isSenhaOk(String senhaDigitada, String senhaAtual)
      {
            String uncSenha = descriptografa(senhaAtual, "martinsconsultoria");

            String uncSenhaNewKey = "";
            try
            {
                  uncSenhaNewKey = new CryptDados().decrypt(senhaAtual);
            }
            catch (IllegalArgumentException eCrypt)
            {
                  uncSenhaNewKey = "";
            }

            if ((!senhaDigitada.equals(uncSenha)) && (!senhaDigitada.trim().equals(uncSenhaNewKey.trim())))
            {
                  return false;
            }
            return true;
      }

      public static boolean compara(String paramString1, String paramString2)
      {
            String str1 = descriptografa(paramString1, "martinsconsultoria");
            String str2 = descriptografa(paramString2, "martinsconsultoria");

            return str1.equals(str2);
      }

      public static String descriptografa(String paramString1, String paramString2)
      {
            if (paramString2.equals(""))
            {
                  paramString2 = "martinsconsultoria";
            }
            resultado = "";
            cont = 0;
            codifica = paramString1.getBytes();
            for (i = 0; i < codifica.length; i += 2)
            {
                  resultado += (char) (codifica[i] ^ Character.getNumericValue(
                              new Character(paramString2.charAt(cont % paramString2.length())).charValue()));
                  cont += 1;
            }
            return resultado;
      }
}

