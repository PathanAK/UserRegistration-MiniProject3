package com.asif.registrarionformminiproject03.Repository;

import com.asif.registrarionformminiproject03.Entity.StateMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<StateMaster, Integer> {

    public List<StateMaster> findByCountryId(Integer countryId);
}
