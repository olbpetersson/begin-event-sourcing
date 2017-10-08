package com.squeed.eventsourcing.impl.events;

public class ValueAddedEvent implements ValueEvent {

    private final int value;

    public ValueAddedEvent(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
