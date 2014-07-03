/**
 * This file is part of Everit - SQL Template Component.
 *
 * Everit - SQL Template Component is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Everit - SQL Template Component is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Everit - SQL Template Component.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.everit.osgi.querydsl.templates;

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
            return majorVersion < 9;
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
            return majorVersion == 9;
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
            return majorVersion == 10;
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
            return majorVersion > 10;
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

    public static final DBMSType getByProductNameAndMajorVersion(final String productName, final int majorVersion) {
        for (DBMSType type : values()) {
            if (type.getProductName().equals(productName) && type.fitsMajorVersion(majorVersion)) {
                return type;
            }
        }
        throw new UnknownDatabaseTypeException("database " + productName + " (major version: " + majorVersion
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
