package com.rahul.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.controller.mapper.CarMapper;
import com.rahul.datatransferobject.CarDTO;
import com.rahul.domainobject.CarDO;
import com.rahul.exception.ConstraintsViolationException;
import com.rahul.exception.EntityNotFoundException;
import com.rahul.service.car.CarService;

/**
 * All operations(CRUD) with a car will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/cars")
public class CarController
{
    private final CarService carService;


    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }
    
    /**
     * Service for getting a list of all cars
     * @return List of CarDTO objects
     */
    @GetMapping
    public List<CarDTO> getCars()
    {
        return CarMapper.makeCarDTOList(carService.getCars());
    }
    
    /**
     * Service for getting Car Information using carId
     * @param carId
     * @return found CarDTO
     */
    @GetMapping("/{carId}")
    public CarDTO getCar(@PathVariable long carId) throws EntityNotFoundException
    {
        return CarMapper.makeCarDTO(carService.find(carId));
    }

    /**
     * Service for creating/adding Car Information
     * @param carDTO
     * @return created CarDTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }

    /**
     * Service for updating Car Information
     * @param carId & carDTO
     * @return updated CarDTO
     */
    @PutMapping("/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO updateCar(@PathVariable long carId, @RequestBody CarDTO carDTO) throws ConstraintsViolationException, EntityNotFoundException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.update(carDO, carId));
    }

    /**
     * Service for deleting Car Information with input as carId
     * @param carId 
     */
    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
    }
    
    /**
     * Service for searching Cars by their attributes
     * @param carDTO
     * @return list of CarDTO objects
     */
    @PostMapping("/search")
    public List<CarDTO> searchCar(@RequestBody CarDTO carDTO)
    {
        return CarMapper.makeCarDTOList(carService.searchCar(carDTO));
    }
}
