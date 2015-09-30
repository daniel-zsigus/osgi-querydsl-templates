/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.persistence.querydsl.sqltemplates.ecm;

import com.mysema.query.sql.CUBRIDTemplates;
import com.mysema.query.sql.DerbyTemplates;
import com.mysema.query.sql.H2Templates;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.MySQLTemplates;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLServer2005Templates;
import com.mysema.query.sql.SQLServer2008Templates;
import com.mysema.query.sql.SQLServer2012Templates;
import com.mysema.query.sql.SQLServerTemplates;
import com.mysema.query.sql.SQLTemplates.Builder;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.TeradataTemplates;

/**
 * Types of DBMS.
 */
public enum DBMSType {

  CUBRID {
    @Override
    public String getProductName() {
      return TYPE_CUBRID;
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return CUBRIDTemplates.builder();
    }
  },

  DERBY {
    @Override
    public String getProductName() {
      return "Apache Derby";
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return DerbyTemplates.builder();
    }

    @Override
    public String toString() {
      return TYPE_DERBY;
    }
  },

  H2 {

    @Override
    public String getProductName() {
      return TYPE_H2;
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return H2Templates.builder();
    }
  },

  HSQLDB {

    @Override
    public String getProductName() {
      return "HSQL Database Engine";
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return HSQLDBTemplates.builder();
    }

    @Override
    public String toString() {
      return TYPE_HSQLDB;
    }

  },
  MYSQL {
    @Override
    public String getProductName() {
      return TYPE_MYSQL;
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return MySQLTemplates.builder();
    }
  },
  ORACLE {
    @Override
    public String getProductName() {
      return TYPE_ORACLE;
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return OracleTemplates.builder();
    }
  },
  POSTGRES {

    @Override
    public String getProductName() {
      return "PostgreSQL";
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return PostgresTemplates.builder();
    }

    @Override
    public String toString() {
      return TYPE_POSTGRES;
    }

  },
  SQLITE {
    @Override
    public String getProductName() {
      return TYPE_SQLITE;
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return SQLiteTemplates.builder();
    }
  },
  SQLSERVER {
    @Override
    boolean fitsMajorVersion(final int majorVersion) {
      return majorVersion < VERSION_NINE;
    }

    @Override
    public String getProductName() {
      return "Microsoft SQL Server";
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return SQLServerTemplates.builder();
    }

    @Override
    public String toString() {
      return TYPE_SQLSERVER;
    }

  },
  SQLSERVER_2005 {
    @Override
    boolean fitsMajorVersion(final int majorVersion) {
      return majorVersion == VERSION_NINE;
    }

    @Override
    public String getProductName() {
      return SQLSERVER.getProductName();
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return SQLServer2005Templates.builder();
    }

    @Override
    public String toString() {
      return TYPE_SQLSERVER_2005;
    }
  },
  SQLSERVER_2008 {
    @Override
    boolean fitsMajorVersion(final int majorVersion) {
      return majorVersion == VERSION_TEN;
    }

    @Override
    public String getProductName() {
      return SQLSERVER.getProductName();
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return SQLServer2008Templates.builder();
    }

    @Override
    public String toString() {
      return TYPE_SQLSERVER_2008;
    }
  },
  SQLSERVER_2012 {
    @Override
    boolean fitsMajorVersion(final int majorVersion) {
      return majorVersion > VERSION_TEN;
    }

    @Override
    public String getProductName() {
      return SQLSERVER.getProductName();
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return SQLServer2012Templates.builder();
    }

    @Override
    public String toString() {
      return TYPE_SQLSERVER_2012;
    }
  },
  SYBASE {
    @Override
    public String getProductName() {
      return "Sybase";
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      throw new UnsupportedOperationException("not yet implemented");
    }
  },
  TERADATA {

    @Override
    public String getProductName() {
      return TYPE_TERADATA;
    }

    @Override
    public Builder getSQLTemplatesBuilder() {
      return TeradataTemplates.builder();
    }

  };

  public static final String TYPE_CUBRID = "CUBRID";
  public static final String TYPE_DERBY = "Derby";
  public static final String TYPE_H2 = "H2";
  public static final String TYPE_HSQLDB = "HSQLDB";
  public static final String TYPE_MYSQL = "MySQL";
  public static final String TYPE_ORACLE = "Oracle";
  public static final String TYPE_POSTGRES = "Postgres";
  public static final String TYPE_SQLITE = "SQLite";
  public static final String TYPE_SQLSERVER = "SQLServer";
  public static final String TYPE_SQLSERVER_2005 = "SQLServer2005";
  public static final String TYPE_SQLSERVER_2008 = "SQLServer2008";
  public static final String TYPE_SQLSERVER_2012 = "SQLServer2012";
  public static final String TYPE_TERADATA = "Teradata";
  private static final int VERSION_NINE = 9;
  private static final int VERSION_TEN = 10;

  /**
   * Gets {@link DBMSType} based on product name and major version.
   */
  public static final DBMSType getByProductNameAndMajorVersion(final String productName,
      final int majorVersion) {
    for (DBMSType type : DBMSType.values()) {
      if (type.getProductName().equals(productName) && type.fitsMajorVersion(majorVersion)) {
        return type;
      }
    }
    throw new UnknownDatabaseTypeException(
        "database " + productName + " (major version: " + majorVersion
            + ") is not supported");
  }

  boolean fitsMajorVersion(final int majorVersion) {
    return true;
  }

  public abstract String getProductName();

  public abstract Builder getSQLTemplatesBuilder();

  @Override
  public String toString() {
    return getProductName();
  }

}
