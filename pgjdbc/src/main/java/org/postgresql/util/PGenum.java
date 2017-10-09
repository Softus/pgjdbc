/*
 * Copyright (c) 2017, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */


package org.postgresql.util;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This implements a class that handles the PostgreSQL enum types
 */
public class PGenum extends PGobject implements Serializable, Cloneable, Comparable {
  private static final HashMap _enums;

  static {
    _enums = new HashMap();
  }

  public static synchronized boolean addEnum(String enumName, HashMap enumValues) {
    if (!_enums.containsKey(enumName)) {
      _enums.put(enumName, enumValues);
      return true;
    }
    return false;
  }

  private int _val;

  /*
   * Required by the driver
   */
  public PGenum() {
  }

  public void setValue(String s) throws SQLException {
    super.setValue(s);
    _val = ((Integer)((HashMap)_enums.get(getType())).get(getValue())).intValue();
  }

  public int getIntValue() {
    return _val;
  }

  public boolean equals(Object o) {
    if (o instanceof PGenum) {
      return _val == ((PGenum)o)._val;
    }

    return false;
  }

  public int hashCode() {
    return _val;
  }

  public int compareTo(Object o) {
    int otherVal = ((PGenum)o)._val;
    return (_val < otherVal ? -1 : (_val > otherVal ? 1 : 0));
  }
}
