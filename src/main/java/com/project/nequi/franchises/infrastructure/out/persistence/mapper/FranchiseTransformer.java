package com.project.nequi.franchises.infrastructure.out.persistence.mapper;

import com.project.nequi.franchises.domain.model.Branch;
import com.project.nequi.franchises.domain.model.Franchise;
import com.project.nequi.franchises.domain.model.Product;
import com.project.nequi.franchises.infrastructure.dto.BranchDTO;
import com.project.nequi.franchises.infrastructure.dto.ProductDTO;
import com.project.nequi.franchises.infrastructure.dto.request.FranchiseRequestDTO;
import com.project.nequi.franchises.infrastructure.dto.request.ProductRequestDTO;
import com.project.nequi.franchises.infrastructure.dto.response.FranchiseResponseDTO;
import com.project.nequi.franchises.infrastructure.dto.response.ProductResponseDTO;

import java.util.List;


public class FranchiseTransformer {
    public static Franchise toFranchise(FranchiseRequestDTO franchise) {
        return Franchise.builder()
                .id(franchise.getId())
                .name(franchise.getNameFranchise())
                .branches(toBranches(franchise.getBranches()))
                .build();
    }

    public static List<Branch> toBranches(List<BranchDTO> branchDTOList) {
        return branchDTOList.stream().map(branchDTO -> Branch.builder()
                .id(branchDTO.getId())
                .name(branchDTO.getName())
                .products(toProducts(branchDTO.getProducts()))
                .build()).toList();
    }

    public static List<Product> toProducts(List<ProductDTO> productDTOList) {
        return productDTOList.stream().map(productDTO -> Product.builder()
                        .id(productDTO.getId())
                        .name(productDTO.getName())
                        .stock(productDTO.getStock())
                        .build())
                .toList();
    }

    public static FranchiseResponseDTO toFranchiseToDTO(Franchise franchise) {
        return FranchiseResponseDTO.builder()
                .id(franchise.getId())
                .nameFranchise(franchise.getName())
                .branchDTOList(toBrancheDTO(franchise.getBranches()))
                .build();
    }


    public static ProductResponseDTO productResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }


    public static List<BranchDTO> toBrancheDTO(List<Branch> branchList) {
        return branchList.stream().map(branchDTO -> BranchDTO.builder()
                .id(branchDTO.getId())
                .name(branchDTO.getName())
                .products(toProductDTO(branchDTO.getProducts()))
                .build()).toList();
    }

    public static List<ProductDTO> toProductDTO(List<Product> productList) {
        return productList.stream().map(productDTO -> ProductDTO.builder()
                        .id(productDTO.getId())
                        .name(productDTO.getName())
                        .stock(productDTO.getStock())
                        .build())
                .toList();
    }

    public static Product toProduct(ProductRequestDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .stock(productDTO.getStock())
                .build();
    }

}
