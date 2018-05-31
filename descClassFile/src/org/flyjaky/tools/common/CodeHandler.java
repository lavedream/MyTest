package org.flyjaky.tools.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class CodeHandler
{
  public static byte[] decode(byte[] bytes)
    throws Exception
  {
    if ((bytes != null) && (bytes.length > 0)) {
      return DES.decrypt("rcxcloud".getBytes(), bytes);
    }
    return bytes;
  }
  
  protected static class DES
  {
    private static SecureRandom sr = new SecureRandom();
    private static SecretKeyFactory keyFactory;
    private static KeyGenerator kg;
    
    static
    {
      try
      {
        keyFactory = SecretKeyFactory.getInstance("DES");
        kg = KeyGenerator.getInstance("DES");
        kg.init(sr);
      }
      catch (NoSuchAlgorithmException neverHappens)
      {
        neverHappens.printStackTrace();
      }
    }
    
    public static byte[] decrypt(byte[] rawKeyData, byte[] encryptedData)
      throws Exception
    {
      DESKeySpec dks = null;
      try
      {
        dks = new DESKeySpec(rawKeyData);
        SecretKey key = keyFactory.generateSecret(dks);
        
        Cipher cipher = Cipher.getInstance("DES");
        
        cipher.init(2, key, sr);
        
        return cipher.doFinal(encryptedData);
      }
      catch (Exception e)
      {
        throw e;
      }
    }
  }
}