package com.rahul.service.car;

import java.util.Collection;
import java.util.List;

import com.rahul.datatransferobject.CarDTO;
import com.rahul.domainobject.CarDO;
import com.rahul.exception.ConstraintsViolationException;
import com.rahul.exception.EntityNotFoundException;

public interface CarService
{

    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO carDO) throws ConstraintsViolationException;

    CarDO update(CarDO carDO, Long carId) throws ConstraintsViolationException, EntityNotFoundException;

    void delete(Long carId) throws EntityNotFoundException;

    Collection<CarDO> getCars();
    
    List<CarDO> searchCar(CarDTO carDTO);
    
}
