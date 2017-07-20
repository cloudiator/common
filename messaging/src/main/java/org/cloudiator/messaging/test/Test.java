package org.cloudiator.messaging.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.inject.Inject;
import org.cloudiator.messages.Installation.ToolInstalledRequest;
import org.cloudiator.messages.Installation.ToolInstalledRequest.Builder;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.Subscription;
import org.cloudiator.messaging.kafka.KafkaMessagingModule;
import org.cloudiator.messaging.services.CloudService;
import org.cloudiator.messaging.services.MessageServiceModule;

public class Test {


  private static Injector injector =
      Guice.createInjector(new KafkaMessagingModule(), new MessageServiceModule());

  /**
   * starts the virtual machine agent.
   *
   * @param args args
   */
  public static void main(String[] args) {
    final TestMessageSender testMessageSender =
        injector.getInstance(TestMessageSender.class);

    testMessageSender.sendMessage();
  }
}
