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
import play.Logger;

import javax.inject.Inject;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Implementation of the EventSourcingService.
 */
public class EventSourcingServiceImpl implements EventSourcingService {

    private final PersistentEntityRegistry persistentEntityRegistry;
    private Materializer materializer;
    private PubSubRegistry pubSubRegistry;

    @Inject
    public EventSourcingServiceImpl(PersistentEntityRegistry persistentEntityRegistry, Materializer materializer,
                                    PubSubRegistry pubSubRegistry) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        this.materializer = materializer;
        this.pubSubRegistry = pubSubRegistry;
        persistentEntityRegistry.register(EventSourcingEntity.class);
    }

    @Override
    public ServiceCall<Source<Integer, ?>, Source<Integer, NotUsed>> voteStream() {
        return inputStream -> {
            inputStream.runForeach(input -> {
                MyWriteCommand myWriteCommand = new MyWriteCommand(input);
                PersistentEntityRef ref = persistentEntityRegistry.refFor(EventSourcingEntity.class, "hard-coded-id");
                ref.ask(myWriteCommand);
            }, materializer);
            PubSubRef pubSubRef = pubSubRegistry.refFor(TopicId.of(Integer.class, "hard-coded-qualifier"));
            Logger.info("Informing subscribers to id {} from voteStream");
            return completedFuture(pubSubRef.subscriber());
        };
    }

}
