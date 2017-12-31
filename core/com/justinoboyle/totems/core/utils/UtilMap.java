package com.justinoboyle.totems.core.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

public class UtilMap
{
  public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map)
  {
    SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet(new Comparator()
    {
      public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2)
      {
        int res = ((Comparable)e1.getValue()).compareTo(e2.getValue());
        return res != 0 ? res : 1;
      }
    });
    sortedEntries.addAll(map.entrySet());
    return sortedEntries;
  }
  
  public static <V, K> Map<V, K> invert(Map<K, V> map)
  {
    Map<V, K> inv = new HashMap();
    for (Map.Entry<K, V> entry : map.entrySet()) {
      inv.put(entry.getValue(), entry.getKey());
    }
    return inv;
  }
}
