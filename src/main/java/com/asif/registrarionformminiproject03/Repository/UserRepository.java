package com.asif.registrarionformminiproject03.Repository;

import com.asif.registrarionformminiproject03.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetails, Integer> {

}
