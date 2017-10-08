package com.squeed.eventsourcing.impl.commands;

public class AddValueCommand implements ValueCommand {

    private final Integer value;

    public AddValueCommand(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
