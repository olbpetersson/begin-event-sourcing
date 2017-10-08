package com.squeed.eventsourcing.api;

import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceAcl;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

public interface EventSourcingService extends Service {

    ServiceCall<Source<Integer, ?>, Source<Integer, NotUsed>> voteStream();
    ServiceCall<NotUsed, Integer> getState();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("es").withCalls(
                pathCall("/api/es/", this::getState),
                pathCall("/api/es/ws", this::voteStream)
        ).withAutoAcl(true)
                .withServiceAcls(ServiceAcl.methodAndPath(Method.OPTIONS,"/api/es/"));
        // @formatter:on
    }
}
