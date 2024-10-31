package com.project.nequi.franchises.infrastructure.in.web.controller;

import com.project.nequi.franchises.aplication.port.in.FranchisePort;
import com.project.nequi.franchises.common.WebAdapter;
import com.project.nequi.franchises.domain.model.Branch;
import com.project.nequi.franchises.domain.model.Franchise;
import com.project.nequi.franchises.infrastructure.dto.request.FranchiseRequestDTO;
import com.project.nequi.franchises.infrastructure.dto.response.FranchiseResponseDTO;
import com.project.nequi.franchises.infrastructure.dto.response.ResponseDTO;
import com.project.nequi.franchises.infrastructure.out.persistence.ResponseEntityBuilder;
import com.project.nequi.franchises.infrastructure.out.persistence.mapper.FranchiseTransformer;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
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
        return franchisePort.findAll();
    }

    @PostMapping()
    @SneakyThrows
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>>
        saveFranchise(@RequestBody FranchiseRequestDTO franchiseRequestDTO) {
        return franchisePort.save(FranchiseTransformer.toFranchise(franchiseRequestDTO))
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildSaveResponse);
    }

    @GetMapping("/{id}")
    @SneakyThrows
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>>
    searchByIdFranchises(@PathVariable("id") String id) {
        return franchisePort.findById(id)
                .map(FranchiseTransformer::toFranchiseToDTO)
                .map(ResponseEntityBuilder::buildFindByIdResponse);

    }

    @PostMapping("/{idFranchise}/{branch}")
    public Mono<ResponseEntity<ResponseDTO<FranchiseResponseDTO>>> saveBranchInFranchise(
            @PathVariable String idFranchise,
            @RequestBody Branch branch) {
        return franchisePort.register(idFranchise,branch)
                .flatMap(registeredBranch -> franchisePort.findById(idFranchise))
                .map(franchise -> {
                    FranchiseResponseDTO dto = FranchiseTransformer.toFranchiseToDTO(franchise);
                    ResponseDTO<FranchiseResponseDTO> responseDTO = ResponseDTO.<FranchiseResponseDTO>builder()
                            .message("Branch registered in franchise successfully.")
                            .status(HttpStatus.CREATED.value())
                            .data(dto)
                            .build();
                    return ResponseEntity.ok(responseDTO);
                });
    }
}
