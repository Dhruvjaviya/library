package com.example.library.util;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API response wrapper")
public class Response {

    @Schema(description = "Indicates if the request was successful", example = "true")
    private Boolean success;
    
    @Schema(description = "Response message", example = "Operation completed successfully")
    private String message;
    
    @Schema(description = "Response data payload")
    private Object data;

    public Response(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    @Schema(description = "Indicates if the request was successful")
    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Schema(description = "Response message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Schema(description = "Response data payload")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
