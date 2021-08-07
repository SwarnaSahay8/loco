package assignment.contract.request;

public class AddTransactionRequest {

    private double amount;
    private String type;
    private long parentId;

    public AddTransactionRequest(double amount, String type, long parentId) {
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public long getParentId() {
        return parentId;
    }
}
