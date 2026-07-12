package com.prueba01.departamentservice.service;

import com.prueba01.departamentservice.dto.DepartamentDTO;
import com.prueba01.departamentservice.entity.Departament;
import com.prueba01.departamentservice.mapper.DepartamentMapper;
import com.prueba01.departamentservice.repository.DepartamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartamentService {
    private final DepartamentRepository departamentRepository;

    public DepartamentService(DepartamentRepository departamentRepository) {
        this.departamentRepository = departamentRepository;
    }

    public DepartamentDTO createDepartament(DepartamentDTO departamentDTO) {
        //Cargamos data nueva entidad
        Departament departamentTmp = new Departament();
        departamentTmp.setName(departamentDTO.getName());
        departamentTmp.setCode(departamentDTO.getCode());

        //Creamos nueva entidad
        Departament departamentNew = new Departament();
        departamentNew = departamentRepository.save(departamentTmp);

        //Mapeamos a DTO
        DepartamentDTO departamentDTONew = new DepartamentDTO();
        departamentDTONew = DepartamentMapper.toDTO(departamentNew);

        //Retornamos DTO solicitada
        return departamentDTONew;
    }

    public DepartamentDTO getDepartamentById(Integer id) {
        //Obtenemos Departamento buscado
        Departament departament = departamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con Id:"+id));

        //Mapeamos a DTO
        DepartamentDTO departamentDTO = new DepartamentDTO();
        departamentDTO = DepartamentMapper.toDTO(departament);

        //Retornamos DTO solicitada
        return departamentDTO;
    }

    public List<DepartamentDTO> findAll() {
        //Obtenemos lista de Departamentos
        List<Departament> departamentos = departamentRepository.findAll();

        //Mapeamos a DTO
        return departamentos.stream()
                .map(DepartamentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteDepartamentById(Integer id) {
        //Verificamos si existe, sino lanzamos excepcion
        if (!departamentRepository.existsById(id)) {
            throw new RuntimeException("Departamento no encontrado con Id:"+id);
        }
        //Todo ok, eliminamos
        departamentRepository.deleteById(id);
    }

    public DepartamentDTO updateDepartamentById(Integer id, DepartamentDTO departamentDTO) {
        //Verificamos si existe, sino lanzamos excepcion
        Departament departament = departamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con Id:"+id));

        //Todo ok, recargamos nuevos valores
        departament.setName(departamentDTO.getName());
        departament.setCode(departamentDTO.getCode());

        //Regrabamos
        Departament departamentUpdated = departamentRepository.save(departament);

        //Mapeamos a DTO
        DepartamentDTO departamentDTOUpdated = new DepartamentDTO();
        departamentDTOUpdated = DepartamentMapper.toDTO(departamentUpdated);

        //Retornamos DTO solicitada
        return departamentDTOUpdated;
    }
}
