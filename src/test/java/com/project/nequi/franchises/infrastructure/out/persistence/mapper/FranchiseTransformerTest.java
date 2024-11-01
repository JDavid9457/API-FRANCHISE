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
import org.junit.jupiter.api.Test;

import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;

class FranchiseTransformerTest {


    @Test
    public void testToFranchise() {
        FranchiseRequestDTO requestDTO = FranchiseRequestDTO.builder()
                .id("6723d708da20943abfd4bf9e")
                .nameFranchise("Franchise A")
                .branches(Collections.singletonList
                        (BranchDTO.builder().id("6723d708da20943abfd4bf9e").name("Branch A").build()))
                .build();

        Franchise franchise = FranchiseTransformer.toFranchise(requestDTO);

        assertEquals("6723d708da20943abfd4bf9e", franchise.getId());
        assertEquals("Franchise A", franchise.getName());
        assertEquals(1, franchise.getBranches().size());
        assertEquals("", franchise.getBranches().get(0).getId());
        assertEquals("Branch A", franchise.getBranches().get(0).getName());
    }

//    @Test
//    public void testToBranches() {
//        List<BranchDTO> branchDTOList = List.of(
//                BranchDTO.builder().id(1L).name("Branch A").products(Collections.emptyList()).build(),
//                BranchDTO.builder().id(2L).name("Branch B").products(Collections.emptyList()).build()
//        );
//
//        List<Branch> branches = FranchiseTransformer.toBranches(branchDTOList);
//
//        assertEquals(2, branches.size());
//        assertEquals(1L, branches.get(0).getId());
//        assertEquals("Branch A", branches.get(0).getName());
//        assertEquals(2L, branches.get(1).getId());
//        assertEquals("Branch B", branches.get(1).getName());
//    }
//
//    @Test
//    public void testToProducts() {
//        List<ProductDTO> productDTOList = List.of(
//                ProductDTO.builder().id(1L).name("Product A").stock(100).build(),
//                ProductDTO.builder().id(2L).name("Product B").stock(200).build()
//        );
//
//        List<Product> products = FranchiseTransformer.toProducts(productDTOList);
//
//        assertEquals(2, products.size());
//        assertEquals(1L, products.get(0).getId());
//        assertEquals("Product A", products.get(0).getName());
//        assertEquals(100, products.get(0).getStock());
//        assertEquals(2L, products.get(1).getId());
//        assertEquals("Product B", products.get(1).getName());
//        assertEquals(200, products.get(1).getStock());
//    }
//
//    @Test
//    public void testToFranchiseToDTO() {
//        Franchise franchise = Franchise.builder()
//                .id(1L)
//                .name("Franchise A")
//                .branches(Collections.emptyList())
//                .build();
//
//        FranchiseResponseDTO responseDTO = FranchiseTransformer.toFranchiseToDTO(franchise);
//
//        assertEquals(1L, responseDTO.getId());
//        assertEquals("Franchise A", responseDTO.getNameFranchise());
//        assertEquals(0, responseDTO.getBranchDTOList().size());
//    }
//
//    @Test
//    public void testProductResponseDTO() {
//        Product product = Product.builder()
//                .id(1L)
//                .name("Product A")
//                .stock(100)
//                .build();
//
//        ProductResponseDTO responseDTO = FranchiseTransformer.productResponseDTO(product);
//
//        assertEquals(1L, responseDTO.getId());
//        assertEquals("Product A", responseDTO.getName());
//        assertEquals(100, responseDTO.getStock());
//    }
//
//    @Test
//    public void testToBrancheDTO() {
//        List<Branch> branches = List.of(
//                Branch.builder().id(1L).name("Branch A").products(Collections.emptyList()).build(),
//                Branch.builder().id(2L).name("Branch B").products(Collections.emptyList()).build()
//        );
//
//        List<BranchDTO> branchDTOs = FranchiseTransformer.toBrancheDTO(branches);
//
//        assertEquals(2, branchDTOs.size());
//        assertEquals(1L, branchDTOs.get(0).getId());
//        assertEquals("Branch A", branchDTOs.get(0).getName());
//        assertEquals(2L, branchDTOs.get(1).getId());
//        assertEquals("Branch B", branchDTOs.get(1).getName());
//    }
//
//    @Test
//    public void testToProductDTO() {
//        List<Product> products = List.of(
//                Product.builder().id(1L).name("Product A").stock(100).build(),
//                Product.builder().id(2L).name("Product B").stock(200).build()
//        );
//
//        List<ProductDTO> productDTOs = FranchiseTransformer.toProductDTO(products);
//
//        assertEquals(2, productDTOs.size());
//        assertEquals(1L, productDTOs.get(0).getId());
//        assertEquals("Product A", productDTOs.get(0).getName());
//        assertEquals(100, productDTOs.get(0).getStock());
//        assertEquals(2L, productDTOs.get(1).getId());
//        assertEquals("Product B", productDTOs.get(1).getName());
//        assertEquals(200, productDTOs.get(1).getStock());
//    }
//
//    @Test
//    public void testToProduct() {
//        ProductRequestDTO requestDTO = ProductRequestDTO.builder()
//                .id(1L)
//                .name("Product A")
//                .stock(100)
//                .build();
//
//        Product product = FranchiseTransformer.toProduct(requestDTO);
//
//        assertEquals(1L, product.getId());
//        assertEquals("Product A", product.getName());
//        assertEquals(100, product.getStock());
//    }
}

