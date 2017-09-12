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

package org.cloudiator.messaging.services;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.Image.ImageQueryRequest;
import org.cloudiator.messages.Image.ImageQueryResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 02.06.17.
 */
public class ImageServiceImpl implements ImageService {

  private final MessageInterface messageInterface;

  @Inject
  @Named("responseTimeout")
  private long timeout = 20000;

  @Inject
  public ImageServiceImpl(MessageInterface messageInterface) {
    checkNotNull(messageInterface, "messageInterface is null");
    this.messageInterface = messageInterface;
  }

  @Override
  public ImageQueryResponse getImages(ImageQueryRequest imageQueryRequest)
      throws ResponseException {
    return messageInterface.call(imageQueryRequest, ImageQueryResponse.class, timeout);
  }
}
