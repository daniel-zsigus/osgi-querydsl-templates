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

import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributeOption;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.everit.persistence.querydsl.sqltemplates.ecm.DBMSType;
import org.everit.persistence.querydsl.sqltemplates.ecm.SQLTemplatesConstants;
import org.everit.persistence.querydsl.sqltemplates.ecm.UnknownDatabaseTypeException;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentException;

import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.SQLTemplates.Builder;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * Component that instantiates and registers SQLTemapltes objects as OSGi service.
 */
@Component(componentId = SQLTemplatesConstants.SERVICE_FACTORY_PID_SQL_TEMPLATES, metatype = true,
    configurationPolicy = ConfigurationPolicy.FACTORY, label = "QueryDSL SQLTemplates (Everit)",
    description = "By configuring this component, the user will get an SQLTemplate as an "
        + "OSGi service.")
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = SQLTemplatesConstants.ATTR_DB_TYPE,
        priority = SQLTemplatesAttributePriority.P01_DB_TYPE, label = "Database type",
        description = "Type of the SQLTemplate which will be created.",
        defaultValue = DBMSType.TYPE_H2, options = {
            @StringAttributeOption(label = DBMSType.TYPE_H2,
                value = DBMSType.TYPE_H2),
            @StringAttributeOption(label = DBMSType.TYPE_POSTGRES,
                value = DBMSType.TYPE_POSTGRES),
            @StringAttributeOption(label = DBMSType.TYPE_MYSQL,
                value = DBMSType.TYPE_MYSQL),
            @StringAttributeOption(label = DBMSType.TYPE_ORACLE,
                value = DBMSType.TYPE_ORACLE),
            @StringAttributeOption(label = DBMSType.TYPE_SQLITE,
                value = DBMSType.TYPE_SQLITE),
            @StringAttributeOption(label = DBMSType.TYPE_CUBRID,
                value = DBMSType.TYPE_CUBRID),
            @StringAttributeOption(label = DBMSType.TYPE_DERBY,
                value = DBMSType.TYPE_DERBY),
            @StringAttributeOption(label = DBMSType.TYPE_HSQLDB,
                value = DBMSType.TYPE_HSQLDB),
            @StringAttributeOption(label = DBMSType.TYPE_TERADATA,
                value = DBMSType.TYPE_TERADATA),
            @StringAttributeOption(label = DBMSType.TYPE_SQLSERVER,
                value = DBMSType.TYPE_SQLSERVER),
            @StringAttributeOption(label = DBMSType.TYPE_SQLSERVER_2005,
                value = DBMSType.TYPE_SQLSERVER_2005),
            @StringAttributeOption(label = DBMSType.TYPE_SQLSERVER_2008,
                value = DBMSType.TYPE_SQLSERVER_2008),
            @StringAttributeOption(label = DBMSType.TYPE_SQLSERVER_2012,
                value = DBMSType.TYPE_SQLSERVER_2012) }),
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION,
        defaultValue = SQLTemplatesConstants.DEFAULT_SERVICE_DESCRIPTION_SQL_TEMPLATES,
        priority = SQLTemplatesAttributePriority.P00_SERVICE_DESCRIPTION,
        label = "Service description",
        description = "The description of this component configuration. It is used to easily "
            + "identify the service registered by this component.") })
public class SQLTemplatesComponent extends AbstractSQLTemplatesComponent {

  private SQLTemplates sqlTemplate;

  /**
   * Configures an {@link SQLTemplates} instance based on {@code componentProperties} and registers
   * it as an OSGi service using {@code context}.
   *
   * @throws ComponentException
   *           if problem with to create service register.
   */
  @Activate
  public void activate(final ComponentContext<SQLTemplatesComponent> componentContext) {
    try {
      Map<String, Object> componentProperties = componentContext.getProperties();
      Object dbTypeObject = componentProperties.get(SQLTemplatesConstants.ATTR_DB_TYPE);
      Builder sqlTemplateBuilder = instantiateBuilder((String) dbTypeObject);
      new SQLTemplateConfigurator(sqlTemplateBuilder, componentProperties).configure();
      sqlTemplate = sqlTemplateBuilder.build();
    } catch (UnknownDatabaseTypeException e) {
      throw new ComponentException(e);
    } catch (NullPointerException | ClassCastException e) {
      throw new ComponentException(SQLTemplatesConstants.ATTR_DB_TYPE
          + " property must be set and must be a String", e);
    }

    registerService(componentContext);
  }

  @Override
  protected SQLTemplates getSQLTemplates() {
    return sqlTemplate;
  }

  private Builder instantiateBuilder(final String dbType) {
    Objects.requireNonNull(dbType, "dbType cannot be null");
    for (DBMSType type : DBMSType.values()) {
      if (type.toString().equals(dbType)) {
        return type.getSQLTemplatesBuilder();
      }
    }
    throw new UnknownDatabaseTypeException("database type [" + dbType + "] is not supported");
  }

}
