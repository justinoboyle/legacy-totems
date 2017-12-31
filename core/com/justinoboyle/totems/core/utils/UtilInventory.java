package com.justinoboyle.totems.core.utils;

import java.util.ArrayList;

public class UtilInventory
{
  public static int[] getFilledInSlots(int inventorySize, int fillAmount)
  {
    int center = MathUtils.getCenter(inventorySize);
    if (fillAmount == 0) {
      return new int[0];
    }
    if (fillAmount == 1) {
      return new int[] { center };
    }
    ArrayList<Integer> filled = new ArrayList();
    boolean p = false;
    int iterations = fillAmount % 3 == 0 ? 0 : 1;
    while (fillAmount - filled.size() > 0)
    {
      filled.add(Integer.valueOf(center + (iterations % 2 == 0 ? 2 : -2) * iterations * 2));
      p = !p;
      iterations++;
    }
    int[] r = new int[filled.size()];
    int c = 0;
    for (Integer i : filled)
    {
      r[c] = i.intValue();
      c++;
    }
    return r;
  }
}
