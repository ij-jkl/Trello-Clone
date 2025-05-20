package org.trello.trelloclone.dtos;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseBuilder {

    public static ResponseObjectJsonDto buildSuccessResponse(Object data, String message) {
        return ResponseObjectJsonDto.builder()
                .response(data)
                .code(200)
                .message(message)
                .build();
    }

    public static ResponseObjectJsonDto buildCreatedResponse(Object data, String message) {
        return ResponseObjectJsonDto.builder()
                .response(data)
                .code(201)
                .message(message)
                .build();
    }

    public static ResponseObjectJsonDto buildErrorResponse(String message) {
        return ResponseObjectJsonDto.builder()
                .response(null)
                .code(500)
                .message("Error : " + message)
                .build();
    }

    public static ResponseObjectJsonDto buildNotFoundResponse(String message) {
        return ResponseObjectJsonDto.builder()
                .response(null)
                .code(404)
                .message(message)
                .build();
    }

    public static ResponseObjectJsonDto buildBadRequestResponse(String message) {
        return ResponseObjectJsonDto.builder()
                .response(null)
                .code(400)
                .message(message)
                .build();
    }
}