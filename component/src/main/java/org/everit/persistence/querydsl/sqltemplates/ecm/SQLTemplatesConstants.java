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
package org.everit.persistence.querydsl.sqltemplates.ecm;

/**
 * Use {@link DBMSType} instead.
 */
public final class SQLTemplatesConstants {

  public static final String ATTR_DATA_SOURCE = "dataSource.target";

  public static final String ATTR_DB_TYPE = "dbtype";

  public static final String ATTR_ESCAPE = "escape";

  public static final String ATTR_LOG_SERVICE = "logService.target";

  public static final String ATTR_NEW_LINE_TO_SINGLE_SPACE = "newLineToSingleSpace";

  public static final String ATTR_PRINT_SCHEMA = "printSchema";

  public static final String ATTR_QUOTE = "quote";

  public static final String ATTR_SELECTED_TEMPLATE = "selected";

  public static final String DEFAULT_SERVICE_DESCRIPTION_AUTO_SQL_TEMPLATES =
      "Default Auto SQL Template Component";

  public static final String DEFAULT_SERVICE_DESCRIPTION_SQL_TEMPLATES =
      "Default SQL Template Component";

  public static final String SERVICE_FACTORY_PID_AUTO_SQL_TEMPLATES =
      "org.everit.persistence.querydsl.sqltemplates.AutoSQLTemplates";

  public static final String SERVICE_FACTORY_PID_SQL_TEMPLATES =
      "org.everit.persistence.querydsl.sqltemplates.SQLTemplates";

  private SQLTemplatesConstants() {
  }
}
