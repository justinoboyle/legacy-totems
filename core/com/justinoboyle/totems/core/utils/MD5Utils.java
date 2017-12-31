package com.justinoboyle.totems.core.utils;

import com.justinoboyle.totems.core.errors.ErrorReporting;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils
{
  public static String calculateHash(File f)
  {
    String md5;
    try
    {
      FileInputStream fis = new FileInputStream(f);
      String md5 = DigestUtils.md5Hex(fis);
      fis.close();
    }
    catch (IOException e)
    {
      md5 = "null";
      ErrorReporting.sendStackTrace(e);
    }
    return md5;
  }
}
