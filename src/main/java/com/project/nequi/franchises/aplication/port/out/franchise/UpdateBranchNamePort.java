package com.project.nequi.franchises.aplication.port.out.franchise;

import com.project.nequi.franchises.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface UpdateBranchNamePort {
    Mono<Franchise> updateBranchName(String idFranchise, String idBranch, String newName);
}
