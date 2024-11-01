package com.project.nequi.franchises.infrastructure.in.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.nequi.franchises.aplication.port.in.FranchisePort;
import com.project.nequi.franchises.common.WebAdapter;
import com.project.nequi.franchises.domain.model.Branch;
import com.project.nequi.franchises.domain.model.Franchise;
import com.project.nequi.franchises.infrastructure.dto.request.FranchiseRequestDTO;
import com.project.nequi.franchises.infrastructure.dto.request.ProductRequestDTO;
import com.project.nequi.franchises.infrastructure.dto.response.FranchiseResponseDTO;
import com.project.nequi.franchises.infrastructure.dto.response.ProductResponseDTO;
import com.project.nequi.franchises.infrastructure.dto.response.ResponseDTO;
import com.project.nequi.franchises.infrastructure.util.auxiliary.ResponseEntityBuilder;
import com.project.nequi.franchises.infrastructure.out.persistence.mapper.FranchiseTransformer;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@WebAdapter
@RestController
@RequestMapping("/franchise")
public class FranchiseController {

    private final FranchisePort franchisePort;

    public FranchiseController(FranchisePort franchisePort) {
        this.franchisePort = franchisePort;
    }

    @GetMapping()
    @SneakyThrows
    public Flux<Franchise> getFranchises() {
        return franchisePort.findAll()
                .doOnNext(franchise -> System.out.println("Franchise: " + franchise))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }


    @PostMapping()
    @SneakyThrows
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> saveFranchise(
            @RequestBody FranchiseRequestDTO franchiseRequestDTO) {
        return franchisePort.save(FranchiseTransformer.toFranchise(franchiseRequestDTO))
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildSaveResponse);
    }

    @GetMapping("/{id}")
    @SneakyThrows
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> searchByIdFranchises(
            @PathVariable("id") String id) {
        return franchisePort.findById(id)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildFindByIdResponse);

    }

    @PostMapping("/{idFranchise}/{branch}")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> saveBranchInFranchise(
            @PathVariable String idFranchise,
            @RequestBody Branch branch) {
        return franchisePort.register(idFranchise, branch)
                .flatMap(registeredBranch -> franchisePort.findById(idFranchise))
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildRegisterResponse);
    }


    @PostMapping("/{idFranchise}/branch/{idBranch}/product")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> saveProductForBranch(
            @PathVariable String idFranchise,
            @PathVariable String idBranch,
            @RequestBody ProductRequestDTO productRequestDTO) {
        return franchisePort.registerProduct(idFranchise, idBranch, FranchiseTransformer.toProduct(productRequestDTO))
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildRegisterProductForBranchResponse);
    }

    @DeleteMapping("/{idFranchise}/branch/{idBranch}/product/{idProduct}")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> deleteProductFromBranch(
            @PathVariable String idFranchise,
            @PathVariable String idBranch,
            @PathVariable String idProduct) {
        return franchisePort.deleteProduct(idFranchise, idBranch, idProduct)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildDeletedResponse);

    }

    @PutMapping("/{franchiseId}/branches/{branchId}/products/{productId}/stock")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> modifyProductByStock(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @RequestBody JsonNode body) {
        Integer newStock = body.get("stock").asInt();
        return franchisePort.modifyProductStock(franchiseId, branchId, productId, newStock)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildModifyStock);
    }

    @GetMapping("/{franchiseId}/branches/{branchId}/products/max-stock")
    public Mono<ResponseEntity<ResponseDTO<ProductResponseDTO>>> getProductWithMaxStock(
            @PathVariable String franchiseId,
            @PathVariable String branchId) {
        return franchisePort.getProductForMaximumStock(franchiseId, branchId)
                .map(FranchiseTransformer::productResponseDTO)
                .map(ResponseEntityBuilder::buildProductWithMaxStock);
    }

    @PutMapping("/{id}/name")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> updateFranchiseByName(
            @PathVariable String id,
            @RequestBody JsonNode body) {
        String newName = body.get("name").asText();
        return franchisePort.updateFranchiseName(id, newName)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildUpdateFranchiseByNameResponse);
    }

    @PutMapping("/{franchiseId}/branches/{branchId}/name")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> updateBranchByName(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @RequestBody String newBranchName) {
        return franchisePort.updateBranchName(franchiseId, branchId, newBranchName)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildUpdateBrachByNameResponse);
    }

    @PutMapping("/{franchiseId}/branches/{branchId}/products/{productId}/name")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> updateProductByName(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @RequestBody String newProductName) {
        return franchisePort.updateProductName(franchiseId, branchId, productId, newProductName)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildUpdateProductByNameResponse);
    }
}


