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

import org.everit.osgi.dev.testrunner.TestRunnerConstants;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.everit.persistence.querydsl.sqltemplates.ecm.SQLTemplatesConstants;
import org.junit.Assert;
import org.junit.Test;

import com.mysema.query.sql.SQLTemplates;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * Test for Auto SQLTemplates Component.
 */
@Component(componentId = "sqlTemplatesComponentTest")
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TESTRUNNER_ENGINE_TYPE,
        defaultValue = "junit4"),
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TEST_ID,
        defaultValue = "sqlTemplatesComponentTest") })
@Service(SQLTemplatesComponentTest.class)
public class SQLTemplatesComponentTest {

  private SQLTemplates sqlTemplates;

  @ServiceRef(defaultValue = "(service.description="
      + SQLTemplatesConstants.DEFAULT_SERVICE_DESCRIPTION_SQL_TEMPLATES + ")")
  public void setSqlTemplates(final SQLTemplates sqlTemplates) {
    this.sqlTemplates = sqlTemplates;
  }

  @Test
  public void testThatComponentIsAlive() {
    Assert.assertNotNull("Auto SQLTemplates is not binded.", sqlTemplates);
  }
}
