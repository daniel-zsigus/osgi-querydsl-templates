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

/**
 * Sets the properties for an instantiated SQLTemplates builder based on the configuration of the component.
 */
public final class SQLTemplateConfigurator {

    private final Builder sqlTemplate;

    private final Map<String, Object> config;

    /**
     * @param sqlTemplate
     *            The sql template to be configured.
     * @param properties
     *            The component configuration to be used to set up the {@code sqlTemplate}
     * @throws NullPointerException
     *             if any of the parameters is {@code null}
     */
    public SQLTemplateConfigurator(final Builder sqlTemplate, final Map<String, Object> config) {
        this.sqlTemplate = Objects.requireNonNull(sqlTemplate, "sqlTemplate cannot be null");
        this.config = Objects.requireNonNull(config, "config cannot be null");
    }

    private ComponentException componentExceptionForInvalidValue(final String name, final Object rawValue,
            final String expectedType) {
        return new ComponentException("config[" + name + "] is expected to be a " + expectedType + ", got "
                + rawValue.getClass()
                + " instead");
    }

    /**
     * Sets the properties for an instantiated SQLTemplates builder based on the configuration of the component.
     *
     *
     *
     */
    public void configure() {
        if (getBooleanProp(SQLTemplatesConstants.PROP_PRINTSCHEMA)) {
            sqlTemplate.printSchema();
        }
        if (getBooleanProp(SQLTemplatesConstants.PROP_QUOTE)) {
            sqlTemplate.quote();
        }
        if (getBooleanProp(SQLTemplatesConstants.PROP_NEWLINETOSINGLESPACE)) {
            sqlTemplate.newLineToSingleSpace();
        }
        Character escapeChar = getCharacterProp(SQLTemplatesConstants.PROP_ESCAPE);
        if (escapeChar != null) {
            sqlTemplate.escape(escapeChar.charValue());
        }
    }

    /**
     * @param name
     * @return {@link Boolean#FALSE} if config[name] is not found
     */
    private Boolean getBooleanProp(final String name) {
        Object rawValue = null;
        try {
            rawValue = config.get(name);
            return rawValue == null ? Boolean.FALSE : (Boolean) rawValue;
        } catch (ClassCastException e) {
            throw componentExceptionForInvalidValue(name, rawValue, "Boolean");
        }
    }

    /**
     * @param name
     * @return {@code null} if config[name] is not found
     */
    private Character getCharacterProp(final String name) {
        Object rawValue = null;
        try {
            rawValue = config.get(name);
            return (Character) rawValue;
        } catch (ClassCastException e) {
            throw componentExceptionForInvalidValue(name, rawValue, "Character");
        }
    }

}
