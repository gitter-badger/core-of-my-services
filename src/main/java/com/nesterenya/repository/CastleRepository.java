package com.nesterenya.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.nesterenya.modal.Castle;

@Repository
public interface CastleRepository extends  MongoRepository<Castle, String>  {
	//public Castle findByFirstName(String firstName);
    //public List<Castle> findByLastName(String lastName);
}