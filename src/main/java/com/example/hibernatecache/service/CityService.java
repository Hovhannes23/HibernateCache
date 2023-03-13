package com.example.hibernatecache.service;

import com.example.hibernatecache.entity.City;
import com.example.hibernatecache.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public City getCityById(Integer id){
        return cityRepository.findById(id).get();
    }

    public City saveCity(City city){
        return cityRepository.save(city);
    }
}