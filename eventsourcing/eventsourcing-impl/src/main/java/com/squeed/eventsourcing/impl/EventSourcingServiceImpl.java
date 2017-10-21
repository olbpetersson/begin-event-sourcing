package com.squeed.eventsourcing.impl;

import akka.NotUsed;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.pubsub.PubSubRef;
import com.lightbend.lagom.javadsl.pubsub.PubSubRegistry;
import com.lightbend.lagom.javadsl.pubsub.TopicId;
import com.squeed.eventsourcing.api.EventSourcingService;
import com.squeed.eventsourcing.impl.commands.AddValueCommand;
import com.squeed.eventsourcing.impl.commands.GetStateCommand;
import play.Logger;

import javax.inject.Inject;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Implementation of the EventSourcingService.
 */
public class EventSourcingServiceImpl implements EventSourcingService {

    public static final String HARD_CODED_QUALIFIER = "hard-coded-qualifier";
    public static final String HARD_CODED_ID = "hard-coded-id";
    private PersistentEntityRegistry persistentEntityRegistry;
    private Materializer materializer;
    private PubSubRegistry pubSubRegistry;
    private PersistentEntityRef ref;

    @Inject
    public EventSourcingServiceImpl(PersistentEntityRegistry persistentEntityRegistry, Materializer materializer,
                                    PubSubRegistry pubSubRegistry) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        this.materializer = materializer;
        this.pubSubRegistry = pubSubRegistry;
        this.persistentEntityRegistry = persistentEntityRegistry;
        persistentEntityRegistry.register(EventSourcingEntity.class);
        ref = persistentEntityRegistry.refFor(EventSourcingEntity.class, HARD_CODED_ID);
    }

    @Override
    public ServiceCall<Source<Integer, ?>, Source<Integer, NotUsed>> voteStream() {
        return inputStream -> {
            inputStream.runForeach(input -> {
                AddValueCommand addValueCommand = new AddValueCommand(input);
                ref.ask(addValueCommand);
            }, materializer);
            PubSubRef pubSubRef = pubSubRegistry.refFor(TopicId.of(Integer.class, HARD_CODED_QUALIFIER));
            return completedFuture(pubSubRef.subscriber());
        };
    }

    @Override
    public ServiceCall<NotUsed, Integer> getState() {
        return request -> ref.ask(new GetStateCommand()).thenApply(response ->
                new Integer (((ValueState) response).getValue()));
    }

}
