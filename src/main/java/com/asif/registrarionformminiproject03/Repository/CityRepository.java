package com.asif.registrarionformminiproject03.Repository;

import com.asif.registrarionformminiproject03.Entity.CityMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<CityMaster, Integer> {

    public List<CityMaster> findByStateId(Integer stateId);
}
