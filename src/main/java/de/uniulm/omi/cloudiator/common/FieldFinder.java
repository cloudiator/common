/*
 *  Copyright 2015 University of Ulm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.uniulm.omi.cloudiator.common;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by daniel on 17.11.15.
 */
public class FieldFinder {

    private final Class c;

    private FieldFinder(Class c) {
        this.c = c;
    }

    public static FieldFinder of(Class c) {
        return new FieldFinder(c);
    }

    public Set<Field> getFields() {
        Class clazz = c;
        Set<Field> fields = new HashSet<>();
        while (clazz != null) {
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public Optional<Field> getField(String name) {
        return getFields().stream().filter(field -> field.getName().equals(name)).findAny();
    }

}
