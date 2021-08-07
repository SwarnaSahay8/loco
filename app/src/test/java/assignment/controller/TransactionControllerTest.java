package assignment.controller;

import assignment.contract.response.GenericContractResponse;
import assignment.model.Error;
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

import java.util.Collections;
import java.util.List;

import static assignment.constants.TransactionConstants.BAD_REQUEST_HTTP_CODE;
import static assignment.constants.TransactionConstants.FAILED_STATUS;
import static assignment.constants.TransactionConstants.SUCCESS_HTTP_CODE;
import static assignment.constants.TransactionConstants.SUCCESS_STATUS;
import static assignment.constants.TransactionConstants.TRANSACTION_ID_PARAM;
import static assignment.constants.TransactionConstants.TYPE_PARAM;
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
        when(mockTransactionRepository.upsert(transaction)).thenReturn(new GenericResponse(true));

        GenericContractResponse actualResponse = transactionController.upsertTransaction(mockRequest, mockResponse);
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

        GenericContractResponse actualResponse = transactionController.upsertTransaction(mockRequest, mockResponse);
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

        GenericContractResponse actualResponse = transactionController.upsertTransaction(mockRequest, mockResponse);
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

        GenericContractResponse actualResponse = transactionController.upsertTransaction(mockRequest, mockResponse);
        GenericContractResponse expectedResponse = new GenericContractResponse(FAILED_STATUS);
        assertEquals(expectedResponse, actualResponse);
        verify(mockResponse).status(BAD_REQUEST_HTTP_CODE);
    }

    @Test
    public void shouldReturnListOfTransactionIdsForGetTransactionIdRequest() {
        String type = "car";
        when(mockRequest.params(TYPE_PARAM)).thenReturn(type);
        List<Long> transactionIds = List.of(1L, 2L, 3L, 4L);
        GenericResponse<List<Long>> genericResponse = new GenericResponse<>(true, transactionIds);
        when(mockTransactionRepository.getByType(type)).thenReturn(genericResponse);
        List<Long> actualResponse = transactionController.getTransactionsByType(mockRequest, mockResponse);
        assertEquals(transactionIds, actualResponse);
    }

    @Test
    public void shouldReturnEmptyListForGetTransactionIdRequestWhenTransactionRepositoryReturnsError() {
        String type = "car";
        when(mockRequest.params(TYPE_PARAM)).thenReturn(type);
        GenericResponse<List<Long>> genericResponse = new GenericResponse<>(new Error("entity", "message"));
        when(mockTransactionRepository.getByType(type)).thenReturn(genericResponse);
        List<Long> actualResponse = transactionController.getTransactionsByType(mockRequest, mockResponse);
        assertEquals(Collections.emptyList(), actualResponse);
    }
}
