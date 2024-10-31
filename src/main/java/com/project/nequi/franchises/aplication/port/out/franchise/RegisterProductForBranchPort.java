package com.project.nequi.franchises.aplication.port.out.franchise;

import com.project.nequi.franchises.domain.model.Franchise;
import com.project.nequi.franchises.domain.model.Product;
import reactor.core.publisher.Mono;

public interface RegisterProductForBranchPort {
    Mono<Franchise> registerProductForBranch(String idFranchise, String idBranch, Product product);
}
