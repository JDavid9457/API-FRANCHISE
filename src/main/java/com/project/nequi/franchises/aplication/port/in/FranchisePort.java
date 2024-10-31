package com.project.nequi.franchises.aplication.port.in;

import com.project.nequi.franchises.domain.model.Branch;
import com.project.nequi.franchises.domain.model.Franchise;
import com.project.nequi.franchises.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchisePort {

    Mono<Franchise> save(Franchise franchise);

    Flux<Franchise> findAll();

    Mono<Franchise> findById(String id);

    Mono<Branch> register(String idFranchise, Branch branch);

    Mono<Franchise> registerProduct(String idFranchise, String idBranch, Product product);

    Mono<Franchise> deleteProduct(String idFranchise, String idBranch, String idProduct);

    Mono<Franchise> modifyProductStock(String idFranchise, String idBranch, String idProduct, int newStock);

    Mono<Product> getProductForMaximumStock(String idFranchise, String idBranch);

    Mono<Franchise> updateFranchiseName(String idFranchise, String newName);

    Mono<Franchise> updateBranchName(String idFranchise, String idBranch, String newName);

    Mono<Franchise> updateProductName(String idFranchise, String idBranch, String idProduct,
                                      String newName);

}
