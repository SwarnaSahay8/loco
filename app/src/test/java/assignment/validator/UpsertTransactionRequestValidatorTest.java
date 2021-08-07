package assignment.validator;

import assignment.contract.request.UpsertTransactionRequest;
import assignment.model.Error;
import assignment.model.GenericResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpsertTransactionRequestValidatorTest {

    private AddTransactionRequestValidator validator;

    @Before
    public void setUp() {
        validator = new AddTransactionRequestValidator();
    }

    @Test
    public void shouldReturnFailureResponseIfAmountIsNull() {
        UpsertTransactionRequest transactionRequest = new UpsertTransactionRequest(null, "type", 2L);
        GenericResponse actualResponse = validator.validate(transactionRequest);
        GenericResponse expectedResponse = new GenericResponse(new Error("amount", "amount should be present"));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldReturnFailureIfTypeIsNull() {
        UpsertTransactionRequest transactionRequest = new UpsertTransactionRequest(23.5, null, 2L);
        GenericResponse actualResponse = validator.validate(transactionRequest);
        GenericResponse expectedResponse = new GenericResponse(new Error("type", "type should be present"));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldReturnFailureIfTypeIsEmpty() {
        UpsertTransactionRequest transactionRequest = new UpsertTransactionRequest(23.5, "", 2L);
        GenericResponse actualResponse = validator.validate(transactionRequest);
        GenericResponse expectedResponse = new GenericResponse(new Error("type", "type should be present"));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldReturnSuccessResponseIfAllValuesArePresent() {
        UpsertTransactionRequest transactionRequest = new UpsertTransactionRequest(23.5, "abc", 2L);
        GenericResponse actualResponse = validator.validate(transactionRequest);
        GenericResponse expectedResponse = new GenericResponse(true);
        assertEquals(expectedResponse, actualResponse);
    }
}
