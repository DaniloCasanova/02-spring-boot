package com.proyecto01.employeeservice.service;

import com.proyecto01.employeeservice.dto.DepartamentDTO;
import com.proyecto01.employeeservice.dto.EmployeeDTO;
import com.proyecto01.employeeservice.dto.ResponseDTO;
import com.proyecto01.employeeservice.entity.Employee;
import com.proyecto01.employeeservice.mapper.EmployeeMapper;
import com.proyecto01.employeeservice.repository.EmployeeRepository;
import com.proyecto01.employeeservice.repository.EmployeeSpecs;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /*Recupera Empleado con su Depto por Id*/
    public ResponseDTO getEmployeeDeptoById(Long id) {
        Employee employee = new Employee();
        ResponseDTO responseDTO = new ResponseDTO();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        DepartamentDTO departamentDTO = new DepartamentDTO();

        /*Obtención del empleado*/
        employee = employeeRepository.findById(id).get();
        employeeDTO = EmployeeMapper.toDTO(employee);

        /*Obtenemos depto desde el servicio de departamentos*/
        ResponseEntity<DepartamentDTO> responseEntity =
                restTemplate.getForEntity( "http://localhost:8080/api/departaments/"
                                +employee.getDepartamentId(),
                        DepartamentDTO.class);
        departamentDTO = responseEntity.getBody();

        /*Cargamos respuesta final completa*/
        responseDTO.setEmployeeDTO(employeeDTO);
        responseDTO.setDepartamentDTO(departamentDTO);

        /*Retornamos respuesta final*/
        return responseDTO;
    }

    /*Recupera Lista de Empleados usando filtros*/
    public List<EmployeeDTO> findEmployees (Long departamentId, String lastName) {

        //Se construye la consulta dinámica
        //Se inicializa con una especificacion que siempre es verdadera

        //Obsoleta, ya no se puede asignar null directo al metodo where de Specification
        //Specification<Employee> spec = Specification.where(null);
        Specification<Employee> spec = Specification.unrestricted();

        //Agregamos filtro por departamento
        if (departamentId != null) {
            spec = spec.and(EmployeeSpecs.tieneDepartamentId(departamentId));
        }
        //Agregamos filtro por apellido
        if (lastName != null && !lastName.trim().isEmpty()) {
            spec = spec.and(EmployeeSpecs.tieneLastName(lastName));
        }

        //Se ejecuta la consulta en el repositorio
        List<Employee> employees = employeeRepository.findAll(spec);

        //Se mapea a DTO para proteger la entidad
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    /*Elimina Empleado*/
    public void deleteEmployeeById(Long id) {
        //Verificamos si existe, sino lanzamos excepcion
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado con Id:"+id);
        }
        //Todo ok, eliminamos
        employeeRepository.deleteById(id);
    }

    //Actualizacion datos Empleado
    public Employee updateEmployeeById(Long id, EmployeeDTO employeeDTO) {
        //Verificamos si existe, sino lanzamos excepcion
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con Id:"+id));

        //Todo ok, recargamos nuevos valores
        employee.setFirsName(employeeDTO.getFirsName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartamentId(employeeDTO.getDepartamentId());

        //Regrabamos
        return employeeRepository.save(employee);
    }

}
