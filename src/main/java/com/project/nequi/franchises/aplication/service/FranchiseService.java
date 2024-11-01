package com.project.nequi.franchises.aplication.service;

import com.project.nequi.franchises.aplication.port.in.FranchisePort;
import com.project.nequi.franchises.aplication.port.out.franchise.*;
import com.project.nequi.franchises.common.UseCase;
import com.project.nequi.franchises.domain.model.Branch;
import com.project.nequi.franchises.domain.model.Franchise;
import com.project.nequi.franchises.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@UseCase
public class FranchiseService implements FranchisePort {

    private final SaveFranchisePort saveFranchisePort;

    private final ListFranchisePort listFranchisePort;

    private final FindFranchiseByIdPort findFranchiseByIdPort;

    private final RegisterBranchForFranchisePort registerBranchForFranchisePort;

    private final RegisterProductForBranchPort registerProductForBranchPort;

    private final DeleteProductForBranchPort deleteProductForBranchPort;

    private final ModifyProductStockPort modifyProductStockPort;

    private final GetProductForMaximumStockPort getProductForMaximumStockP;

    private final UpdateFranchiseNamePort updateFranchiseNamePort;

    private final UpdateBranchNamePort updateBranchNamePort;

    private final UpdateProductNamePort updateProductNamePort;

    public FranchiseService(SaveFranchisePort saveFranchisePort,
                            ListFranchisePort listFranchisePort, FindFranchiseByIdPort findFranchiseByIdPort,

                            RegisterBranchForFranchisePort
                            registerBranchForFranchisePort,
                            RegisterProductForBranchPort registerProductForBranchPort, DeleteProductForBranchPort deleteProductForBranchPort,
                            ModifyProductStockPort modifyProductStockPort,
                            GetProductForMaximumStockPort getProductForMaximumStockP,
                            UpdateFranchiseNamePort updateFranchiseNamePort,
                            UpdateBranchNamePort updateBranchNamePort,
                            UpdateProductNamePort updateProductNamePort) {
        this.saveFranchisePort = saveFranchisePort;
        this.listFranchisePort = listFranchisePort;
        this.findFranchiseByIdPort = findFranchiseByIdPort;
        this.registerBranchForFranchisePort = registerBranchForFranchisePort;
        this.registerProductForBranchPort = registerProductForBranchPort;
        this.deleteProductForBranchPort = deleteProductForBranchPort;
        this.modifyProductStockPort = modifyProductStockPort;
        this.getProductForMaximumStockP = getProductForMaximumStockP;
        this.updateFranchiseNamePort = updateFranchiseNamePort;
        this.updateBranchNamePort = updateBranchNamePort;
        this.updateProductNamePort = updateProductNamePort;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return saveFranchisePort.saveFranchise(franchise);
    }

    @Override
    public Flux<Franchise> findAll() {
        return listFranchisePort.listFranchise();
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return findFranchiseByIdPort.findByIdFranchise(id);
    }

    @Override
    public Mono<Branch> register(String idFranchise, Branch branch) {
        return registerBranchForFranchisePort.RegisterBranchForFranchise(idFranchise, branch);
    }


    @Override
    public Mono<Franchise> registerProduct(String idFranchise, String idBranch, Product product) {
        return registerProductForBranchPort.registerProductForBranch(idFranchise, idBranch, product);
    }

    @Override
    public Mono<Franchise> deleteProduct(String idFranchise, String idBranch, String idProduct) {
        return deleteProductForBranchPort.deleteProductForBranch(idFranchise, idBranch, idProduct);
    }

    @Override
    public Mono<Franchise> modifyProductStock(String idFranchise, String idBranch, String idProduct, int newStock) {
        return modifyProductStockPort.modifyProductStock(idFranchise, idFranchise, idProduct, newStock);
    }

    @Override
    public Mono<Product> getProductForMaximumStock(String idFranchise, String idBranch) {
        return getProductForMaximumStockP.getProductForMaximumStock(idFranchise, idBranch);
    }

    @Override
    public Mono<Franchise> updateFranchiseName(String idFranchise, String newName) {
        return updateFranchiseNamePort.updateFranchiseName(idFranchise, newName);
    }

    @Override
    public Mono<Franchise> updateBranchName(String idFranchise, String idBranch, String newName) {
        return updateBranchNamePort.updateBranchName(idFranchise,idBranch ,newName);
    }

    @Override
    public Mono<Franchise> updateProductName(String idFranchise, String idBranch, String idProduct,
                                             String newName) {
        return updateProductNamePort.updateProductName(idFranchise, idBranch,idProduct,newName);
    }
}
