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
package org.everit.osgi.querydsl.templates.internal;

import java.util.Map;

import org.everit.osgi.querydsl.templates.SQLTemplatesConstants;
import org.osgi.service.log.LogService;

import com.mysema.query.sql.CUBRIDTemplates;
import com.mysema.query.sql.DerbyTemplates;
import com.mysema.query.sql.H2Templates;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.MySQLTemplates;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLServer2005Templates;
import com.mysema.query.sql.SQLServer2012Templates;
import com.mysema.query.sql.SQLTemplates.Builder;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.TeradataTemplates;

final class SQLTemplateUtils {

    public static Builder getBuilderByDBProductNameAndMajorVersion(final String dbType, final int majorVersion,
            final LogService logService) {

        Builder sqlTemplate = null;

        if (SQLTemplatesConstants.DB_PRODUCT_NAME_POSTGRES.equals(dbType)) {
            sqlTemplate = PostgresTemplates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_H2.equals(dbType)) {
            sqlTemplate = H2Templates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_MYSQL.equals(dbType)) {
            sqlTemplate = MySQLTemplates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_ORACLE.equals(dbType)) {
            sqlTemplate = OracleTemplates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_CUBRID.equals(dbType)) {
            sqlTemplate = CUBRIDTemplates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_DERBY.equals(dbType)) {
            sqlTemplate = DerbyTemplates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_HSQLDB.equals(dbType)) {
            sqlTemplate = HSQLDBTemplates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_SQLITE.equals(dbType)) {
            sqlTemplate = SQLiteTemplates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_TERADATA.equals(dbType)) {
            sqlTemplate = TeradataTemplates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_SYBASE.equals(dbType)) {
            sqlTemplate = SQLServer2005Templates.builder();
        } else if (SQLTemplatesConstants.DB_PRODUCT_NAME_SQLSERVER.equals(dbType)) {
            if (majorVersion < 9) {
                logService.log(LogService.LOG_WARNING, "SQLServer version " + majorVersion
                        + "  is lower than 9 (2005). The closest template will be selected.");
                sqlTemplate = SQLServer2005Templates.builder();
            } else if (majorVersion < 11) {
                sqlTemplate = SQLServer2005Templates.builder();
            } else {
                sqlTemplate = SQLServer2012Templates.builder();
            }

        } else {
            throw new RuntimeException("The database type of the given DataSource is not supported.");
        }

        return sqlTemplate;
    }

    public static void setBuilderProperties(final Builder sqlTemplate, final Map<String, Object> properties) {

        Object printSchemaObject = properties.get(SQLTemplatesConstants.PROPERTY_PRINTSCHEMA);
        if (printSchemaObject != null) {
            if (!(printSchemaObject instanceof Boolean)) {
                throw new RuntimeException("Expected type for printSchema is Boolean but got "
                        + printSchemaObject.getClass());
            } else {
                if ((Boolean) printSchemaObject) {
                    sqlTemplate.printSchema();
                }
            }
        }
        Object quoteObject = properties.get(SQLTemplatesConstants.PROPERTY_QUOTE);
        if (quoteObject != null) {
            if (!(quoteObject instanceof Boolean)) {
                throw new RuntimeException("Expected type for quote is Boolean but got "
                        + quoteObject.getClass());
            } else {
                if ((Boolean) quoteObject) {
                    sqlTemplate.quote();
                }
            }
        }
        Object newLineToSingleSpaceObject = properties
                .get(SQLTemplatesConstants.PROPERTY_NEWLINETOSINGLESPACE);
        if (newLineToSingleSpaceObject != null) {
            if (!(newLineToSingleSpaceObject instanceof Boolean)) {
                throw new RuntimeException("Expected type for newLineToSingleSpace is Boolean but got "
                        + newLineToSingleSpaceObject.getClass());
            } else {
                if ((Boolean) newLineToSingleSpaceObject) {
                    sqlTemplate.newLineToSingleSpace();
                }
            }
        }
        Object escapeObject = properties.get(SQLTemplatesConstants.PROPERTY_ESCAPE);
        if (escapeObject != null) {
            if (!(escapeObject instanceof Character)) {
                throw new RuntimeException("Expected type for escape is Character but got "
                        + escapeObject.getClass());
            } else {
                sqlTemplate.escape((Character) escapeObject);
            }
        }

    }

}
