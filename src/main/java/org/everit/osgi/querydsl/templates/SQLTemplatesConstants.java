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

public final class SQLTemplatesConstants {

    public static final String PROPERTY_DB_TYPE = "dbtype";
    public static final String PROPERTY_PRINTSCHEMA = "printSchema";
    public static final String PROPERTY_QUOTE = "quote";
    public static final String PROPERTY_ESCAPE = "escape";
    public static final String PROPERTY_NEWLINETOSINGLESPACE = "newLineToSingleSpace";

    public static final String DB_TYPE_POSTGRES = "PostgreSQL";
    public static final String DB_TYPE_H2 = "H2";
    public static final String DB_TYPE_ORACLE = "Oracle";
    public static final String DB_TYPE_MYSQL = "MySQL";
    public static final String DB_TYPE_SQLITE = "SQLite";
    public static final String DB_TYPE_HSQLDB = "HSQL Database Engine";
    public static final String DB_TYPE_DERBY = "Apache Derby";
    public static final String DB_TYPE_CUBRID = "CUBRID";
    public static final String DB_TYPE_TERADATA = "Teradata";
    public static final String DB_TYPE_SQLSERVER2005 = "Microsoft SQL Server";
    public static final String DB_TYPE_SQLSERVER2012 = "Microsoft SQL Server 2012";

    private SQLTemplatesConstants() {
    }
}
