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
import org.springframework.util.Assert;
import play.Logger;

import javax.inject.Inject;
import java.util.Optional;

public class EventSourcingEntity extends PersistentEntity<ValueCommand, ValueEvent, EventSourcingState> {

    private PubSubRegistry pubSubRegistry;
    private PubSubRef pubSubRef;


    @Inject
    public EventSourcingEntity(PubSubRegistry pubSubRegistry) {
        this.pubSubRegistry = pubSubRegistry;
        pubSubRef = pubSubRegistry.refFor(TopicId.of(Integer.class, EventSourcingServiceImpl.HARD_CODED_QUALIFIER));


    }

    @Override
    public Behavior initialBehavior(Optional<EventSourcingState> snapshotState) {
        BehaviorBuilder behaviorBuilder = newBehaviorBuilder(snapshotState.orElse(new EventSourcingState(0)));

        behaviorBuilder.setCommandHandler(AddValueCommand.class, (cmd, ctx) -> {
            Integer value = cmd.getValue();
            Logger.info("Got a command to add value: {}", value);
            Assert.isTrue(value.equals(-1) || value.equals(1));
            return ctx.thenPersist(new ValueAddedEvent(value));
        });

        behaviorBuilder.setReadOnlyCommandHandler(GetStateCommand.class, (cmd, ctx) -> ctx.reply(state()));

        behaviorBuilder.setEventHandler(ValueAddedEvent.class, evt -> {
                    Logger.info("Got event with value: {}. Current state: {}", evt.getValue(), state().getValue());
                    final EventSourcingState newState = new EventSourcingState(state().getValue() + evt.getValue());
                    pubSubRef.publish(newState.getValue());
                    return newState;
                }
        );

        return behaviorBuilder.build();


        /*
            There also exists behaviorBuilder.setReadOnlyCommandHandler();
            and behaviorBuilder.setEventHandlerChangingBehavior();
        */

    }

}
