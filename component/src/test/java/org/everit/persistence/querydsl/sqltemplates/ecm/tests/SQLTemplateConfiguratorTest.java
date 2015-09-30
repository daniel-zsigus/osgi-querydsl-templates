/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.persistence.querydsl.sqltemplates.ecm.tests;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.everit.persistence.querydsl.sqltemplates.ecm.SQLTemplatesConstants;
import org.everit.persistence.querydsl.sqltemplates.ecm.internal.SQLTemplateConfigurator;
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
    subject(config(SQLTemplatesConstants.ATTR_ESCAPE, '$')).configure();
    Assert.assertEquals('$', sqlTemplates.build().getEscapeChar());
  }

  @Test
  public void setNewLineToSingleSpace() {
    subject(config(SQLTemplatesConstants.ATTR_NEW_LINE_TO_SINGLE_SPACE, true)).configure();
    // no accessor for this property
  }

  @Test
  public void setPrintSchema() {
    subject(config(SQLTemplatesConstants.ATTR_PRINT_SCHEMA, true)).configure();
    Assert.assertTrue(sqlTemplates.build().isPrintSchema());
  }

  @Test
  public void setPrintSchemaNullValue() {
    subject(Collections.<String, Object> emptyMap()).configure();
    Assert.assertFalse(sqlTemplates.build().isPrintSchema());
  }

  @Test(expected = ComponentException.class)
  public void setPrintSchemaTypeMismatch() {
    subject(config(SQLTemplatesConstants.ATTR_PRINT_SCHEMA, "invalid")).configure();
  }

  @Test
  public void setQuote() {
    subject(config(SQLTemplatesConstants.ATTR_QUOTE, true)).configure();
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
