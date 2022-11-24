package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Valoracion;
import com.example.repository.ValoracionesRestauranteRepo;
import com.example.repository.ValoracionesRiderRepo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@Service
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ValoracionService {

    @Autowired
    private ValoracionesRiderRepo valRiderRepo;

    @Autowired
    private ValoracionesRestauranteRepo valRestauranteRepo;
    
    public Valoracion saveValoracionRider(Valoracion valoracion) {
        return valRiderRepo.insert(valoracion);
    }

    public Valoracion saveValoracionRestaurante(Valoracion valoracion) {
        return valRestauranteRepo.insert(valoracion);
    }
    public Valoracion updateValoracionRider(Valoracion valoracion) {
        return valRiderRepo.save(valoracion);
    }

    public Valoracion updateValoracionRestaurante(Valoracion valoracion) {
    	return valRestauranteRepo.save(valoracion);
    }
}