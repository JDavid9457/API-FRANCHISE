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


    @SneakyThrows
    @GetMapping("/list")
    public Flux<Franchise> getFranchises() {
        return franchisePort.findAll()
                .doOnNext(franchise -> System.out.println("Franchise: " + franchise))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    @SneakyThrows
    @PostMapping()
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> saveFranchise(
            @RequestBody FranchiseRequestDTO franchiseRequestDTO) {
        return franchisePort.save(FranchiseTransformer.toFranchise(franchiseRequestDTO))
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildSaveResponse);
    }


    @SneakyThrows
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> searchByIdFranchises(
            @PathVariable("id") String id) {
        return franchisePort.findById(id)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildFindByIdResponse);

    }

    @SneakyThrows
    @PostMapping("/{idFranchise}/{branch}")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> saveBranchInFranchise(
            @PathVariable String idFranchise,
            @RequestBody Branch branch) {
        return franchisePort.register(idFranchise, branch)
                .flatMap(registeredBranch -> franchisePort.findById(idFranchise))
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildRegisterResponse);
    }

    @SneakyThrows
    @PostMapping("/{idFranchise}/branch/{idBranch}/product")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> saveProductForBranch(
            @PathVariable String idFranchise,
            @PathVariable String idBranch,
            @RequestBody ProductRequestDTO productRequestDTO) {
        return franchisePort.registerProduct(idFranchise, idBranch, FranchiseTransformer.toProduct(productRequestDTO))
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildRegisterProductForBranchResponse);
    }


    @SneakyThrows
    @DeleteMapping("/{idFranchise}/branch/{idBranch}/product/{idProduct}")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> deleteProductFromBranch(
            @PathVariable String idFranchise,
            @PathVariable String idBranch,
            @PathVariable String idProduct) {
        return franchisePort.deleteProduct(idFranchise, idBranch, idProduct)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildDeletedResponse);

    }


    @SneakyThrows
    @PatchMapping("/{idFranchise}/branchs/{idBranch}/products/{idProduct}/stock")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> modifyProductByStock(
            @PathVariable String idFranchise,
            @PathVariable String idBranch,
            @PathVariable String idProduct,
            @RequestBody JsonNode body) {
        Integer newStock = body.get("stock").asInt();
        return franchisePort.modifyProductStock(idFranchise, idBranch, idProduct, newStock)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildModifyStock);
    }

    @SneakyThrows
    @GetMapping("/{franchiseId}/branches/{branchId}/products/max-stock")
    public Mono<ResponseEntity<ResponseDTO<ProductResponseDTO>>> getProductWithMaxStock(
            @PathVariable String franchiseId,
            @PathVariable String branchId) {
        return franchisePort.getProductForMaximumStock(franchiseId, branchId)
                .map(FranchiseTransformer::productResponseDTO)
                .map(ResponseEntityBuilder::buildProductWithMaxStock);
    }


    @SneakyThrows
    @PutMapping("/{idFranchise}/name")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> updateFranchiseByName(
            @PathVariable String idFranchise,
            @RequestBody JsonNode body) {
        String newName = body.get("name").asText().trim(); ;
        return franchisePort.updateFranchiseName(idFranchise, newName)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildUpdateFranchiseByNameResponse);
    }

    @SneakyThrows
    @PutMapping("/{idFranchise}/branchs/{idBranch}/name")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> updateBranchByName(
            @PathVariable String idFranchise,
            @PathVariable String idBranch,
            @RequestBody JsonNode body) {
        String newName = body.get("name").asText().trim(); ;
        return franchisePort.updateBranchName(idFranchise, idBranch, newName)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildUpdateBrachByNameResponse);
    }

    @SneakyThrows
    @PutMapping("/{idFranchise}/branchs/{idBranch}/products/{idProduct}/name")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> updateProductByName(
            @PathVariable String idFranchise,
            @PathVariable String idBranch,
            @PathVariable String idProduct,
            @RequestBody JsonNode body) {
        String newName = body.get("name").asText().trim(); ;
        return franchisePort.updateProductName(idFranchise, idBranch, idProduct, newName)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildUpdateProductByNameResponse);
    }
}


