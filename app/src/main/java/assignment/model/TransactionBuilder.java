package assignment.model;

public class TransactionBuilder {

    private Long transactionId;
    private Double amount;
    private String type;
    private Long parentId;

    public TransactionBuilder withTransactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public TransactionBuilder withAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public TransactionBuilder withParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public Transaction build() {
        return new Transaction(transactionId, amount, type, parentId);
    }
}
