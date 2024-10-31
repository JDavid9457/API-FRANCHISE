package com.project.nequi.franchises.infrastructure.dto.request;

import com.project.nequi.franchises.domain.model.Branch;
import com.project.nequi.franchises.infrastructure.dto.BranchDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FranchiseRequestDTO {
    private String id;
    private String nameFranchise;
    private List<BranchDTO> branches;
}
