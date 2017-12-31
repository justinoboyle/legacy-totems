package com.justinoboyle.totems.core.utils;

public class MathUtils
{
  public static <T extends Comparable<T>> T clamp(T val, T min, T max)
  {
    if (val.compareTo(min) < 0) {
      return min;
    }
    if (val.compareTo(max) > 0) {
      return max;
    }
    return val;
  }
  
  public static int getCenter(int size)
  {
    return (int)Math.round(size / 2);
  }
}
