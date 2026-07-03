package com.prueba01.departamentservice.repository;

import com.prueba01.departamentservice.entity.Departament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentRepository extends JpaRepository<Departament, Integer> {
}
