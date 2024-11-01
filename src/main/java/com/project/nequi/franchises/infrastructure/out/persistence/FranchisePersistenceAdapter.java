package com.project.nequi.franchises.infrastructure.out.persistence;

import com.project.nequi.franchises.aplication.port.out.franchise.*;
import com.project.nequi.franchises.common.PersistenceAdapter;
import com.project.nequi.franchises.domain.model.Branch;
import com.project.nequi.franchises.domain.model.Franchise;
import com.project.nequi.franchises.domain.model.Product;
import com.project.nequi.franchises.infrastructure.exceptions.FranchiseNotFoundException;
import com.project.nequi.franchises.infrastructure.out.persistence.entity.BranchEntity;
import com.project.nequi.franchises.infrastructure.out.persistence.entity.FranchiseEntity;
import com.project.nequi.franchises.infrastructure.out.persistence.entity.ProductEntity;
import com.project.nequi.franchises.infrastructure.out.persistence.mapper.FranchiseMapper;
import com.project.nequi.franchises.infrastructure.out.persistence.repositories.FranchiseRepository;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.rmi.server.ExportException;
import java.util.Comparator;
import java.util.List;

import static com.project.nequi.franchises.infrastructure.util.constants.Messages.*;

@PersistenceAdapter
public class FranchisePersistenceAdapter implements SaveFranchisePort, ListFranchisePort, FindFranchiseByIdPort,
        RegisterBranchForFranchisePort, DeleteProductForBranchPort, GetProductForMaximumStockPort, ModifyProductStockPort,
        RegisterProductForBranchPort, UpdateFranchiseNamePort, UpdateBranchNamePort, UpdateProductNamePort {

    private final FranchiseRepository franchiseRepository;

    private final FranchiseMapper franchiseMapper;


    public FranchisePersistenceAdapter(FranchiseRepository franchiseRepository, FranchiseMapper franchiseMapper) {
        this.franchiseRepository = franchiseRepository;
        this.franchiseMapper = franchiseMapper;
    }

    @Override
    public Flux<Franchise> listFranchise() {
        return franchiseRepository.findAll()
                .map(franchiseMapper::toFranchise)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(EMPTY_LIST_FRANCHISE)));
    }

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        franchise.getBranches().forEach(this::generateIdentifier);
        FranchiseEntity newFranchise = franchiseMapper.toFranchiseEntity(franchise);
        return franchiseRepository.save(newFranchise)
                .map(franchiseMapper::toFranchise)
                .onErrorResume(e -> Mono.error(new RuntimeException(FAILED_IN_SAVING_FRANCHISE)));
    }


    @Override
    public Mono<Franchise> findByIdFranchise(String id) {
        return franchiseRepository.findById(id)
                .map(franchiseMapper::toFranchise)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(FRANCHISE_NOT_FOUND + id)));
    }

    @Override
    public Mono<Branch> RegisterBranchForFranchise(String idFranchise, Branch branch) {
        generateIdentifier(branch);
        BranchEntity branchEntity = franchiseMapper.toBranchEntityToModel(branch);
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(FRANCHISE_NOT_FOUND + idFranchise)))
                .flatMap(franchiseEntity -> {
                    franchiseEntity.getBranchEntities().add(branchEntity);
                    return franchiseRepository.save(franchiseEntity)
                            .thenReturn(branch);
                })
                .onErrorMap(error -> new ExportException(FAILED_TO_REGISTER_BRANCH));
    }

    private void generateIdentifier(Branch branch) {
        if (branch.getId() == null || branch.getId().isEmpty()) {
            branch.setId(new ObjectId().toString());
        }
        branch.getProducts().forEach(product -> {
            if (product.getId() == null || product.getId().isEmpty()) {
                product.setId(new ObjectId().toString());
            }
        });
    }


    public Mono<Franchise> registerProductForBranch(String idFranchise, String idBranch, Product product) {
        if (product.getId() == null || product.getId().isEmpty()) {
            product.setId(new ObjectId().toString());
        }
        ProductEntity prepareProduct = franchiseMapper.toProductEntity(product);
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(FRANCHISE_NOT_FOUND + idFranchise)))
                .flatMap(franchise -> {
                    BranchEntity branch = franchise.getBranchEntities().stream()
                            .filter(b -> b.getId().equals(idBranch))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(BREACH_NOT_FOUND));
                    branch.getProducts().add(prepareProduct);
                    return franchiseRepository.save(franchise)
                            .map(franchiseMapper::toFranchise);
                });
    }

    @Override
    public Mono<Franchise> deleteProductForBranch(String idFranchise, String idBranch, String idProduct) {
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(FRANCHISE_NOT_FOUND +idFranchise)))
                .flatMap(franchise -> {
                    BranchEntity branchEntity = franchise.getBranchEntities().stream()
                            .filter(b -> b.getId().equals(idBranch)).findFirst()
                            .orElseThrow(() -> new RuntimeException(BREACH_NOT_FOUND));

                    boolean removed = branchEntity.getProducts()
                            .removeIf(product -> product.getId().equals(idProduct));
                    if (!removed) {
                        return Mono.error(new RuntimeException(PRODUCT_NOT_FOUND));
                    }

                    return franchiseRepository.save(franchise).map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new RuntimeException(FAILED_TO_REMOVE_PRODUCT_FROM_BRANCH, error));
    }

    @Override
    public Mono<Franchise> modifyProductStock(String idFranchise, String idBranch, String idProduct, int newStock) {
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(FRANCHISE_NOT_FOUND)))
                .flatMap(franchise -> {
                    BranchEntity branch = franchise.getBranchEntities()
                            .stream().filter(b -> b.getId().equals(idBranch)).findFirst()
                            .orElseThrow(() -> new RuntimeException(BREACH_NOT_FOUND));

                    ProductEntity product = branch.getProducts()
                            .stream().filter(p -> p.getId().equals(idProduct)).findFirst()
                            .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));

                    product.setStock(newStock);
                    return franchiseRepository.save(franchise)
                            .map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new RuntimeException(
                        FAILED_TO_UPDATE_PRODUCT_STOCK, error));
    }

    @Override
    public Mono<Product> getProductForMaximumStock(String franchiseId, String branchId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(FRANCHISE_NOT_FOUND)))
                .flatMap(franchise -> {
                    BranchEntity branch = franchise.getBranchEntities().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(BREACH_NOT_FOUND));
                    List<ProductEntity> products = branch.getProducts();
                    return Mono.just(products.stream()
                            .max(Comparator.comparingInt(ProductEntity::getStock))
                            .map(productEntity -> Product.builder()
                                    .id(productEntity.getId())
                                    .name(productEntity.getNameProduct())
                                    .stock(productEntity.getStock())
                                    .build())
                            .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND)));
                })
                .onErrorMap(error -> new RuntimeException(FAILED_TO_GET_PRODUCT_WITH_MAX_STOCK, error));
    }

    @Override
    public Mono<Franchise> updateFranchiseName(String idFranchise, String newName) {
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(FRANCHISE_NOT_FOUND)))
                .flatMap(franchise -> {
                    franchise.setNameFranchise(newName);
                    return franchiseRepository.save(franchise).map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new RuntimeException(
                        FAILED_TO_UPDATE_FRANCHISE_NAME, error));
    }

    @Override
    public Mono<Franchise> updateBranchName(String idFranchise, String idBranch, String newName) {
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new RuntimeException(FRANCHISE_NOT_FOUND)))
                .flatMap(franchise -> {
                    BranchEntity branch = franchise.getBranchEntities()

                            .stream().filter(b -> b.getId().equals(idBranch)).findFirst()
                            .orElseThrow(() -> new RuntimeException(BREACH_NOT_FOUND));

                    branch.setNameBranch(newName);
                    return franchiseRepository.save(franchise).map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new RuntimeException(
                        FAILED_TO_UPDATE_BREACH_NAME, error));
    }

    @Override
    public Mono<Franchise> updateProductName(String idFranchise, String idBranch, String idProduct, String newName) {
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(FRANCHISE_NOT_FOUND)))
                .flatMap(franchise -> {
                    BranchEntity branch = franchise.getBranchEntities()
                            .stream().filter(b -> b.getId().equals(idBranch)).findFirst()
                            .orElseThrow(() -> new RuntimeException(BREACH_NOT_FOUND));

                    ProductEntity product = branch.getProducts()
                            .stream().filter(p -> p.getId().equals(idProduct)).findFirst()
                            .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));

                    product.setNameProduct(newName);
                    return franchiseRepository.save(franchise).map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new RuntimeException(
                        FAILED_TO_UPDATE_PRODUCT_NAME, error));
    }
}
