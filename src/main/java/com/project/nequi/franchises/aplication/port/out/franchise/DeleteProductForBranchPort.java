package com.project.nequi.franchises.aplication.port.out.franchise;

import com.project.nequi.franchises.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface DeleteProductForBranchPort {
    Mono<Franchise> deleteProductForBranch(String idFranchise, String idBranch, String idProduct);
}
