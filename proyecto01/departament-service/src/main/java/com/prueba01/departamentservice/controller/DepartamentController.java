package com.prueba01.departamentservice.controller;

import com.prueba01.departamentservice.dto.DepartamentDTO;
import com.prueba01.departamentservice.entity.Departament;
import com.prueba01.departamentservice.repository.DepartamentRepository;
import com.prueba01.departamentservice.service.DepartamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departaments")
//Habilitamos acceso desde (Localhost) Live Server
@CrossOrigin(origins = "http://127.0.0.1:5500") // Add this line

public class DepartamentController {
    private final DepartamentService departamentService;

    public DepartamentController(DepartamentService departamentService) {
        this.departamentService = departamentService;
    }

    @PostMapping
    public ResponseEntity<DepartamentDTO> createDepartament(@RequestBody DepartamentDTO departamentDTO) {
        DepartamentDTO departamentDTONew = departamentService.createDepartament(departamentDTO);

        //Respondemos ResponseEntity
        return ResponseEntity.ok(departamentDTONew);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentDTO> getDepartamentById(@PathVariable Integer id) {
        DepartamentDTO departamentDTO = departamentService.getDepartamentById(id);
        //Respondemos ResponseEntity
        return ResponseEntity.ok(departamentDTO);
    }

    @GetMapping
    public ResponseEntity<List<DepartamentDTO>> findAll() {
        //Buscamos departamentos con metodo del Servicio
        List<DepartamentDTO> departamentosDTO = departamentService.findAll();

        //Verificacion Lista vacia
        if (departamentosDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        //Todo ok. Retornamos empleados encontrados.
        return ResponseEntity.ok(departamentosDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamentById(@PathVariable Integer id) {
        //Eliminamos depto.
        departamentService.deleteDepartamentById(id);
        //Respondemos ResponseEntity
        //Retorna un código 204 No Content indicando que se eliminó con éxito
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentDTO> updateDepartamentById(@PathVariable Integer id,
                                                                @RequestBody DepartamentDTO departamentDTO) {
        //Actualizamos depto.
        DepartamentDTO departamentDTOUpdated = departamentService.updateDepartamentById(id, departamentDTO);

        //Respondemos ResponseEntity
        return ResponseEntity.ok(departamentDTOUpdated);
    }

}
