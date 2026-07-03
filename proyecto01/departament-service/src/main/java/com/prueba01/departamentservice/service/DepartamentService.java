package com.prueba01.departamentservice.service;

import com.prueba01.departamentservice.dto.DepartamentDTO;
import com.prueba01.departamentservice.entity.Departament;
import com.prueba01.departamentservice.repository.DepartamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentService {
    private final DepartamentRepository departamentRepository;

    public DepartamentService(DepartamentRepository departamentRepository) {
        this.departamentRepository = departamentRepository;
    }

    public Departament createDepartament(Departament departament) {
        return departamentRepository.save(departament);
    }

    public Departament getDepartamentById(Integer id) {
        return departamentRepository.findById(id).get();
    }

    public List<Departament> findAll() {
        return departamentRepository.findAll();
    }

    public void deleteDepartamentById(Integer id) {
        //Verificamos si existe, sino lanzamos excepcion
        if (!departamentRepository.existsById(id)) {
            throw new RuntimeException("Departamento no encontrado con Id:"+id);
        }
        //Todo ok, eliminamos
        departamentRepository.deleteById(id);
    }

    public Departament updateDepartamentById(Integer id, DepartamentDTO departamentDTO) {
        //Verificamos si existe, sino lanzamos excepcion
        Departament departament = departamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con Id:"+id));

        //Todo ok, recargamos nuevos valores
        departament.setName(departamentDTO.getName());
        departament.setCode(departamentDTO.getCode());

        //Regrabamos
        return departamentRepository.save(departament);
    }
}
