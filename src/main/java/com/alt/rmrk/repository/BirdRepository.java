package com.alt.rmrk.repository;

import com.alt.rmrk.models.Bird;
import com.alt.rmrk.models.BirdLegacy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public interface BirdRepository extends CrudRepository<Bird, Integer> {

    @Query(value = "select * from birds where fullset = 1", nativeQuery = true)
    Collection<Bird> findAllFullsets();

    @Transactional
    @Modifying
    @Query("update Bird b set b.price = :price where b.birdId=:id")
    void updatePrice(@Param(value = "id") Integer id, @Param(value = "price") Integer price);
}
