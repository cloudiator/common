package org.cloudiator.messaging.services;

import com.google.inject.Inject;
import org.cloudiator.messages.Pricing.PricingQueryRequest;
import org.cloudiator.messages.Pricing.PricingQueryResponse;
import org.cloudiator.messaging.MessageInterface;
import org.cloudiator.messaging.ResponseException;

import javax.inject.Named;

import static com.google.common.base.Preconditions.checkNotNull;

public class PricingServiceImpl implements PricingService {
    private final MessageInterface messageInterface;

    @Inject(optional = true)
    @Named("responseTimeout")
    private long timeout = 20000;

    @Inject
    public PricingServiceImpl(MessageInterface messageInterface) {
        checkNotNull(messageInterface, "messageInterface is null");
        this.messageInterface = messageInterface;
    }

    @Override
    public PricingQueryResponse getPrice(PricingQueryRequest pricingQueryRequest) throws ResponseException {
        return messageInterface.call(pricingQueryRequest, PricingQueryResponse.class, timeout);
    }
}
