/*
 * Copyright 2017 University of Ulm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.uniulm.omi.cloudiator.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by daniel on 13.06.17.
 */
public class PropertiesLoader {

  private final static String ERROR_MESSAGE = "Could not read properties file at location %s";

  private PropertiesLoader() {
    throw new AssertionError("Do not instantiate " + this);
  }

  public static Properties loadPropertiesFrom(String fileName) throws IOException {

    final URL resource = PropertiesLoader.class.getClassLoader().getResource(fileName);

    if (resource == null) {
      throw new IOException(String.format(ERROR_MESSAGE, fileName));
    }

    try {
      final String[] array = resource.toURI().toString().split("!");
      final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), Collections.emptyMap());

      final InputStream inputStream = Files
          .newInputStream(fs.getPath(array[1]), StandardOpenOption.READ);
      Properties properties = new Properties();
      properties.load(inputStream);
      return properties;
    } catch (URISyntaxException | IOException e) {
      throw new IOException(
          String.format(ERROR_MESSAGE, resource));
    }
  }

}
