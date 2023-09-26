package com.asif.registrarionformminiproject03.Repository;

import com.asif.registrarionformminiproject03.Entity.CountryMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryMaster, Integer> {

}

