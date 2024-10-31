package com.project.nequi.franchises.aplication.port.out.franchise;

import com.project.nequi.franchises.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface RegisterBranchForFranchisePort {
    Mono<Branch> RegisterBranchForFranchise(String idFranchise, Branch branch);
}
