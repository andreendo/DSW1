package com.example.mural.repositories;

import jakarta.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //private int id; // usar tipo Long para ids
    private Long id;
    @Column(name = "mFrom") // from é keyword do SQL, evita erro na criação do BD
    private String from;
    @Column(name = "mTo")
    private String to;
    private String message;
    private String timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return String.format("Message[id='%d', from='%s', to='%s', message='%s', timestamp='%s']", id, from, to, message, timestamp);
    }
}
