package com.project.nequi.franchises.infrastructure.util.auxiliary;

import com.project.nequi.franchises.infrastructure.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.project.nequi.franchises.infrastructure.util.constants.Messages.*;

public class ResponseEntityBuilder {

    public static <T> ResponseEntity<ResponseDTO<T>> buildSaveResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message(FRANCHISE_CREATION_SUCCESS)
                        .status(HttpStatus.CREATED.value())
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ResponseDTO<T>> buildRegisterResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message(BREACH_REGISTER_IN_FRANCHISE)
                        .status(HttpStatus.CREATED.value())
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ResponseDTO<T>> buildRegisterProductForBranchResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message(BREACH_REGISTER_IN_FRANCHISE)
                        .status(HttpStatus.CREATED.value())
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ResponseDTO<T>> buildFindByIdResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message(ID_FOUND)
                        .status(HttpStatus.OK.value())
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ResponseDTO<T>> buildModifyStock(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message("ok")
                        .status(HttpStatus.OK.value())
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ResponseDTO<T>> buildProductWithMaxStock(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message("Product with max stock found successfully.")
                        .status(HttpStatus.OK.value())
                        .data(data)
                        .build());

}

    public static <T> ResponseEntity<ResponseDTO<T>> buildUpdateFranchiseByNameResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message("Product with max stock found successfully.")
                        .status(HttpStatus.OK.value())
                        .data(data)
                        .build());

    }

    public static <T> ResponseEntity<ResponseDTO<T>> buildUpdateBrachByNameResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message("Product with max stock found successfully.")
                        .status(HttpStatus.OK.value())
                        .data(data)
                        .build());

    }

    public static <T> ResponseEntity<ResponseDTO<T>> buildUpdateProductByNameResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message("Product with max stock found successfully.")
                        .status(HttpStatus.OK.value())
                        .data(data)
                        .build());

    }

        public static <T> ResponseEntity<ResponseDTO<T>> buildDeletedResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message(FAILED_TO_REMOVE_PRODUCT_FROM_BRANCH)
                        .status(HttpStatus.OK.value())
                        .build());
    }


}
