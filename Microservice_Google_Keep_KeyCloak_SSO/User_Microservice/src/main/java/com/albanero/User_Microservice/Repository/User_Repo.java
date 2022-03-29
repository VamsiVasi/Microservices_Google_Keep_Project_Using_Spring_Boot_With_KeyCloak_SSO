package com.albanero.User_Microservice.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.albanero.User_Microservice.Model.Users;

@Repository
public interface User_Repo extends MongoRepository<Users, String> {

	Optional<Users> findByUserName(String userName);

}
