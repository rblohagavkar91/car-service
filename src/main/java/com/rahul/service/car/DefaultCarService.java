package com.rahul.service.car;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rahul.dataaccessobject.CarDOSpecification;
import com.rahul.dataaccessobject.CarRepository;
import com.rahul.datatransferobject.CarDTO;
import com.rahul.domainobject.CarDO;
import com.rahul.exception.ConstraintsViolationException;
import com.rahul.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some car specific things.
 * <p/>
 */
@Service
public class DefaultCarService implements CarService
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    private final CarRepository carRepository;
    
    public DefaultCarService(final CarRepository carRepository)
    {
        this.carRepository = carRepository;
    }


    /**
     * Method for getting a list of all cars
     */
    @Override
    public Collection<CarDO> getCars()
    {
        return carRepository.findAllByDeleted(false);
    }
    
    /**
     * Method for getting Car Information using carId
     */
    
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return carRepository.findByIdAndDeleted(carId, false)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
    }

    /**
     * Method for creating/adding Car Information
     */
    
    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException
    {
        CarDO car;
        try
        {
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }

    /**
     * Method for updating Car Information
     */
    
    @Override
    @Transactional
    public CarDO update(CarDO carDO, Long carId) throws ConstraintsViolationException, EntityNotFoundException
    {
        CarDO car = find(carId);
        try
        {
            BeanUtils.copyProperties(carDO, car, getNullPropertyNames(carDO));
            car = carRepository.save(car);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }

    /**
     * Method for deleting Car Information with input as carId
     */
    
    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException
    {
        CarDO carDO = find(carId);
        carDO.setDeleted(true);
    }
    
    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
 
        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || (srcValue.getClass() == String.class && "".equals(srcValue)))
            {
                emptyNames.add(pd.getName());   
            }
        }
        return emptyNames.toArray(new String[emptyNames.size()]);
    }


	@Override
	public List<CarDO> searchCar(CarDTO carDTO) {
		
		CarDOSpecification carDOSpecification = new CarDOSpecification(carDTO);
		return carRepository.findAll(carDOSpecification);
	}
    
}
