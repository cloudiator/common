package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import javax.inject.Named;
import org.cloudiator.messages.entities.User.AuthRequest;
import org.cloudiator.messages.entities.User.AuthResponse;
import org.cloudiator.messages.entities.User.CreateTenantRequest;
import org.cloudiator.messages.entities.User.CreateTenantResponse;
import org.cloudiator.messages.entities.User.CreateUserRequest;
import org.cloudiator.messages.entities.User.CreateUserResponse;
import org.cloudiator.messages.entities.User.TenantQueryRequest;
import org.cloudiator.messages.entities.User.TenantQueryResponse;
import org.cloudiator.messages.entities.User.TenantCreatedEvent;
import org.cloudiator.messages.entities.User.TenantDeletedEvent;
import org.cloudiator.messages.entities.User.LoginRequest;
import org.cloudiator.messages.entities.User.LoginResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

public class UserServiceImpl implements UserService {

  private final MessageInterface messageInterface;

  @Inject(optional = true)
  @Named("responseTimeout")
  private long timeout = 20000;

  @Inject
  UserServiceImpl(MessageInterface messageInterface) {
    this.messageInterface = messageInterface;
  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) throws ResponseException {
    return messageInterface.call(loginRequest, LoginResponse.class, timeout);
  }

  @Override
  public CreateUserResponse createUser(CreateUserRequest createUserRequest)
      throws ResponseException {
    return messageInterface.call(createUserRequest, CreateUserResponse.class, timeout);
  }

  @Override
  public CreateTenantResponse createTenant(CreateTenantRequest createTenantRequest)
      throws ResponseException {
    return messageInterface.call(createTenantRequest, CreateTenantResponse.class, timeout);
  }

  @Override
  public AuthResponse auth(AuthRequest authRequest) throws ResponseException {
    return messageInterface.call(authRequest, AuthResponse.class, timeout);
  }

  @Override
  public TenantQueryResponse getTenants(TenantQueryRequest getTenantRequest)
      throws ResponseException {
    return messageInterface.call(getTenantRequest, TenantQueryResponse.class, timeout);
  }

  @Override
  public void tenantCreatedEvent(TenantCreatedEvent tenantCreatedEvent) {
    messageInterface.publish(tenantCreatedEvent);
  }

  @Override
  public void tenantDeletedEvent(TenantDeletedEvent tenantDeletedEvent) {
    messageInterface.publish(tenantDeletedEvent);
  }

}
