/**
 * This file is part of Everit - Liquibase OSGi Component.
 *
 * Everit - Liquibase OSGi Component is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Everit - Liquibase OSGi Component is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Everit - Liquibase OSGi Component.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.everit.osgi.querydsl.templates;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.mysema.query.sql.MySQLTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.SQLTemplates.Builder;

@Component(metatype = true, configurationFactory = true, policy = ConfigurationPolicy.REQUIRE)
@Properties({
        @Property(name = "printSchema", boolValue = false),
        @Property(name = "quote", boolValue = false),
        @Property(name = "newLineToSingleSpace", boolValue = false),
        @Property(name = "escape", charValue = '\\')
})
public class MySQLTemplatesComponent {

    private ServiceRegistration<SQLTemplates> service;
    private Builder pgTemplate;

    @Activate
    public void activate(final BundleContext context, final Map<String, Object> componentProperties) {
        pgTemplate = MySQLTemplates.builder();

        Object printSchemaObject = componentProperties.get("printSchema");
        if (printSchemaObject != null) {
            if (!(printSchemaObject instanceof Boolean)) {
                throw new RuntimeException("Expected type for printSchema is Boolean but got "
                        + printSchemaObject.getClass());
            } else {
                if ((Boolean) printSchemaObject == true) {
                    pgTemplate.printSchema();
                }
            }
        }
        Object quoteObject = componentProperties.get("quote");
        if (quoteObject != null) {
            if (!(quoteObject instanceof Boolean)) {
                throw new RuntimeException("Expected type for quote is Boolean but got "
                        + quoteObject.getClass());
            } else {
                if ((Boolean) quoteObject == true) {
                    pgTemplate.quote();
                }
            }
        }
        Object newLineToSingleSpaceObject = componentProperties.get("newLineToSingleSpace");
        if (newLineToSingleSpaceObject != null) {
            if (!(newLineToSingleSpaceObject instanceof Boolean)) {
                throw new RuntimeException("Expected type for newLineToSingleSpace is Boolean but got "
                        + newLineToSingleSpaceObject.getClass());
            } else {
                if ((Boolean) newLineToSingleSpaceObject == true) {
                    pgTemplate.newLineToSingleSpace();
                }
            }
        }
        Object escapeObject = componentProperties.get("escape");
        if (escapeObject != null) {
            if (!(escapeObject instanceof Character)) {
                throw new RuntimeException("Expected type for escape is Character but got "
                        + escapeObject.getClass());
            } else {
                pgTemplate.escape((Character) escapeObject);
            }
        }

        Dictionary<String, Object> properties = new Hashtable<String, Object>();
        properties.put("service.pid", componentProperties.get("service.pid"));

        service = context.registerService(SQLTemplates.class, pgTemplate.build(), properties);
    }

    @Deactivate
    public void deactivate(final BundleContext context) {
        if (service != null) {
            // context.ungetService((ServiceReference<?>) service);
            service.unregister();
        }
    }

}
