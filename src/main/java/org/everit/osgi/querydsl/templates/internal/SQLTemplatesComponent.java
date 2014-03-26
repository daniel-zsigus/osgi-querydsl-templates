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

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyOption;
import org.everit.osgi.querydsl.templates.SQLTemplatesConstants;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentException;

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
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.SQLTemplates.Builder;
import com.mysema.query.sql.SQLiteTemplates;
import com.mysema.query.sql.TeradataTemplates;

/**
 * Component that instantiates and registers SQLTemapltes objects as OSGi service.
 */
@Component(name = SQLTemplatesConstants.COMPONENT_NAME_SQL_TEMPLATES, metatype = true, configurationFactory = true,
        policy = ConfigurationPolicy.REQUIRE)
@Properties({
        @Property(name = SQLTemplatesConstants.PROP_DB_TYPE, value = SQLTemplatesConstants.PROP_DB_TYPE_H2,
                options = {
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_H2,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_H2),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_POSTGRES,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_POSTGRES),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_MYSQL,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_MYSQL),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_ORACLE,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_ORACLE),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_SQLITE,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_SQLITE),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_CUBRID,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_CUBRID),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_DERBY,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_DERBY),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_HSQLDB,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_HSQLDB),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_TERADATA,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_TERADATA),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER_2005,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER_2005),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER_2008,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER_2008),
                        @PropertyOption(name = SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER_2012,
                                value = SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER_2012) }),
        @Property(name = SQLTemplatesConstants.PROP_PRINTSCHEMA, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROP_QUOTE, boolValue = true),
        @Property(name = SQLTemplatesConstants.PROP_NEWLINETOSINGLESPACE, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROP_ESCAPE, charValue = '\\')
})
public class SQLTemplatesComponent {

    private ServiceRegistration<SQLTemplates> serviceRegistration;

    @Activate
    public void activate(final BundleContext context, final Map<String, Object> componentProperties) {

        Builder sqlTemplate = null;

        Object dbTypeObject = componentProperties.get(SQLTemplatesConstants.PROP_DB_TYPE);
        if (dbTypeObject != null) {
            if (!(dbTypeObject instanceof String)) {
                throw new ComponentException("Expected type for TYPE property is String but got "
                        + dbTypeObject.getClass());
            } else {
                String dbType = (String) dbTypeObject;
                sqlTemplate = instantiateBuilder(dbType);
            }
        } else {
            throw new ComponentException(SQLTemplatesConstants.PROP_DB_TYPE + " property must be set.");
        }

        SQLTemplateUtils.setBuilderProperties(sqlTemplate, componentProperties);

        Dictionary<String, Object> properties = new Hashtable<String, Object>(componentProperties);

        serviceRegistration = context.registerService(SQLTemplates.class, sqlTemplate.build(), properties);
    }

    @Deactivate
    public void deactivate(final BundleContext context) {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
    }

    protected Builder instantiateBuilder(String dbType) {
        if (dbType == null) {
            return null;
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_CUBRID.equals(dbType)) {
            return CUBRIDTemplates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_DERBY.equals(dbType)) {
            return DerbyTemplates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_H2.equals(dbType)) {
            return H2Templates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_HSQLDB.equals(dbType)) {
            return HSQLDBTemplates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_MYSQL.equals(dbType)) {
            return MySQLTemplates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_ORACLE.equals(dbType)) {
            return OracleTemplates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_POSTGRES.equals(dbType)) {
            return PostgresTemplates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_SQLITE.equals(dbType)) {
            return SQLiteTemplates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER.equals(dbType)) {
            return SQLServerTemplates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER_2005.equals(dbType)) {
            return SQLServer2005Templates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER_2008.equals(dbType)) {
            return SQLServer2008Templates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_SQLSERVER_2012.equals(dbType)) {
            return SQLServer2012Templates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_TERADATA.equals(dbType)) {
            return TeradataTemplates.builder();
        }
        if (SQLTemplatesConstants.PROP_DB_TYPE_CUBRID.equals(dbType)) {
            return CUBRIDTemplates.builder();
        }
        return null;
    }
}
