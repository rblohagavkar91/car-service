package com.rahul.dataaccessobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.rahul.datatransferobject.CarDTO;
import com.rahul.domainobject.CarDO;

public class CarDOSpecification implements Specification<CarDO> {
 
    private static final long serialVersionUID = 869702811668522887L;
    CarDTO carDTO;
 
    public CarDOSpecification(CarDTO carDTO)
    {
        this.carDTO = carDTO;
    }

    @Override
    public Predicate toPredicate
      (Root<CarDO> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();
        
        if (carDTO.getLicensePlate() != null)
        {
            predicates.add(builder.equal(root.get("licensePlate"), carDTO.getLicensePlate()));
        }
        if (carDTO.getSeatCount() != null)
        {
            predicates.add(builder.equal(root.get("seatCount"), carDTO.getSeatCount()));
        }
        if (carDTO.getConvertible() != null)
        {
            predicates.add(builder.equal(root.get("convertible"), carDTO.getConvertible()));
        }
        if (carDTO.getRating() != null)
        {
            predicates.add(builder.equal(root.get("rating"), carDTO.getRating()));
        }
        if (carDTO.getEngineType() != null)
        {
            predicates.add(builder.equal(root.get("engineType"), carDTO.getEngineType()));
        }
        if (carDTO.getManufacturer() != null)
        {
            predicates.add(builder.equal(root.get("manufacturer"), carDTO.getManufacturer()));
        } 
    
        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}