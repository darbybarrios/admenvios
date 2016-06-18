package general;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


import sun.misc.BASE64Encoder;

/**
 * <p>Title:EncriptarClave </p>
 *
 * <p>Description: Esta clase contiene  los métodos necesarios para encriptar un texto  </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company:Enelbar </p>
 *
 * @author not attributable
 * @version 1.0
 */

@SuppressWarnings("restriction")
public final class EncriptarClave {
    /**
      * Método que permite encriptar una cadena
      *
      * @param textoplano String
      * @return String (cadena encriptada)
      * @throws GeneralException
      */


 /* public static String encriptar(String textoplano) throws
      IllegalStateException {
    MessageDigest md = null;

    try {
      md = MessageDigest.getInstance("SHA"); // Instancia de generador SHA-1
    }
    catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e.getMessage());
    }

    try {
      md.update(textoplano.getBytes("UTF-8")); // Generación de resumen de mensaje
    }
    catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(e.getMessage());
    }

    byte raw[] = md.digest(); // Obtención del resumen de mensaje
    String hash = (new BASE64Encoder()).encode(raw); // Traducción a BASE64
    return hash;
  }*/
  
  
  public static String Encriptar(String texto) {

      String secretKey = "qualityinfosolutions"; //llave para encriptar datos
      String base64EncryptedString = "";

      try {

          MessageDigest md = MessageDigest.getInstance("MD5");
          byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
          byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

          SecretKey key = new SecretKeySpec(keyBytes, "DESede");
          Cipher cipher = Cipher.getInstance("DESede");
          cipher.init(Cipher.ENCRYPT_MODE, key);

          byte[] plainTextBytes = texto.getBytes("utf-8");
          byte[] buf = cipher.doFinal(plainTextBytes);
          byte[] base64Bytes = Base64.encodeBase64(buf);
          base64EncryptedString = new String(base64Bytes);

      } catch (Exception ex) {
      }
      return base64EncryptedString;
}
  
  public static String Desencriptar(String textoEncriptado) throws Exception {

      String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
      String base64EncryptedString = "";

      try {
          byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
          MessageDigest md = MessageDigest.getInstance("MD5");
          byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
          byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
          SecretKey key = new SecretKeySpec(keyBytes, "DESede");

          Cipher decipher = Cipher.getInstance("DESede");
          decipher.init(Cipher.DECRYPT_MODE, key);

          byte[] plainText = decipher.doFinal(message);

          base64EncryptedString = new String(plainText, "UTF-8");

      } catch (Exception ex) {
      }
      return base64EncryptedString;
}
}

