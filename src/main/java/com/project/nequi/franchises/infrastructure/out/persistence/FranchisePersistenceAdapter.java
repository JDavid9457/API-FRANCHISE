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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
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
        franchise.getBranches().forEach(branch -> {
            generateIdentifier(branch);
        });

        FranchiseEntity newFranchise = franchiseMapper.toFranchiseEntity(franchise);
        return franchiseRepository.save(newFranchise)
                .map(franchiseMapper::toFranchise)
                .onErrorResume(e -> Mono.error(new RuntimeException(ERROR_IN_SAVING)));
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
                .switchIfEmpty(Mono.error(new ExportException("no found")))
                .flatMap(franchiseEntity -> {
                    franchiseEntity.getBranchEntities().add(branchEntity);
                    return franchiseRepository.save(franchiseEntity)
                            .thenReturn(branch);
                })
                .onErrorMap(error -> new ExportException("Failed to register branch."));
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
            .switchIfEmpty(Mono.error(new RuntimeException("Franchise not found")))
            .flatMap(franchise -> {
                BranchEntity branch = franchise.getBranchEntities().stream()
                        .filter(b -> b.getId().equals(idBranch))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Branch not found"));
                branch.getProducts().add(prepareProduct);
                return franchiseRepository.save(franchise)
                        .map(franchiseMapper::toFranchise);
            });
    }

    @Override
    public Mono<Franchise> deleteProductForBranch(String franchiseId, String branchId, String productId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franchise not found")))
                .flatMap(franchise -> {
                    BranchEntity branchEntity = franchise.getBranchEntities().stream().filter(b -> b.getId().equals(branchId)).findFirst()
                            .orElseThrow(() -> new RuntimeException("Branch not found"));

                    boolean removed = branchEntity.getProducts().removeIf(product -> product.getId().equals(productId));
                    if (!removed) {
                        return Mono.error(new RuntimeException("Product not found"));
                    }

                    return franchiseRepository.save(franchise).map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new RuntimeException("Product not found, Failed to remove product from branch", error));
    }

    @Override
    public Mono<Franchise> modifyProductStock(String franchiseId, String branchId, String productId,
                                              int newStock) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franchise not found")))
                .flatMap(franchise -> {
                    BranchEntity branch = franchise.getBranchEntities().stream().filter(b -> b.getId().equals(branchId)).findFirst()
                            .orElseThrow(() -> new RuntimeException("Branch not found"));

                    ProductEntity product = branch.getProducts().stream().filter(p -> p.getId().equals(productId)).findFirst()
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    product.setStock(newStock);
                    return franchiseRepository.save(franchise).map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new RuntimeException(
                        "Failed to update product stock", error));
    }

    @Override
    public Mono<Product> getProductForMaximumStock(String franchiseId, String branchId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franchise not found")))
                .flatMap(franchise -> {
                    BranchEntity branch = franchise.getBranchEntities().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Branch not found"));
                    List<ProductEntity> products = branch.getProducts();
                    return Mono.just(products.stream()
                            .max(Comparator.comparingInt(ProductEntity::getStock))
                            .map(productEntity -> Product.builder()
                                    .id(productEntity.getId())
                                    .name(productEntity.getNameProduct())
                                    .stock(productEntity.getStock())
                                    .build())
                            .orElseThrow(() -> new RuntimeException("No products found")));
                })
                .onErrorMap(error -> new RuntimeException("Failed to get product with max stock", error));
    }

    @Override
    public Mono<Franchise> updateFranchiseName(String idFranchise, String newName) {
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new RuntimeException("Franchise not found")))
                .flatMap(franchise -> {
                    franchise.setNameFranchise(newName);
                    return franchiseRepository.save(franchise).map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new RuntimeException(
                        "Failed to update franchise name", error));
    }

    @Override
    public Mono<Franchise> updateBranchName(String idFranchise, String idBranch, String newName) {
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new RuntimeException("Franchise not found")))
                .flatMap(franchise -> {
                    BranchEntity branch = franchise.getBranchEntities().stream().filter(b -> b.getId().equals(idBranch)).findFirst()
                            .orElseThrow(() -> new RuntimeException("Branch not found"));

                    branch.setNameBranch(newName);
                    return franchiseRepository.save(franchise).map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new RuntimeException(
                        "Failed to update branch name", error));
    }

    @Override
    public Mono<Franchise> updateProductName(String idFranchise, String idBranch, String idProduct, String newName) {
        return franchiseRepository.findById(idFranchise)
                .switchIfEmpty(Mono.error(new RuntimeException("Franchise not found")))
                .flatMap(franchise -> {
                    BranchEntity branch = franchise.getBranchEntities().stream().filter(b -> b.getId().equals(idBranch)).findFirst()
                            .orElseThrow(() -> new RuntimeException("Branch not found"));

                    ProductEntity product = branch.getProducts().stream().filter(p -> p.getId().equals(idProduct)).findFirst()
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    product.setNameProduct(newName);
                    return franchiseRepository.save(franchise).map(franchiseMapper::toFranchise);
                }).onErrorMap(error -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to update product name", error));
    }
}
