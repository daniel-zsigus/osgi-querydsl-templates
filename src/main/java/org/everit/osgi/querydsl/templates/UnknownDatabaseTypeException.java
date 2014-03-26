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
 * Exception that says if the database type could not have been determined based on the production name and version of
 * the database.
 * 
 */
public class UnknownDatabaseTypeException extends RuntimeException {

    /**
     * .
     */
    private static final long serialVersionUID = -7422125247222807335L;

    public UnknownDatabaseTypeException(String message) {
        super(message);
    }

}
