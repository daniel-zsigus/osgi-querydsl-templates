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
package org.everit.persistence.querydsl.sqltemplates.ecm.internal;

import java.util.Map;
import java.util.Objects;

import org.everit.persistence.querydsl.sqltemplates.ecm.SQLTemplatesConstants;
import org.osgi.service.component.ComponentException;

import com.mysema.query.sql.SQLTemplates.Builder;

/**
 * Sets the properties for an instantiated SQLTemplates builder based on the configuration of the
 * component.
 */
public final class SQLTemplateConfigurator {

  private final Map<String, Object> config;

  private final Builder sqlTemplate;

  /**
   * Constructor.
   *
   * @param sqlTemplate
   *          The sql template to be configured.
   * @param config
   *          The component configuration to be used to set up the {@code sqlTemplate}
   * @throws NullPointerException
   *           if any of the parameters is {@code null}
   */
  public SQLTemplateConfigurator(final Builder sqlTemplate, final Map<String, Object> config) {
    this.sqlTemplate = Objects.requireNonNull(sqlTemplate, "sqlTemplate cannot be null");
    this.config = Objects.requireNonNull(config, "config cannot be null");
  }

  private ComponentException componentExceptionForInvalidValue(final String name,
      final Object rawValue, final String expectedType) {
    return new ComponentException(
        "config[" + name + "] is expected to be a " + expectedType + ", got "
            + (rawValue != null ? rawValue.getClass() : "null") + " instead");
  }

  /**
   * Sets the properties for an instantiated SQLTemplates builder based on the configuration of the
   * component.
   */
  public void configure() {
    if (getBooleanProp(SQLTemplatesConstants.ATTR_PRINT_SCHEMA)) {
      sqlTemplate.printSchema();
    }
    if (getBooleanProp(SQLTemplatesConstants.ATTR_QUOTE)) {
      sqlTemplate.quote();
    }
    if (getBooleanProp(SQLTemplatesConstants.ATTR_NEW_LINE_TO_SINGLE_SPACE)) {
      sqlTemplate.newLineToSingleSpace();
    }
    Character escapeChar = getCharacterProp(SQLTemplatesConstants.ATTR_ESCAPE);
    if (escapeChar != null) {
      sqlTemplate.escape(escapeChar.charValue());
    }
  }

  /**
   * Gets boolean property value.
   *
   * @param name
   *          the name of the property to be obtained from {@code config}.
   * @return {@link Boolean#FALSE} if config[name] is not found
   */
  private Boolean getBooleanProp(final String name) {
    Object rawValue = null;
    try {
      rawValue = config.getOrDefault(name, Boolean.FALSE);
      return (Boolean) rawValue;
    } catch (ClassCastException e) {
      throw componentExceptionForInvalidValue(name, rawValue, "Boolean");
    }
  }

  /**
   * Gets char property value.
   *
   * @param name
   *          the name of the property to be obtained from {@code config}.
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
