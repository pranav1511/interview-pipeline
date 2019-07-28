package com.pranav.interviewpipeline.stage;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private long position;

    public Stage() {}

    public Stage(long id, String name, long position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }
}