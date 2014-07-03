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
package org.everit.osgi.org.everit.osgi.querydsl.templates;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.everit.osgi.querydsl.templates.SQLTemplatesConstants;
import org.everit.osgi.querydsl.templates.internal.SQLTemplateConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.component.ComponentException;

import com.mysema.query.sql.H2Templates;
import com.mysema.query.sql.SQLTemplates.Builder;

public class SQLTemplateConfiguratorTest {

    private Builder sqlTemplates;

    private Map<String, Object> config(final String propName, final Object propValue) {
        Map<String, Object> rval = new HashMap<>();
        rval.put(propName, propValue);
        return rval;
    }

    @Test(expected = NullPointerException.class)
    public void constructorFailureNullConfig() {
        new SQLTemplateConfigurator(sqlTemplates, null);
    }

    @Test(expected = NullPointerException.class)
    public void constructorNullTemplate() {
        new SQLTemplateConfigurator(null, null);
    }

    @Test
    public void setEscapeCharacter() {
        subject(config(SQLTemplatesConstants.PROP_ESCAPE, '$')).configure();
        Assert.assertEquals('$', sqlTemplates.build().getEscapeChar());
    }

    @Test
    public void setNewLineToSingleSpace() {
        subject(config(SQLTemplatesConstants.PROP_NEWLINETOSINGLESPACE, true)).configure();
        // no accessor for this property
    }

    @Test
    public void setPrintSchema() {
        subject(config(SQLTemplatesConstants.PROP_PRINTSCHEMA, true)).configure();
        Assert.assertTrue(sqlTemplates.build().isPrintSchema());
    }

    @Test
    public void setPrintSchemaNullValue() {
        subject(Collections.<String, Object> emptyMap()).configure();
        Assert.assertFalse(sqlTemplates.build().isPrintSchema());
    }

    @Test(expected = ComponentException.class)
    public void setPrintSchemaTypeMismatch() {
        subject(config(SQLTemplatesConstants.PROP_PRINTSCHEMA, "invalid")).configure();
    }

    @Test
    public void setQuote() {
        subject(config(SQLTemplatesConstants.PROP_QUOTE, true)).configure();
        Assert.assertTrue(sqlTemplates.build().isUseQuotes());
    }

    @Before
    public void setUp() {
        sqlTemplates = H2Templates.builder();
    }

    private SQLTemplateConfigurator subject(final Map<String, Object> config) {
        return new SQLTemplateConfigurator(sqlTemplates, config);
    }

}
