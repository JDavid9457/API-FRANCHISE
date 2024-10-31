package com.project.nequi.franchises.aplication.port.out.franchise;

import com.project.nequi.franchises.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface UpdateFranchiseNamePort {
    Mono<Franchise> updateFranchiseName(String idFranchise, String newName);

}
