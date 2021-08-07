package assignment.validator;

import assignment.contract.request.AddTransactionRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddTransactionRequestValidatorTest {

    private AddTransactionRequestValidator validator;

    @Before
    public void setUp() {
        validator = new AddTransactionRequestValidator();
    }

    @Test
    public void shouldReturnFalseIfAmountIsNull() {
        AddTransactionRequest transactionRequest = new AddTransactionRequest(null, "type", 2L);
        boolean actualResponse = validator.validate(transactionRequest);
        assertFalse(actualResponse);
    }

    @Test
    public void shouldReturnFalseIfTypeIsNull() {
        AddTransactionRequest transactionRequest = new AddTransactionRequest(23.5,null , 2L);
        boolean actualResponse = validator.validate(transactionRequest);
        assertFalse(actualResponse);
    }

    @Test
    public void shouldReturnFalseIfTypeIsEmpty() {
        AddTransactionRequest transactionRequest = new AddTransactionRequest(23.5,"" , 2L);
        boolean actualResponse = validator.validate(transactionRequest);
        assertFalse(actualResponse);
    }

    @Test
    public void shouldReturnTrueIfAllValuesArePresent() {
        AddTransactionRequest transactionRequest = new AddTransactionRequest(23.5,"abc" , 2L);
        boolean actualResponse = validator.validate(transactionRequest);
        assertTrue(actualResponse);
    }
}
