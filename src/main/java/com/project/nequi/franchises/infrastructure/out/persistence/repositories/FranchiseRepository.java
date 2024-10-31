package com.project.nequi.franchises.infrastructure.out.persistence.repositories;

import com.project.nequi.franchises.infrastructure.out.persistence.entity.FranchiseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface FranchiseRepository  extends ReactiveMongoRepository<FranchiseEntity, String> {
}
