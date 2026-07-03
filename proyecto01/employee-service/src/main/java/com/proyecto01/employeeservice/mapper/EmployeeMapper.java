package com.proyecto01.employeeservice.mapper;

import com.proyecto01.employeeservice.dto.EmployeeDTO;
import com.proyecto01.employeeservice.entity.Employee;

public class EmployeeMapper {

    //Metodo estatico que recibe la entidad y devuelve el DTO
    public static EmployeeDTO toDTO(Employee employee) {

        //Verificamos objeto recibido
        if (employee == null) {
            return null;
        }

        //Cargamos DTO
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirsName(employee.getFirsName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setDepartamentId(employee.getDepartamentId());

        //Retornamos DTO
        return employeeDTO;
    }
}
