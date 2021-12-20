package com.alt.rmrk.repository;

import com.alt.rmrk.models.Bird;
import com.alt.rmrk.models.BirdLegacy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BirdLegacyRepository extends CrudRepository<BirdLegacy, Integer> {

    @Query(value = "select * from backup_birds", nativeQuery = true)
    Collection<BirdLegacy> findAllFullsets();
}
