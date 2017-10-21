package com.squeed.eventsourcing.impl;

public final class ValueState {

    private final int value;

    public ValueState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
