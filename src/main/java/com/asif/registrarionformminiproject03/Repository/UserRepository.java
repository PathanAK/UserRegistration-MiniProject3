package com.asif.registrarionformminiproject03.Repository;

import com.asif.registrarionformminiproject03.Entity.UserDetails;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetails, Integer> {

    //select * from userDetails where email=?
    public UserDetails findByEmail(String email);

    //select * from userDetails where email=? and password=?
    public UserDetails findByEmailAndPassword(String email, String password);
}
