package com.example.mural.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IMessageDAO extends CrudRepository<Message, Long> {

    public Message save(Message message);

    public List<Message> findAll();

    @Query("SELECT m FROM Message m ORDER BY m.id DESC")
    public List<Message> findAllOrderedByIdDesc();
}
