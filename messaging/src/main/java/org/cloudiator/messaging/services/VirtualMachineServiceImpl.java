package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.Vm.CreateVirtualMachineRequestMessage;
import org.cloudiator.messages.Vm.VirtualMachineCreatedResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 27.06.17.
 */
public class VirtualMachineServiceImpl implements VirtualMachineService {

  private final MessageInterface messageInterface;
  private long timeout = 0;

  @Inject
  public VirtualMachineServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Inject
  public void setResponseTimeout(@Named("responseTimeout") long timeout) {
    this.timeout = timeout;
  }

  @Override
  public VirtualMachineCreatedResponse createVirtualMachine(
      CreateVirtualMachineRequestMessage virtualMachineRequestRequest) throws ResponseException {
    return messageInterface
        .call(virtualMachineRequestRequest, VirtualMachineCreatedResponse.class, timeout);
  }
}
