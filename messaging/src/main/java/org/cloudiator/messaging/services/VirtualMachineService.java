package org.cloudiator.messaging.services;

import org.cloudiator.messages.Vm.CreateVirtualMachineRequestMessage;
import org.cloudiator.messages.Vm.VirtualMachineCreatedResponse;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 27.06.17.
 */
public interface VirtualMachineService
{

  VirtualMachineCreatedResponse createVirtualMachine(CreateVirtualMachineRequestMessage virtualMachineRequestRequest)
      throws ResponseException;

}
