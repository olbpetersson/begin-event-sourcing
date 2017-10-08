package com.squeed.eventsourcing.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.squeed.eventsourcing.api.EventSourcingService;

/**
 * The module that binds the EventSourcingService so that it can be served.
 */
public class EventSourcingModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindService(EventSourcingService.class, EventSourcingServiceImpl.class);
  }
}
