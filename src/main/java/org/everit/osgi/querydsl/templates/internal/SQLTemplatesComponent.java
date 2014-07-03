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
import java.util.Objects;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyOption;
import org.everit.osgi.querydsl.templates.DBMSType;
import org.everit.osgi.querydsl.templates.SQLTemplatesConstants;
import org.everit.osgi.querydsl.templates.UnknownDatabaseTypeException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentException;

import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.SQLTemplates.Builder;

/**
 * Component that instantiates and registers SQLTemapltes objects as OSGi service.
 */
@Component(name = SQLTemplatesConstants.SERVICE_FACTORY_PID__SQL_TEMPLATES, metatype = true,
        configurationFactory = true,
policy = ConfigurationPolicy.REQUIRE)
@Properties({
    @Property(name = SQLTemplatesConstants.PROP_DB_TYPE, value = DBMSType.PROP_DB_TYPE_H2,
            options = {
            @PropertyOption(name = DBMSType.PROP_DB_TYPE_H2,
                    value = DBMSType.PROP_DB_TYPE_H2),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_POSTGRES,
                    value = DBMSType.PROP_DB_TYPE_POSTGRES),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_MYSQL,
                    value = DBMSType.PROP_DB_TYPE_MYSQL),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_ORACLE,
                    value = DBMSType.PROP_DB_TYPE_ORACLE),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_SQLITE,
                    value = DBMSType.PROP_DB_TYPE_SQLITE),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_CUBRID,
                    value = DBMSType.PROP_DB_TYPE_CUBRID),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_DERBY,
                    value = DBMSType.PROP_DB_TYPE_DERBY),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_HSQLDB,
                    value = DBMSType.PROP_DB_TYPE_HSQLDB),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_TERADATA,
                    value = DBMSType.PROP_DB_TYPE_TERADATA),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_SQLSERVER,
                    value = DBMSType.PROP_DB_TYPE_SQLSERVER),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_SQLSERVER_2005,
                    value = DBMSType.PROP_DB_TYPE_SQLSERVER_2005),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_SQLSERVER_2008,
                    value = DBMSType.PROP_DB_TYPE_SQLSERVER_2008),
                    @PropertyOption(name = DBMSType.PROP_DB_TYPE_SQLSERVER_2012,
                    value = DBMSType.PROP_DB_TYPE_SQLSERVER_2012) }),
                    @Property(name = SQLTemplatesConstants.PROP_PRINTSCHEMA, boolValue = false),
                    @Property(name = SQLTemplatesConstants.PROP_QUOTE, boolValue = true),
                    @Property(name = SQLTemplatesConstants.PROP_NEWLINETOSINGLESPACE, boolValue = false),
                    @Property(name = SQLTemplatesConstants.PROP_ESCAPE, charValue = '\\')
})
public class SQLTemplatesComponent {

    private ServiceRegistration<SQLTemplates> serviceRegistration;

    /**
     * Configures an {@link SQLTemplates} instance based on {@code componentProperties} and registers it as an OSGi
     * service using {@code context}.
     *
     * @param context
     * @param componentProperties
     * @throws ComponentException
     */
    @Activate
    public void activate(final BundleContext context, final Map<String, Object> componentProperties) {
        try {
            Object dbTypeObject = componentProperties.get(SQLTemplatesConstants.PROP_DB_TYPE);
            Builder sqlTemplate = instantiateBuilder((String) dbTypeObject);
            new SQLTemplateConfigurator(sqlTemplate, componentProperties).configure();
            Dictionary<String, Object> properties = new Hashtable<String, Object>(componentProperties);
            serviceRegistration = context.registerService(SQLTemplates.class, sqlTemplate.build(), properties);
        } catch (UnknownDatabaseTypeException e) {
            throw new ComponentException(e);
        } catch (NullPointerException | ClassCastException e) {
            throw new ComponentException(SQLTemplatesConstants.PROP_DB_TYPE
                    + " property must be set and must be a String", e);
        }
    }

    /**
     * Unregisters the {@link SQLTemplates} instance which has been registered by {@link #activate(BundleContext, Map)}.
     *
     * @param context
     */
    @Deactivate
    public void deactivate(final BundleContext context) {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
    }

    private Builder instantiateBuilder(final String dbType) {
        Objects.requireNonNull(dbType, "dbType cannot be null");
        for (DBMSType type : DBMSType.values()) {
            if (type.toString().equals(dbType)) {
                return type.getSQLTemplatesBuilder();
            }
        }
        throw new UnknownDatabaseTypeException("database type [" + dbType + "] is not supported");
    }
}
