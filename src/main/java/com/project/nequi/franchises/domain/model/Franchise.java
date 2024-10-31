package com.project.nequi.franchises.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Franchise {
    private String id;
    private String name;
    private List<Branch> branches;


}
