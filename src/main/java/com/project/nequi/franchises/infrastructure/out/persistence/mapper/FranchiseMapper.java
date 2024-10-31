package com.project.nequi.franchises.infrastructure.out.persistence.mapper;

import com.project.nequi.franchises.domain.model.Branch;
import com.project.nequi.franchises.domain.model.Franchise;
import com.project.nequi.franchises.domain.model.Product;
import com.project.nequi.franchises.infrastructure.out.persistence.entity.BranchEntity;
import com.project.nequi.franchises.infrastructure.out.persistence.entity.FranchiseEntity;
import com.project.nequi.franchises.infrastructure.out.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FranchiseMapper {


    public FranchiseEntity toFranchiseEntity(Franchise franchise) {
        return FranchiseEntity.builder()
                .id(franchise.getId())
                .nameFranchise(franchise.getName())
                .branchEntities(toBranchEntityTo(franchise.getBranches()))
                .build();
    }

    public List<BranchEntity> toBranchEntityTo(List<Branch> branchList) {
        return Optional.ofNullable(branchList)
                .orElse(Collections.emptyList())
                .stream()
                .map(branchEntity -> BranchEntity.builder()
                        .id(branchEntity.getId())
                        .nameBranch(branchEntity.getName())
                        .products(toProductEntity(branchEntity.getProducts()))
                        .build())
                .toList();
    }

    public BranchEntity toBranchEntityToModel(Branch branch) {
        return BranchEntity.builder()
                .id(branch.getId())
                .nameBranch(branch.getName())
                .products(toProductEntity(branch.getProducts()))
                .build();
    }

    public List<ProductEntity> toProductEntity(List<Product> productList) {
        return Optional.ofNullable(productList)
                .orElse(Collections.emptyList())
                .stream().map(product -> ProductEntity.builder()
                        .id(product.getId())
                        .nameProduct(product.getName())
                        .stock(product.getStock())
                        .build())
                .toList();
    }

    public Franchise toFranchise(FranchiseEntity franchise) {
        return Franchise.builder()
                .id(franchise.getId())
                .name(franchise.getNameFranchise())
                .branches(toBranch(franchise.getBranchEntities()))
                .build();
    }
    public List<Branch> toBranch(List<BranchEntity> branchList) {
        return Optional.ofNullable(branchList)
                .orElse(Collections.emptyList())
                .stream()
                .map(branchEntity -> Branch.builder()
                        .id(branchEntity.getId())
                        .name(branchEntity.getNameBranch())
                        .products(toProduct(branchEntity.getProducts()))
                        .build())
                .toList();
    }

    public List<Product> toProduct(List<ProductEntity> productList) {
        return Optional.ofNullable(productList)
                .orElse(Collections.emptyList())
                .stream().map(productEntity -> Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getNameProduct())
                .stock(productEntity.getStock())
                .build()).toList();
    }

    public ProductEntity toProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .nameProduct(product.getName())
                .stock(product.getStock())
                .build();
    }
}
