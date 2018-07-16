package org.cloudiator.messaging.services;

import org.cloudiator.messages.entities.User.AuthRequest;
import org.cloudiator.messages.entities.User.AuthResponse;
import org.cloudiator.messages.entities.User.CreateTenantRequest;
import org.cloudiator.messages.entities.User.CreateTenantResponse;
import org.cloudiator.messages.entities.User.CreateUserRequest;
import org.cloudiator.messages.entities.User.CreateUserResponse;
import org.cloudiator.messages.entities.User.LoginRequest;
import org.cloudiator.messages.entities.User.LoginResponse;
import org.cloudiator.messaging.ResponseException;

public interface UserService {

  LoginResponse login(LoginRequest loginRequest) throws ResponseException;

  CreateUserResponse createUser(CreateUserRequest createUserRequest) throws ResponseException;

  CreateTenantResponse createTenant(CreateTenantRequest createTenantRequest)
      throws ResponseException;

  AuthResponse auth(AuthRequest authRequest) throws ResponseException;

}
