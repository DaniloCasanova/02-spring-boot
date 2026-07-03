package com.proyecto01.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private EmployeeDTO employeeDTO;
    private DepartamentDTO departamentDTO;
}
