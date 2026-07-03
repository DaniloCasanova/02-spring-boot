package com.proyecto01.employeeservice.controller;

import com.proyecto01.employeeservice.dto.EmployeeDTO;
import com.proyecto01.employeeservice.dto.ResponseDTO;
import com.proyecto01.employeeservice.entity.Employee;
import com.proyecto01.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@AllArgsConstructor
public class EmployeController {
    private final EmployeeService employeeService;

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/{id}")
    public ResponseDTO getEmployeeDeptoById(@PathVariable Long id) {
        return employeeService.getEmployeeDeptoById(id);
    }

    /*Recuperar Lista de Empleados usando filtros*/
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findEmployees (
                @RequestParam(required = false) Long departamentId,
                @RequestParam(required = false) String lastName
            ) {

        //Buscamos empleados con metodo del Servicio
        List<EmployeeDTO> employeesDTO = employeeService.findEmployees(departamentId, lastName);

        //Verificacion Lista vacia
        if (employeesDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        //Todo ok. Retornamos empleados encontrados.
        return ResponseEntity.ok(employeesDTO);
    }

    /*Eliminar Empleado*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        //Eliminamos empleado.
        employeeService.deleteEmployeeById(id);
        //Respondemos ResponseEntity
        //Retorna un código 204 No Content indicando que se eliminó con éxito
        return ResponseEntity.noContent().build();
    }

    //Actualizacion datos Empleado
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id,
                                                       @RequestBody EmployeeDTO employeeDTO) {
        //Actualizamos Empleado.
        Employee employee = employeeService.updateEmployeeById(id, employeeDTO);
        //Respondemos ResponseEntity
        return ResponseEntity.ok(employee);
    }


}
