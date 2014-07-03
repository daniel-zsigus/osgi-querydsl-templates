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
import org.everit.osgi.querydsl.templates.SQLTemplatesConstants;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentException;
import org.osgi.service.log.LogService;

import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.SQLTemplates.Builder;

/**
 * Component that automatically detects the type of the database based on the referenced DataSource and registers the
 * right type of SQLTemplates instance.
 */
@Component(name = SQLTemplatesConstants.COMPONENT_NAME_AUTO_SQL_TEMPLATES,
        metatype = true, configurationFactory = true, policy = ConfigurationPolicy.REQUIRE)
@Properties({
        @Property(name = "dataSource.target"),
        @Property(name = SQLTemplatesConstants.PROP_PRINTSCHEMA, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROP_QUOTE, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROP_NEWLINETOSINGLESPACE, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROP_ESCAPE, charValue = '\\'),
        @Property(name = "logService.target")
})
public class AutoSQLTemplatesComponent {

    /**
     * The datasource that is used to find out the type of the database.
     */
    @Reference
    private DataSource dataSource;

    /**
     * The logging service.
     */
    @Reference
    private LogService logService;

    /**
     * SQLTemplates OSGi service registration instance.
     */
    private ServiceRegistration<SQLTemplates> serviceRegistration;

    /**
     * Automatically configures an {@link SQLTemplates} instance based on the underlying {@code dataSource} and
     * registers it as an OSGi service.
     *
     * @param context
     * @param componentProperties
     */
    @Activate
    public void activate(final BundleContext context, final Map<String, Object> componentProperties) {
        Builder sqlTemplateBuilder = null;
        String dbProductName = "";
        int dbMajorVersion = 0;

        try (Connection conn = dataSource.getConnection()) {
            dbProductName = conn.getMetaData().getDatabaseProductName();
            dbMajorVersion = conn.getMetaData().getDatabaseMajorVersion();
        } catch (SQLException e) {
            throw new ComponentException("Cannot get Database product name of the given DataSource.", e);
        }

        sqlTemplateBuilder = DBMSType.getByProductNameAndMajorVersion(dbProductName, dbMajorVersion)
                .getSQLTemplatesBuilder();

        new SQLTemplateConfigurator(sqlTemplateBuilder, componentProperties).configure();

        Dictionary<String, Object> properties = new Hashtable<String, Object>(componentProperties);

        SQLTemplates sqlTemplates = sqlTemplateBuilder.build();
        properties.put(SQLTemplatesConstants.PROP_SELECTED_TEMPLATE, sqlTemplateBuilder.getClass().getName());
        serviceRegistration = context.registerService(SQLTemplates.class, sqlTemplates, properties);
        logService.log(LogService.LOG_INFO, "Selected template: " + sqlTemplateBuilder.getClass().getName());
    }

    @Deactivate
    public void deactivate(final BundleContext context) {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }
    }

}
