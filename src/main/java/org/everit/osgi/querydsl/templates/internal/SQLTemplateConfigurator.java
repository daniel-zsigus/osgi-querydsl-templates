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
import java.util.Objects;

import org.everit.osgi.querydsl.templates.SQLTemplatesConstants;
import org.osgi.service.component.ComponentException;

import com.mysema.query.sql.SQLTemplates.Builder;

public final class SQLTemplateConfigurator {

    private final Builder sqlTemplate;

    private final Map<String, Object> config;

    public SQLTemplateConfigurator(final Builder sqlTemplate, final Map<String, Object> config) {
        this.sqlTemplate = Objects.requireNonNull(sqlTemplate, "sqlTemplate cannot be null");
        this.config = Objects.requireNonNull(config, "config cannot be null");
    }

    /**
     * Sets the properties for an instantiated SQLTemplates builder based on the configuration of the component.
     *
     * @param sqlTemplate
     *            The sql template instance.
     * @param properties
     *            The configuration of the component.
     */
    public void configure() {
        Object printSchemaObject = config.get(SQLTemplatesConstants.PROP_PRINTSCHEMA);
        if (printSchemaObject != null) {
            if (!(printSchemaObject instanceof Boolean)) {
                throw new ComponentException("Expected type for printSchema is Boolean but got "
                        + printSchemaObject.getClass());
            } else {
                if ((Boolean) printSchemaObject) {
                    sqlTemplate.printSchema();
                }
            }
        }
        Object quoteObject = config.get(SQLTemplatesConstants.PROP_QUOTE);
        if (quoteObject != null) {
            if (!(quoteObject instanceof Boolean)) {
                throw new ComponentException("Expected type for quote is Boolean but got "
                        + quoteObject.getClass());
            } else {
                if ((Boolean) quoteObject) {
                    sqlTemplate.quote();
                }
            }
        }
        Object newLineToSingleSpaceObject = config
                .get(SQLTemplatesConstants.PROP_NEWLINETOSINGLESPACE);
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
        Object escapeObject = config.get(SQLTemplatesConstants.PROP_ESCAPE);
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
