package com.rahul.dataaccessobject;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rahul.domainobject.CarDO;

/**
 * Database Access Object for car table.
 * <p/>
 */
public interface CarRepository extends JpaRepository<CarDO, Long>, JpaSpecificationExecutor<CarDO>
{

    Collection<CarDO> findAllByDeleted(boolean deleted);

    Optional<CarDO> findByIdAndDeleted(Long carId, boolean deleted);
    
    
}
