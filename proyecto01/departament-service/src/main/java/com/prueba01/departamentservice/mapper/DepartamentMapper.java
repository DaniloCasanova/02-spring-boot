package com.prueba01.departamentservice.mapper;

import com.prueba01.departamentservice.dto.DepartamentDTO;
import com.prueba01.departamentservice.entity.Departament;

public class DepartamentMapper {

    //Metodo estatico que recibe la entidad y devuelve el DTO
    public static DepartamentDTO toDTO(Departament departament) {

        //Verificamos objeto recibido
        if (departament==null) {
            return null;
        }
        //Cargamos DTO
        DepartamentDTO departamentDTO = new DepartamentDTO();
        departamentDTO.setId(departament.getId());
        departamentDTO.setName(departament.getName());
        departamentDTO.setCode(departament.getCode());

        //Retornamos DTO
        return departamentDTO;
    }
}
