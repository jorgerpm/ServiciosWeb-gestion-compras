package com.idebsystems.serviciosweb.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyMD5
{
  private MessageDigest md = null;
  private static MyMD5 md5 = null;
  private static final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  private MyMD5()
    throws NoSuchAlgorithmException
  {
    this.md = MessageDigest.getInstance("MD5");
  }

  public static MyMD5 getInstance()
    throws NoSuchAlgorithmException
  {
    if (md5 == null)
    {
      md5 = new MyMD5();
    }

    return md5;
  }

  public String hashData(byte[] dataToHash)
  {
    return hexStringFromBytes(calculateHash(dataToHash));
  }

  private byte[] calculateHash(byte[] dataToHash)
  {
    this.md.update(dataToHash, 0, dataToHash.length);

    return this.md.digest();
  }

  public String hexStringFromBytes(byte[] b)
  {
    String hex = "";

    int lsb = 0;

    for (int i = 0; i < b.length; i++)
    {
      int msb = (b[i] & 0xFF) / 16;

      lsb = (b[i] & 0xFF) % 16;
      hex = hex + hexChars[msb] + hexChars[lsb];
    }
    return hex;
  }

  public static void main(String[] args)
  {
    try
    {
      MyMD5 md = getInstance();
      System.out.println(md.hashData("jorge".getBytes()));
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace(System.out);
    }
  }
}