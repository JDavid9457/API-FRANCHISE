package com.project.nequi.franchises.aplication.port.out.franchise;

import com.project.nequi.franchises.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface UpdateProductNamePort {
    Mono<Franchise> updateProductName(String idFranchise, String idBranch, String idProduct, String newName);
}
