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

import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.SQLTemplates.Builder;

@Component(name = "org.everit.osgi.querydsl.templates.SQLTemplates", metatype = true, configurationFactory = true,
        policy = ConfigurationPolicy.REQUIRE)
@Properties({
        @Property(name = SQLTemplatesConstants.PROPERTY_DB_TYPE,
                options = {
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_POSTGRES,
                                value = SQLTemplatesConstants.DB_TYPE_POSTGRES),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_H2,
                                value = SQLTemplatesConstants.DB_TYPE_H2),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_MYSQL,
                                value = SQLTemplatesConstants.DB_TYPE_MYSQL),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_ORACLE,
                                value = SQLTemplatesConstants.DB_TYPE_ORACLE),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_SQLITE,
                                value = SQLTemplatesConstants.DB_TYPE_SQLITE),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_CUBRID,
                                value = SQLTemplatesConstants.DB_TYPE_CUBRID),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_DERBY,
                                value = SQLTemplatesConstants.DB_TYPE_DERBY),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_HSQLDB,
                                value = SQLTemplatesConstants.DB_TYPE_HSQLDB),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_TERADATA,
                                value = SQLTemplatesConstants.DB_TYPE_TERADATA),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_SQLSERVER2005,
                                value = SQLTemplatesConstants.DB_TYPE_SQLSERVER2005),
                        @PropertyOption(name = SQLTemplatesConstants.DB_TYPE_SQLSERVER2012,
                                value = SQLTemplatesConstants.DB_TYPE_SQLSERVER2012) }),
        @Property(name = SQLTemplatesConstants.PROPERTY_PRINTSCHEMA, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROPERTY_QUOTE, boolValue = true),
        @Property(name = SQLTemplatesConstants.PROPERTY_NEWLINETOSINGLESPACE, boolValue = false),
        @Property(name = SQLTemplatesConstants.PROPERTY_ESCAPE, charValue = '\\')
})
public class SQLTemplatesComponent {

    private ServiceRegistration<SQLTemplates> service;

    @Activate
    public void activate(final BundleContext context, final Map<String, Object> componentProperties) {

        Builder sqlTemplate = null;

        Object dbTypeObject = componentProperties.get(SQLTemplatesConstants.PROPERTY_DB_TYPE);
        if (dbTypeObject != null) {
            if (!(dbTypeObject instanceof String)) {
                throw new RuntimeException("Expected type for TYPE property is String but got "
                        + dbTypeObject.getClass());
            } else {
                String dbType = (String) dbTypeObject;
                sqlTemplate = SQLTemplateUtils.getBuilderByType(dbType);
            }
        } else {
            throw new RuntimeException("DB_TYPE property must be set.");
        }

        SQLTemplateUtils.setBuilderProperties(sqlTemplate, componentProperties);

        Dictionary<String, Object> properties = new Hashtable<String, Object>();
        properties.put("service.pid", componentProperties.get("service.pid"));

        service = context.registerService(SQLTemplates.class, sqlTemplate.build(), properties);
    }

    @Deactivate
    public void deactivate(final BundleContext context) {
        if (service != null) {
            service.unregister();
        }
    }

}
