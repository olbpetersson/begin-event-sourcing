package com.squeed.eventsourcing.impl;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

import java.io.Serializable;

public interface MyCommand extends Jsonable, Serializable, PersistentEntity.ReplyType<EventSourcingState> {
}
