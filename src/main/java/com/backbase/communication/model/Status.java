package com.backbase.communication.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Status {
    private String ref;
    private String state;
    private Error error;

    public Status(String state) {
        this.state = state;
    }
}

