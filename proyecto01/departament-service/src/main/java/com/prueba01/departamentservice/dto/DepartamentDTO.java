package com.prueba01.departamentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentDTO {
    private Long id;
    private String name;
    private String code;
}
