package assignment.model;

import java.util.Objects;

public class GenericResponse {

    private final boolean success;
    private Error error;

    public GenericResponse(boolean success) {
        this.success = success;
    }

    public GenericResponse(Error error) {
        this.error = error;
        this.success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public Error getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericResponse that = (GenericResponse) o;
        return success == that.success && Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, error);
    }
}
