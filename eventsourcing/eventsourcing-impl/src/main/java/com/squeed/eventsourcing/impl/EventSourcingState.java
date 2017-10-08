package com.squeed.eventsourcing.impl;

public final class EventSourcingState {

    private final int value;

    public EventSourcingState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
