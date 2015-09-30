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
 * Exception that says if the database type could not have been determined based on the production
 * name and version of the database.
 *
 */
public class UnknownDatabaseTypeException extends RuntimeException {

  /**
   * .
   */
  private static final long serialVersionUID = -7422125247222807335L;

  public UnknownDatabaseTypeException(final String message) {
    super(message);
  }

}
