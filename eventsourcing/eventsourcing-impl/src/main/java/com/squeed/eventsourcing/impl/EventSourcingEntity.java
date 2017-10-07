package com.squeed.eventsourcing.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import com.squeed.eventsourcing.impl.HelloCommand.Hello;
import com.squeed.eventsourcing.impl.HelloCommand.UseGreetingMessage;
import com.squeed.eventsourcing.impl.HelloEvent.GreetingMessageChanged;

/**
 * This is an event sourced entity. It has a state, {@link EventSourcingState}, which
 * stores what the greeting should be (eg, "Hello").
 * <p>
 * Event sourced entities are interacted with by sending them commands. This
 * entity supports two commands, a {@link UseGreetingMessage} command, which is
 * used to change the greeting, and a {@link Hello} command, which is a read
 * only command which returns a greeting to the name specified by the command.
 * <p>
 * Commands get translated to events, and it's the events that get persisted by
 * the entity. Each event will have an event handler registered for it, and an
 * event handler simply applies an event to the current state. This will be done
 * when the event is first created, and it will also be done when the entity is
 * loaded from the database - each event will be replayed to recreate the state
 * of the entity.
 * <p>
 * This entity defines one event, the {@link GreetingMessageChanged} event,
 * which is emitted when a {@link UseGreetingMessage} command is received.
 */
public class EventSourcingEntity extends PersistentEntity<MyCommand, MyEvent, EventSourcingState> {

  /**
   * An entity can define different behaviours for different states, but it will
   * always start with an initial behaviour. This entity only has one behaviour.
   */
  @Override
  public Behavior initialBehavior(Optional<EventSourcingState> snapshotState) {
    return null;
  }

}
