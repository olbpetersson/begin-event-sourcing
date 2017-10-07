package com.squeed.eventsourcing.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

public interface EventSourcingService extends Service {

  //ServiceCall<NotUsed, Integer> sendVote(Integer value);
  ServiceCall<Source<Integer, ?>, Source<Integer, NotUsed>> voteStream();


  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("es").withCalls(
    //    pathCall("/api/es/",  this::sendVote),
        pathCall("/api/es/ws", this::voteStream)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
