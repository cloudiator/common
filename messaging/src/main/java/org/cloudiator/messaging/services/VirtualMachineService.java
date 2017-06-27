package org.cloudiator.messaging.services;

import org.cloudiator.messages.Vm.CreateVirtualMachineRequestRequest;
import org.cloudiator.messages.Vm.VirtualMachineCreatedResponse;
import org.cloudiator.messaging.ResponseException;

/**
 * Created by daniel on 27.06.17.
 */
public interface VirtualMachineService
{

  VirtualMachineCreatedResponse createVirtualMachine(CreateVirtualMachineRequestRequest virtualMachineRequestRequest)
      throws ResponseException;

}
