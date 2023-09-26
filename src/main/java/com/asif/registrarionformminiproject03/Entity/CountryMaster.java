package com.asif.registrarionformminiproject03.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PublicKey;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryMaster {

    @Id
    private Integer id;
    private String cName;
}
