package assignment.contract.request;

public class UpsertTransactionRequest {

    private Double amount;
    private String type;
    private Long parentId;

    public UpsertTransactionRequest(Double amount, String type, Long parentId) {
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public Long getParentId() {
        return parentId;
    }
}
