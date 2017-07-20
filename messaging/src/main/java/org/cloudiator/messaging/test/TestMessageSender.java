package org.cloudiator.messaging.test;

import javax.inject.Inject;
import org.cloudiator.messages.Installation.ToolInstalledRequest;
import org.cloudiator.messages.Installation.ToolInstalledRequest.Builder;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.Subscription;
import org.cloudiator.messaging.services.CloudService;

public class TestMessageSender {
  private final MessageInterface messagingService;
  private final CloudService cloudService;
  private volatile Subscription subscription;

  @Inject
  public TestMessageSender(MessageInterface messageInterface,
      CloudService cloudService) {
    this.messagingService = messageInterface;
    this.cloudService = cloudService;
  }

  public void sendMessage(){
    final Builder builder = ToolInstalledRequest.newBuilder();
    builder.setUserId("1");
    final ToolInstalledRequest build = builder.build();

    messagingService.publish(build);
  }
}
