package com.example.repository;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.model.Valoracion;
/*Repositorio que guarda las valoraciones de rider*/
@Document(value = "ValoracionesRider")
public interface ValoracionesRiderRepo extends MongoRepository<Valoracion, String>{

}