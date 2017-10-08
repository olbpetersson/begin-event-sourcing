package com.squeed.eventsourcing.impl.commands;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import com.squeed.eventsourcing.impl.EventSourcingState;

import java.io.Serializable;

public interface ValueCommand extends Jsonable, Serializable, PersistentEntity.ReplyType<EventSourcingState> {
}
