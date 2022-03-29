package com.albanero.Notes_Microservice.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.albanero.Notes_Microservice.Model.Notes;

@Repository
public interface Notes_Repo extends MongoRepository<Notes, String> {

	Optional<Notes> findByName(String name);

}
