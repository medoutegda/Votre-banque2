package org.me.dao;

import org.me.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends  JpaRepository <Compte, String> {

}
