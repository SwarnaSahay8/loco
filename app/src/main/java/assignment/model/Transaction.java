package assignment.model;

import java.util.Objects;

public class Transaction {

    private Long transactionId;
    private Double amount;
    private String type;
    private Long parentId;

    public Transaction(Long transactionId, Double amount, String type, Long parentId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    public Long getTransactionId() {
        return transactionId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(amount, that.amount) && Objects.equals(type, that.type) && Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, amount, type, parentId);
    }
}
