package org.resource.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorResponse {
    private String errorMessage;
    private int errorCode;
    private Map<String, String> details;
}
