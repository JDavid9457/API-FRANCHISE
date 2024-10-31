package com.project.nequi.franchises.aplication.port.out.franchise;

import com.project.nequi.franchises.domain.model.Franchise;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ListFranchisePort {
    Flux<Franchise> listFranchise();
}
