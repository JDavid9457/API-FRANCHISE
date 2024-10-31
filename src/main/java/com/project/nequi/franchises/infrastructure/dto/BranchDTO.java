package com.project.nequi.franchises.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BranchDTO {
    private String id;
    private String name;
    private List<ProductDTO> products;
}
