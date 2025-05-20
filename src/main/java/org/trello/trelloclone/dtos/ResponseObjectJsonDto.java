package org.trello.trelloclone.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObjectJsonDto {
    @JsonProperty("response")
    private Object response;

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;
}