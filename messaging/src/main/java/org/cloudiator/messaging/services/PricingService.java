package org.cloudiator.messaging.services;

import org.cloudiator.messages.Pricing.PricingQueryRequest;
import org.cloudiator.messages.Pricing.PricingQueryResponse;
import org.cloudiator.messaging.ResponseException;

public interface PricingService {
    PricingQueryResponse getPrice(PricingQueryRequest pricingQueryRequest) throws ResponseException;
}
