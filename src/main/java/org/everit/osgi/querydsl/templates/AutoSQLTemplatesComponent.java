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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.mysema.query.sql.CUBRIDTemplates;
import com.mysema.query.sql.DerbyTemplates;
import com.mysema.query.sql.H2Templates;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.MySQLTemplates;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLServer2005Templates;
import com.mysema.query.sql.SQLServer2012Templates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.SQLTemplates.Builder;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.TeradataTemplates;

@Component(metatype = true, configurationFactory = true, policy = ConfigurationPolicy.REQUIRE)
@Properties({
        @Property(name = "dataSource.target"),
        @Property(name = SQLTemplatesConstants.PROPERTY_PRINTSCHEMA, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROPERTY_QUOTE, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROPERTY_NEWLINETOSINGLESPACE, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROPERTY_ESCAPE, charValue = '\\')
})
public class AutoSQLTemplatesComponent {

    private ServiceRegistration<SQLTemplates> service;

    @Reference
    private DataSource dataSource;

    @Activate
    public void activate(final BundleContext context, final Map<String, Object> componentProperties) {

        Builder SQLTemplate = null;
        String dbType = null;
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            dbType = conn.getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get Database product name of the given DataSource.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Cannot close database connection.");
                }
            }
        }

        if (SQLTemplatesConstants.DB_TYPE_POSTGRES.equals(dbType)) {
            SQLTemplate = PostgresTemplates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_H2.equals(dbType)) {
            SQLTemplate = H2Templates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_MYSQL.equals(dbType)) {
            SQLTemplate = MySQLTemplates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_ORACLE.equals(dbType)) {
            SQLTemplate = OracleTemplates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_CUBRID.equals(dbType)) {
            SQLTemplate = CUBRIDTemplates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_DERBY.equals(dbType)) {
            SQLTemplate = DerbyTemplates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_HSQLDB.equals(dbType)) {
            SQLTemplate = HSQLDBTemplates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_SQLITE.equals(dbType)) {
            SQLTemplate = SQLiteTemplates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_TERADATA.equals(dbType)) {
            SQLTemplate = TeradataTemplates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_SQLSERVER2005.equals(dbType)) {
            SQLTemplate = SQLServer2005Templates.builder();
        }
        else if (SQLTemplatesConstants.DB_TYPE_SQLSERVER2012.equals(dbType)) {
            SQLTemplate = SQLServer2012Templates.builder();
        }
        else {
            throw new RuntimeException("The database type of the given DataSource is not supported.");
        }

        Object printSchemaObject = componentProperties.get(SQLTemplatesConstants.PROPERTY_PRINTSCHEMA);
        if (printSchemaObject != null) {
            if (!(printSchemaObject instanceof Boolean)) {
                throw new RuntimeException("Expected type for printSchema is Boolean but got "
                        + printSchemaObject.getClass());
            } else {
                if ((Boolean) printSchemaObject == true) {
                    SQLTemplate.printSchema();
                }
            }
        }
        Object quoteObject = componentProperties.get(SQLTemplatesConstants.PROPERTY_QUOTE);
        if (quoteObject != null) {
            if (!(quoteObject instanceof Boolean)) {
                throw new RuntimeException("Expected type for quote is Boolean but got "
                        + quoteObject.getClass());
            } else {
                if ((Boolean) quoteObject == true) {
                    SQLTemplate.quote();
                }
            }
        }
        Object newLineToSingleSpaceObject = componentProperties.get(SQLTemplatesConstants.PROPERTY_NEWLINETOSINGLESPACE);
        if (newLineToSingleSpaceObject != null) {
            if (!(newLineToSingleSpaceObject instanceof Boolean)) {
                throw new RuntimeException("Expected type for newLineToSingleSpace is Boolean but got "
                        + newLineToSingleSpaceObject.getClass());
            } else {
                if ((Boolean) newLineToSingleSpaceObject == true) {
                    SQLTemplate.newLineToSingleSpace();
                }
            }
        }
        Object escapeObject = componentProperties.get(SQLTemplatesConstants.PROPERTY_ESCAPE);
        if (escapeObject != null) {
            if (!(escapeObject instanceof Character)) {
                throw new RuntimeException("Expected type for escape is Character but got "
                        + escapeObject.getClass());
            } else {
                SQLTemplate.escape((Character) escapeObject);
            }
        }

        Dictionary<String, Object> properties = new Hashtable<String, Object>();
        properties.put("service.pid", componentProperties.get("service.pid"));

        service = context.registerService(SQLTemplates.class, SQLTemplate.build(), properties);
    }

    @Deactivate
    public void deactivate(final BundleContext context) {
        if (service != null) {
            service.unregister();
        }
    }

}
