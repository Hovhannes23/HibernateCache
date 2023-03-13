package com.example.hibernatecache.controller;

import com.example.hibernatecache.entity.City;
import com.example.hibernatecache.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/cities/{id}")
    public ResponseEntity<City> getCityById(@PathVariable(name = "id") Integer id){
        return new ResponseEntity<>(cityService.getCityById(id), HttpStatus.OK);
    }

    @PostMapping("/cities")
    public ResponseEntity<City> saveCity(@RequestBody City city){
        return new ResponseEntity<>(cityService.saveCity(city), HttpStatus.CREATED);
    }
}
