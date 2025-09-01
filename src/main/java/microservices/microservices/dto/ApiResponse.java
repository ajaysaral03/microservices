package microservices.microservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // ignore null fields in JSON
public class ApiResponse<T> {

    private String message;
    private T data;

    // No-args constructor (needed for JSON deserialization)
    public ApiResponse() {
    }

    // Constructor with message and data
    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // Constructor with message only
    public ApiResponse(String message) {
        this.message = message;
        this.data = null;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
