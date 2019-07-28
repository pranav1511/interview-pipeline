package com.pranav.interviewpipeline.interview;

import com.pranav.interviewpipeline.stage.Stage;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private long position;

    @ManyToOne
    private Stage stage;

    public Interview() {}

    public Interview(long id, String name, long position, Stage stage) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.stage = stage;
    }
}