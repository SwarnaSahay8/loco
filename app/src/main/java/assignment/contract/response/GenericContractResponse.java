package assignment.contract.response;

import java.util.Objects;

public class GenericContractResponse {

    private final String status;

    public GenericContractResponse(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericContractResponse that = (GenericContractResponse) o;
        return status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }
}
