package assignment.model;

import java.util.Objects;

public class GenericResponse<T> {

    private final boolean success;
    private Error error;
    private T data;

    public GenericResponse(boolean success) {
        this.success = success;
    }

    public GenericResponse(Error error) {
        this.error = error;
        this.success = false;
    }

    public GenericResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public Error getError() {
        return error;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericResponse<?> that = (GenericResponse<?>) o;
        return success == that.success && Objects.equals(error, that.error) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, error, data);
    }
}
