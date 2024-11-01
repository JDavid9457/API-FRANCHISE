package com.project.nequi.franchises.aplication.service;

import com.project.nequi.franchises.aplication.port.out.franchise.*;
import com.project.nequi.franchises.domain.model.Branch;
import com.project.nequi.franchises.domain.model.Franchise;
import com.project.nequi.franchises.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FranchiseServiceTest {

    @Mock
    private SaveFranchisePort saveFranchisePort;
    @Mock
    private ListFranchisePort listFranchisePort;
    @Mock
    private FindFranchiseByIdPort findFranchiseByIdPort;
    @Mock
    private RegisterBranchForFranchisePort registerBranchForFranchisePort;
    @Mock
    private RegisterProductForBranchPort registerProductForBranchPort;
    @Mock
    private DeleteProductForBranchPort deleteProductForBranchPort;
    @Mock
    private ModifyProductStockPort modifyProductStockPort;
    @Mock
    private GetProductForMaximumStockPort getProductForMaximumStockPort;
    @Mock
    private UpdateFranchiseNamePort updateFranchiseNamePort;
    @Mock
    private UpdateBranchNamePort updateBranchNamePort;
    @Mock
    private UpdateProductNamePort updateProductNamePort;

    @InjectMocks
    private FranchiseService franchiseService;

    private Franchise franchise;
    private Branch branch;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        franchise = Franchise.builder()
                .id("123")
                .name("Test Franchise")
                .build();

        branch = Branch.builder()
                .id("456")
                .name("Test Branch")
                .build();

        product = Product.builder()
                .id("789")
                .name("Test Product")
                .stock(50)
                .build();
    }

    @Test
    void saveFranchiseTest() {
        when(saveFranchisePort.saveFranchise(franchise)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.save(franchise))
                .expectNext(franchise)
                .verifyComplete();

        verify(saveFranchisePort, times(1)).saveFranchise(franchise);
    }

    @Test
    void findAllFranchisesTest() {
        when(listFranchisePort.listFranchise()).thenReturn(Flux.just(franchise));

        StepVerifier.create(franchiseService.findAll())
                .expectNext(franchise)
                .verifyComplete();

        verify(listFranchisePort, times(1)).listFranchise();
    }

    @Test
    void findByIdTest() {
        String franchiseId = "123";
        when(findFranchiseByIdPort.findByIdFranchise(franchiseId)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.findById(franchiseId))
                .expectNext(franchise)
                .verifyComplete();

        verify(findFranchiseByIdPort, times(1)).findByIdFranchise(franchiseId);
    }

    @Test
    void registerBranchTest() {
        String franchiseId = "123";
        when(registerBranchForFranchisePort.RegisterBranchForFranchise(franchiseId, branch)).thenReturn(Mono.just(branch));

        StepVerifier.create(franchiseService.register(franchiseId, branch))
                .expectNext(branch)
                .verifyComplete();

        verify(registerBranchForFranchisePort, times(1)).RegisterBranchForFranchise(franchiseId, branch);
    }

    @Test
    void registerProductTest() {
        String franchiseId = "123";
        String branchId = "456";
        when(registerProductForBranchPort.registerProductForBranch(franchiseId, branchId, product)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.registerProduct(franchiseId, branchId, product))
                .expectNext(franchise)
                .verifyComplete();

        verify(registerProductForBranchPort, times(1)).registerProductForBranch(franchiseId, branchId, product);
    }

    @Test
    void deleteProductTest() {
        String franchiseId = "123";
        String branchId = "456";
        String productId = "789";
        when(deleteProductForBranchPort.deleteProductForBranch(franchiseId, branchId, productId)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.deleteProduct(franchiseId, branchId, productId))
                .expectNext(franchise)
                .verifyComplete();

        verify(deleteProductForBranchPort, times(1)).deleteProductForBranch(franchiseId, branchId, productId);
    }

    @Test
    void getProductForMaximumStockTest() {
        String franchiseId = "123";
        String branchId = "456";
        when(getProductForMaximumStockPort.getProductForMaximumStock(franchiseId, branchId)).thenReturn(Mono.just(product));

        StepVerifier.create(franchiseService.getProductForMaximumStock(franchiseId, branchId))
                .expectNext(product)
                .verifyComplete();

        verify(getProductForMaximumStockPort, times(1)).getProductForMaximumStock(franchiseId, branchId);
    }


    @Test
    void updateFranchiseNameTest() {
        Franchise modifyFranchise = Franchise
                .builder()
                .id("6724d514f28ef41d4619b339")
                .name("Refactor")
                .build();

        String franchiseId = "6724d514f28ef41d4619b339";
        String newName = "Umbrella";

        when(updateFranchiseNamePort.updateFranchiseName(franchiseId, newName))
                .thenReturn(Mono.just(modifyFranchise));

        Mono<Franchise> result = franchiseService.updateFranchiseName(franchiseId, newName);

        Franchise actualFranchise = result.block();
        assertNotNull(actualFranchise, "franchise");
        assertEquals(modifyFranchise.getId(), actualFranchise.getId(), "6724d514f28ef41d4619b339");
        assertEquals(modifyFranchise.getName(), actualFranchise.getName(), "Umbrella");
        verify(updateFranchiseNamePort, times(1)).updateFranchiseName(franchiseId, newName);
    }

    @Test
    void updateBranchNameTest() {
        Branch modifyBranch = Branch
                .builder()
                .id("6724d7b5f28ef41d4619b33c")
                .name("Pollos hermanos")
                .build();

        Franchise modifyFranchise = Franchise
                .builder()
                .id("6724d514f28ef41d4619b339")
                .branches(Collections.singletonList(modifyBranch))
                .build();

        String franchiseId = "6724d514f28ef41d4619b339";
        String branchId = "6724d7b5f28ef41d4619b33c";
        String newName = "KFF";

        when(updateBranchNamePort.updateBranchName(franchiseId, branchId, newName))
                .thenReturn(Mono.just(modifyFranchise));

        Mono<Franchise> result = franchiseService.updateBranchName(franchiseId, branchId, newName);

        Franchise actualFranchise = result.block();
        assertNotNull(actualFranchise, "franchise");
        assertEquals(modifyFranchise.getId(), actualFranchise.getId(), "6724d514f28ef41d4619b339");
        assertEquals(modifyFranchise.getName(), actualFranchise.getName(), "Pollos hermanos");
        verify(updateBranchNamePort, times(1)).updateBranchName(franchiseId, branchId, newName);
    }

    @Test
    void updateProductNameTest() {

        Product modifyProduct = Product.builder()
                .id("6724e5d321b8733a654909d7")
                .name("Gaseosas")
                .stock(20)
                .build();

        Branch modifyBranch = Branch
                .builder()
                .id("6724d7b5f28ef41d4619b33c")
                .name("Pollos hermanos")
                .products(Collections.singletonList(modifyProduct))
                .build();

        Franchise modifyFranchise = Franchise
                .builder()
                .id("6724d514f28ef41d4619b339")
                .branches(Collections.singletonList(modifyBranch))
                .build();

        String franchiseId = "6724d514f28ef41d4619b339";
        String branchId = "6724d7b5f28ef41d4619b33c";
        String productId = "6724e5d321b8733a654909d7";
        String newName = "Helados granja del lobo";

        when(updateProductNamePort.updateProductName(franchiseId, branchId, productId, newName))
                .thenReturn(Mono.just(modifyFranchise));

        Mono<Franchise> result =franchiseService.updateProductName(franchiseId, branchId, productId, newName);

        Franchise actualFranchise = result.block();
        assertNotNull(actualFranchise, "franchise");
        assertEquals(modifyFranchise.getId(), actualFranchise.getId(), "6724d514f28ef41d4619b339");
        assertEquals(modifyFranchise.getName(), actualFranchise.getName(), "Pollos hermanos");
        verify(updateProductNamePort, times(1)).updateProductName(franchiseId, branchId, productId, newName);
    }


}