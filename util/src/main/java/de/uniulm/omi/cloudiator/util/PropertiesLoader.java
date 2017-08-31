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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daniel on 13.06.17.
 */
public class PropertiesLoader {

  private final static String JAR_FILE_SCHEMA = "jar";
  private final static String FILE_SCHEMA = "file";

  private final static String ERROR_MESSAGE = "Could not read properties file from systemProperty %s or as resource %s.";
  private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

  private PropertiesLoader() {
    throw new AssertionError("Do not instantiate " + this);
  }


  public static Properties loadPropertiesWithException(String systemPropertyKey,
      String resourceName) throws IOException {
    Properties p = loadPropertiesFrom(systemPropertyKey, resourceName);
    if (p == null) {
      throw new IOException(String.format(ERROR_MESSAGE, systemPropertyKey, resourceName));
    }
    return p;
  }

  /**
   * this method retrieves the values of the system property passed as a parameter and interprets
   * the value as a file name. It then tries to load the content of this file. If this failes, it
   * tries to find a resource on the class path to load.
   *
   * @param systemPropertyKey the system property key to use
   * @return the filled Properties object or null if (i) the path is null or empty, (ii) the file
   * does not exist at that location is not a real file, (iii) an IOException occurred while reading
   * the file, (iv) the system property has not been set (v) the resource is not found on the
   * classpath, (vi) it cannot be loaded from the classpath.
   */
  public static Properties loadPropertiesFrom(String systemPropertyKey, String resourceName) {
    LOGGER.info("loading properties from systemPropertyKey or from class path resource");
    Properties p = loadPropertiesFromSystempropertyKey(systemPropertyKey);
    if (p != null) {
      return p;
    }
    LOGGER.warn("could not load properties from system properties. Trying classpath");
    return loadPropertiesFromClassPathResource(resourceName);
  }


  private static InputStream loadFromRegularFile(URI uri) throws IOException {
    return Files.newInputStream(Paths.get(uri), StandardOpenOption.READ);
  }

  public static Properties loadPropertiesFromClassPathResource(String resourceName) {
    final URL resource = PropertiesLoader.class.getClassLoader().getResource(resourceName);

    if (resource == null) {
      LOGGER.warn("could not find resource '" + resourceName + "' on class path. giving up.");
      return null;
    }

    try {
      URI uri = resource.toURI();
      InputStream in = null;
      if (JAR_FILE_SCHEMA.equals(uri.getScheme())) {
        in = loadFromJarFile(uri);
      } else if (FILE_SCHEMA.equals(uri.getScheme())) {
        in = loadFromRegularFile(uri);
      }

      if (in != null) {
        Properties properties = new Properties();
        properties.load(in);
        return properties;
      } else {
        LOGGER.warn("could not read resource '" + resourceName + "' on class path. giving up.");
        return null;
      }
    } catch (URISyntaxException | IOException e) {
      LOGGER.warn("could not read resource '" + resourceName + "' on class path. giving up.", e);
      return null;
    }
  }

  private static InputStream loadFromJarFile(URI resourceUri) throws IOException {
    // until first "!" is the path of the jar file itself //
    final String[] array = resourceUri.toString().split("!");
    final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), Collections.emptyMap());
    List<String> params = new ArrayList<>();
    for (int i = 2; i < array.length; i++) {
      params.add(array[i]);
    }

    return Files.newInputStream(fs.getPath(array[1], params.toArray(new String[params.size()])),
        StandardOpenOption.READ);
  }


  /**
   * this method retrieves the values of the system property passed as a parameter and interprets
   * the value as a file name. It then tries to load the content of this file
   *
   * @param systemPropertyKey the system property key to use
   * @return the filled Properties object or null if (i) the path is null or empty, (ii) the file
   * does not exist at that location is not a real file, (iii) an IOException occurred while reading
   * the file, (iv) the system property has not been set.
   */
  public static Properties loadPropertiesFromSystempropertyKey(String systemPropertyKey) {
    String value = System.getProperty(systemPropertyKey);
    // should work fine. The called method is very tolerant //
    return loadPropertiesFromFileSystem(value);
  }

  /**
   * Opens the file on the file system and loads its content as Java Properties
   *
   * @param pathToFile the path to the file
   * @return the filled Properties object or null if (i) the path is null or empty, (ii) the file
   * does not exist at that location is not a real file, (iii) an IOException occurred while reading
   * the file.
   */
  public static Properties loadPropertiesFromFileSystem(String pathToFile) {
    if (pathToFile == null || pathToFile.isEmpty()) {
      LOGGER.warn("Name for file is null or empty. giving up loading properties.");
      return null;
    }
    File f = new File(pathToFile);
    if (!f.exists() || !f.isFile()) {
      LOGGER.warn("Property file '" + pathToFile
          + "' does not exists or is not a file. giving up loading properties.");
      return null;
    }
    LOGGER.debug("Trying to open properties file '" + pathToFile + "'");

    try (FileInputStream fis = new FileInputStream(f)) {
      Properties p = new Properties();
      p.load(fis);
      return p;
    } catch (IOException ioe) {
      LOGGER.warn("Property file '" + pathToFile + "' cannot be read. Giving up loading properties",
          ioe);
      return null;
    }
  }

  /**
   * @param fileName the resource name of the class path resource
   * @return properties as loaded from the resource
   * @throws IOException in case no properties were found
   */
  @Deprecated
  public static Properties loadPropertiesFrom(String fileName) throws IOException {
    Properties p = loadPropertiesFromClassPathResource(fileName);
    if (p == null) {
      throw new IOException(String.format(ERROR_MESSAGE, fileName, fileName));
    }
    return p;
  }

}
