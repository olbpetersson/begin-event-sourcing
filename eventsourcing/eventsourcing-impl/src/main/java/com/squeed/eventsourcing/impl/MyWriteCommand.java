package com.squeed.eventsourcing.impl;

public class MyWriteCommand implements MyCommand {

    private final Integer value;

    public MyWriteCommand(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
