package com.project.nequi.franchises.infrastructure.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collation = "branchs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BranchEntity {
    @Id
    private String id;

    private String nameBranch;

    private List<ProductEntity> products;

}
