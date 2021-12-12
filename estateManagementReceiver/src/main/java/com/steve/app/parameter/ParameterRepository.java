package com.steve.app.parameter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer>{

	Parameter findByParameterName(String pName);
}
