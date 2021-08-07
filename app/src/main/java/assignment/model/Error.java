package assignment.model;

import java.util.Objects;

public class Error {

    private String entity;
    private String message;

    public Error(String entity, String message) {
        this.entity = entity;
        this.message = message;
    }

    public String getEntity() {
        return entity;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return Objects.equals(entity, error.entity) && Objects.equals(message, error.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, message);
    }
}
