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

/**
 * Use {@link DBMSType} instead.
 *
 * @author bence
 */
public final class SQLTemplatesConstants {

    public static final String SERVICE_FACTORY_PID__AUTO_SQL_TEMPLATES =
            "org.everit.osgi.querydsl.templates.AutoSQLTemplates";
    public static final String SERVICE_FACTORY_PID__SQL_TEMPLATES = "org.everit.osgi.querydsl.templates.SQLTemplates";

    public static final String PROP_DB_TYPE = "dbtype";

    public static final String PROP_ESCAPE = "escape";
    public static final String PROP_NEWLINETOSINGLESPACE = "newLineToSingleSpace";
    public static final String PROP_PRINTSCHEMA = "printSchema";
    public static final String PROP_QUOTE = "quote";
    public static final String PROP_SELECTED_TEMPLATE = "selected";

    private SQLTemplatesConstants() {
    }
}
