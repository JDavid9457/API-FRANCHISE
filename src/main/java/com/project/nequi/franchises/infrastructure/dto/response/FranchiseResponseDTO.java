package com.project.nequi.franchises.infrastructure.dto.response;

import com.project.nequi.franchises.infrastructure.dto.BranchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FranchiseResponseDTO {
    private String id;
    private String nameFranchise;
    private List<BranchDTO> branchDTOList;
}
