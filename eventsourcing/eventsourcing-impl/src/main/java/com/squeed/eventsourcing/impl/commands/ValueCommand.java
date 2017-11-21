package com.squeed.eventsourcing.impl.commands;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import com.squeed.eventsourcing.impl.ValueState;

import java.io.Serializable;

public interface ValueCommand
        extends Serializable, PersistentEntity.ReplyType<ValueState> {
}
