package com.example.madcamp_4week.service;

import com.example.madcamp_4week.domain.Atmosphere;
import com.example.madcamp_4week.repository.AtmosphereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class AtmosphereService {



    private final AtmosphereRepository atmosphereRepository;

    public List<Atmosphere> getAllAtmospheres()
    {
        return atmosphereRepository.findAll();
    }
}
