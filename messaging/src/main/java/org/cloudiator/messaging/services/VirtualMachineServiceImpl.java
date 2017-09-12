package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.Vm.CreateVirtualMachineRequestMessage;
import org.cloudiator.messages.Vm.VirtualMachineCreatedResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseCallback;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 27.06.17.
 */
public class VirtualMachineServiceImpl implements VirtualMachineService {

  private final MessageInterface messageInterface;
  
  @Inject
  @Named("responseTimeout")
  private long timeout = 20000;

  @Inject
  public VirtualMachineServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public VirtualMachineCreatedResponse createVirtualMachine(
      CreateVirtualMachineRequestMessage virtualMachineRequestRequest) throws ResponseException {
    return messageInterface
        .call(virtualMachineRequestRequest, VirtualMachineCreatedResponse.class, timeout);
  }

  @Override
  public void createVirtualMachineAsync(
      CreateVirtualMachineRequestMessage virtualMachineRequestMessage,
      ResponseCallback<VirtualMachineCreatedResponse> callback) {
    messageInterface
        .callAsync(virtualMachineRequestMessage, VirtualMachineCreatedResponse.class, callback);
  }
}
