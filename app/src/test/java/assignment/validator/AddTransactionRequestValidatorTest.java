package assignment.validator;

import assignment.contract.request.AddTransactionRequest;
import assignment.model.Error;
import assignment.model.GenericResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddTransactionRequestValidatorTest {

    private AddTransactionRequestValidator validator;

    @Before
    public void setUp() {
        validator = new AddTransactionRequestValidator();
    }

    @Test
    public void shouldReturnFailureResponseIfAmountIsNull() {
        AddTransactionRequest transactionRequest = new AddTransactionRequest(null, "type", 2L);
        GenericResponse actualResponse = validator.validate(transactionRequest);
        GenericResponse expectedResponse = new GenericResponse(new Error("amount", "amount should be present"));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldReturnFailureIfTypeIsNull() {
        AddTransactionRequest transactionRequest = new AddTransactionRequest(23.5, null, 2L);
        GenericResponse actualResponse = validator.validate(transactionRequest);
        GenericResponse expectedResponse = new GenericResponse(new Error("type", "type should be present"));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldReturnFailureIfTypeIsEmpty() {
        AddTransactionRequest transactionRequest = new AddTransactionRequest(23.5, "", 2L);
        GenericResponse actualResponse = validator.validate(transactionRequest);
        GenericResponse expectedResponse = new GenericResponse(new Error("type", "type should be present"));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldReturnSuccessResponseIfAllValuesArePresent() {
        AddTransactionRequest transactionRequest = new AddTransactionRequest(23.5, "abc", 2L);
        GenericResponse actualResponse = validator.validate(transactionRequest);
        GenericResponse expectedResponse = new GenericResponse(true);
        assertEquals(expectedResponse, actualResponse);
    }
}
