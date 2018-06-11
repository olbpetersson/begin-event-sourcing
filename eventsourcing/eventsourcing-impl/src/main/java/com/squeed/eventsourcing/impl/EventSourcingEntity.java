package com.squeed.eventsourcing.impl;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.javadsl.pubsub.PubSubRef;
import com.lightbend.lagom.javadsl.pubsub.PubSubRegistry;
import com.lightbend.lagom.javadsl.pubsub.TopicId;
import com.squeed.eventsourcing.impl.commands.AddValueCommand;
import com.squeed.eventsourcing.impl.commands.GetStateCommand;
import com.squeed.eventsourcing.impl.commands.ValueCommand;
import com.squeed.eventsourcing.impl.events.ValueAddedEvent;
import com.squeed.eventsourcing.impl.events.ValueEvent;
import play.Logger;

import javax.inject.Inject;
import java.util.Optional;

public class EventSourcingEntity
        extends PersistentEntity<ValueCommand, ValueEvent, ValueState> {

    private PubSubRegistry pubSubRegistry;
    private PubSubRef pubSubRef;


    @Inject
    public EventSourcingEntity(PubSubRegistry pubSubRegistry) {
        this.pubSubRegistry = pubSubRegistry;
        this.pubSubRef = pubSubRegistry.refFor(TopicId.of(Integer.class, EventSourcingServiceImpl.HARD_CODED_QUALIFIER));
    }

    @Override
    public Behavior initialBehavior(Optional<ValueState> snapshotState) {
        BehaviorBuilder behaviorBuilder =
                newBehaviorBuilder(snapshotState.orElse(new ValueState(0)));

        behaviorBuilder.setCommandHandler(AddValueCommand.class, (cmd, ctx) -> {
            Integer value = cmd.getValue();
            Logger.info("\n --- Current state: {}\n --- Got a command to add value: {}", state().getValue(), value);
            if(!(value.equals(-1) || value.equals(1))) {
                throw new IllegalArgumentException(String.format("%s is not a legal value", value));
            }
            return ctx.thenPersist(new ValueAddedEvent(value));
        });

        behaviorBuilder.setReadOnlyCommandHandler(GetStateCommand.class, (cmd, ctx) -> ctx.reply(state()));

        behaviorBuilder.setEventHandler(ValueAddedEvent.class, evt -> {
                    Logger.info("\n --- Applying event with value: {}", evt.getValue());
                    final ValueState newState = new ValueState(state().getValue()
                            + evt.getValue());
                    pubSubRef.publish(newState.getValue());
                    Logger.info("\n --- New state is {} + {}: {} \n\n", state().getValue(), evt.getValue(), newState.getValue());
                    return newState;
                }
        );

        return behaviorBuilder.build();

    }
}
