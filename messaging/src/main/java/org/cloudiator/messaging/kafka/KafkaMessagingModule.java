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

package org.cloudiator.messaging.kafka;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import org.cloudiator.messaging.MessageInterface;

/**
 * Created by daniel on 13.06.17.
 */
public class KafkaMessagingModule extends AbstractModule {

  private static final String configFileLocation = "kafka.properties";

  @Override
  protected void configure() {
    bind(MessageInterface.class).to(KafkaMessageInterface.class).in(Singleton.class);

    try {
      Properties properties = PropertiesLoader.loadPropertiesFrom(configFileLocation);
      Names.bindProperties(binder(), properties);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private static class PropertiesLoader {

    private final static String ERROR_MESSAGE = "Could not read properties file at location %s";

    static Properties loadPropertiesFrom(String fileName) throws IOException {

      final URL resource = PropertiesLoader.class.getClassLoader().getResource(fileName);

      if (resource == null) {
        throw new IOException(String.format(ERROR_MESSAGE, fileName));
      }

      try {
        final InputStream inputStream = Files
            .newInputStream(Paths.get(resource.toURI()), StandardOpenOption.READ);
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
      } catch (URISyntaxException | IOException e) {
        throw new IOException(
            String.format(ERROR_MESSAGE, resource));
      }
    }

  }

}
