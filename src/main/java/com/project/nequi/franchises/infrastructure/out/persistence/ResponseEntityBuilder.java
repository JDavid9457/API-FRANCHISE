package com.project.nequi.franchises.infrastructure.out.persistence;

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
                        .message(BRACH_REGISTER_IN_FRANCHISE)
                        .status(HttpStatus.CREATED.value())
                        .data(data)
                        .build());
    }

    public static <T> ResponseEntity<ResponseDTO<T>> buildRegisterProductForBranchResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message(BRACH_REGISTER_IN_FRANCHISE)
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

    public static <T> ResponseEntity<ResponseDTO<T>> buildDeletedResponse(T data) {
        return ResponseEntity.ok(
                ResponseDTO.<T>builder()
                        .message(USER_DELETED_SUCCESSFULLY)
                        .status(HttpStatus.OK.value())
                        .build());
    }


}
