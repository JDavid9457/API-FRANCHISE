package com.project.nequi.franchises.aplication.port.out.franchise;

import com.project.nequi.franchises.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetProductForMaximumStockPort {
    Mono<Product> getProductForMaximumStock(String idFranchise, String idBranch);

}
