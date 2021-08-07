package assignment.contract.response;

import java.util.Objects;

public class TransactionAmountResponse {

    private final Double sum;

    public TransactionAmountResponse(Double sum) {
        this.sum = sum;
    }

    public Double getSum() {
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionAmountResponse that = (TransactionAmountResponse) o;
        return Objects.equals(sum, that.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum);
    }
}
