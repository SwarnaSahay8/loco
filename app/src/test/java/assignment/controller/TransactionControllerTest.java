package assignment.controller;

import assignment.contract.response.GenericContractResponse;
import assignment.model.GenericResponse;
import assignment.model.Transaction;
import assignment.repository.TransactionRepository;
import assignment.validator.AddTransactionRequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

import static assignment.constants.TransactionConstants.BAD_REQUEST_HTTP_CODE;
import static assignment.constants.TransactionConstants.FAILED_STATUS;
import static assignment.constants.TransactionConstants.SUCCESS_HTTP_CODE;
import static assignment.constants.TransactionConstants.SUCCESS_STATUS;
import static assignment.constants.TransactionConstants.TRANSACTION_ID_PARAM;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @Mock
    TransactionRepository mockTransactionRepository;

    @Mock
    private Request mockRequest;

    @Mock
    private Response mockResponse;

    private TransactionController transactionController;

    @Before
    public void setUp() {
        AddTransactionRequestValidator validator = new AddTransactionRequestValidator();
        transactionController = new TransactionController(mockTransactionRepository, validator);
    }

    @Test
    public void shouldReturnSuccessResponseWhenSuccessfullyInsertTransactionToDb() {
        long transactionId = 6456;
        double amount = 23.4;
        String type = "car";
        long parentId = 12;

        when(mockRequest.params(TRANSACTION_ID_PARAM)).thenReturn(Long.toString(transactionId));
        Transaction transaction = new Transaction(transactionId, amount, type, parentId);
        when(mockRequest.body()).thenReturn("{\n" +
                "  \"amount\": 23.4,\n" +
                "  \"type\": \"car\",\n" +
                "  \"parent_id\": 12\n" +
                "}");
        when(mockTransactionRepository.insert(transaction)).thenReturn(new GenericResponse(true));

        GenericContractResponse actualResponse = transactionController.addTransaction(mockRequest, mockResponse);
        GenericContractResponse expectedResponse = new GenericContractResponse(SUCCESS_STATUS);
        assertEquals(expectedResponse, actualResponse);
        verify(mockResponse).status(SUCCESS_HTTP_CODE);
    }

    @Test
    public void shouldReturnFailureResponseAmountIsNotGivenInRequestBody() {
        long transactionId = 6456;

        when(mockRequest.params(TRANSACTION_ID_PARAM)).thenReturn(Long.toString(transactionId));
        when(mockRequest.body()).thenReturn("{\n" +
                "  \"type\": \"car\",\n" +
                "  \"parent_id\": 12\n" +
                "}");

        GenericContractResponse actualResponse = transactionController.addTransaction(mockRequest, mockResponse);
        GenericContractResponse expectedResponse = new GenericContractResponse(FAILED_STATUS);
        assertEquals(expectedResponse, actualResponse);
        verify(mockResponse).status(BAD_REQUEST_HTTP_CODE);
    }

    @Test
    public void shouldReturnFailureResponseTypeIsNotGivenInRequestBody() {
        long transactionId = 6456;

        when(mockRequest.params(TRANSACTION_ID_PARAM)).thenReturn(Long.toString(transactionId));
        when(mockRequest.body()).thenReturn("{\n" +
                "  \"amount\": 23.4,\n" +
                "  \"parent_id\": 12\n" +
                "}");

        GenericContractResponse actualResponse = transactionController.addTransaction(mockRequest, mockResponse);
        GenericContractResponse expectedResponse = new GenericContractResponse(FAILED_STATUS);
        assertEquals(expectedResponse, actualResponse);
        verify(mockResponse).status(BAD_REQUEST_HTTP_CODE);
    }

    @Test
    public void shouldReturnFailureResponseTypeIsEmptyInRequestBody() {
        long transactionId = 6456;

        when(mockRequest.params(TRANSACTION_ID_PARAM)).thenReturn(Long.toString(transactionId));
        when(mockRequest.body()).thenReturn("{\n" +
                "  \"type\": \"\",\n" +
                "  \"amount\": 23.4,\n" +
                "  \"parent_id\": 12\n" +
                "}");

        GenericContractResponse actualResponse = transactionController.addTransaction(mockRequest, mockResponse);
        GenericContractResponse expectedResponse = new GenericContractResponse(FAILED_STATUS);
        assertEquals(expectedResponse, actualResponse);
        verify(mockResponse).status(BAD_REQUEST_HTTP_CODE);
    }
}
