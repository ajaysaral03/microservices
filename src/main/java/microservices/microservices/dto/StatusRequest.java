package microservices.microservices.dto;

public class StatusRequest {
    private Integer status; // 0 or 1
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
