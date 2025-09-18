package circulacao.control.seguranca;

import java.io.UnsupportedEncodingException;

public class CryptDados
{

      private static final String[] DEFAULT_KEYS =
      { "01210ACB39201293948ABE4839201CDF", "123219843895AFDE3920291038103839", "89128912093908120983980981098309",
                  "AABBCCDD019201920384383728298109" };

      @SuppressWarnings("unused")
      private static boolean updatedProps = false;
      private static final char[] hexDigits =
      { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

      private byte[][] keys = null;

      public CryptDados()
      {
            init(DEFAULT_KEYS);
      }

      public CryptDados(String[] keystrs)
      {
            init(keystrs);
      }

      private void init(String[] keystrs)
      {
            this.keys = new byte[keystrs.length][];
            for (int i = 0; i < this.keys.length; i++)
                  this.keys[i] = fromString(keystrs[i]);
      }

      private String toString(byte[] ba)
      {
            char[] buf = new char[ba.length * 2];
            int j = 0;

            for (int i = 0; i < ba.length; i++)
            {
                  int k = ba[i];
                  buf[(j++)] = hexDigits[(k >>> 4 & 0xF)];
                  buf[(j++)] = hexDigits[(k & 0xF)];
            }
            return new String(buf);
      }

      private int fromDigit(char ch)
      {
            if ((ch >= '0') && (ch <= '9'))
                  return ch - '0';
            if ((ch >= 'A') && (ch <= 'F'))
                  return ch - 'A' + 10;
            if ((ch >= 'a') && (ch <= 'f'))
            {
                  return ch - 'a' + 10;
            }
            throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
      }

      private byte[] fromString(String hex)
      {
            int len = hex.length();
            byte[] buf = new byte[(len + 1) / 2];

            int i = 0;
            int j = 0;
            if (len % 2 == 1)
            {
                  buf[(j++)] = ((byte) fromDigit(hex.charAt(i++)));
            }
            while (i < len)
            {
                  buf[(j++)] = ((byte) (fromDigit(hex.charAt(i++)) << 4 | fromDigit(hex.charAt(i++))));
            }
            return buf;
      }

      private byte encrypt(byte d, byte[] key)
      {
            byte e = d;
            for (int i = 0; i < key.length; i++)
            {
                  e = (byte) (e ^ key[i]);
            }

            return e;
      }

      private byte decrypt(byte e, byte[] key)
      {
            byte d = e;
            for (int i = key.length - 1; i >= 0; i--)
            {
                  d = (byte) (d ^ key[i]);
            }

            return d;
      }

      public String encrypt(String orig)
      {
            byte[] ect = (byte[]) null;

            byte[] origBytes = (byte[]) null;
            try
            {
                  origBytes = orig.getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                  e.printStackTrace();
                  throw new RuntimeException(e.toString());
            }

            ect = new byte[origBytes.length];
            for (int i = 0; i < origBytes.length; i += this.keys.length)
            {
                  for (int j = 0; j < this.keys.length; j++)
                  {
                        if (i + j >= origBytes.length)
                        {
                              break;
                        }
                        ect[(i + j)] = encrypt(origBytes[(i + j)], this.keys[j]);
                  }

            }

            return toString(ect);
      }

      public String decrypt(String ectstr)
      {
            byte[] ect = (byte[]) null;

            byte[] origBytes = (byte[]) null;
            String dctStr = null;

            ect = fromString(ectstr);
            origBytes = new byte[ect.length];
            for (int i = 0; i < origBytes.length; i += this.keys.length)
            {
                  for (int j = 0; j < this.keys.length; j++)
                  {
                        if (i + j >= origBytes.length)
                        {
                              break;
                        }
                        origBytes[(i + j)] = decrypt(ect[(i + j)], this.keys[j]);
                  }
            }

            try
            {
                  dctStr = new String(origBytes, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                  e.printStackTrace();
                  throw new RuntimeException(e.toString());
            }
            return dctStr;
      }

}

