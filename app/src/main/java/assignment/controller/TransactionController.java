package assignment.controller;

import assignment.contract.request.AddTransactionRequest;
import assignment.contract.response.GenericContractResponse;
import assignment.mapper.TransactionRequestMapper;
import assignment.model.GenericResponse;
import assignment.model.Transaction;
import assignment.repository.TransactionRepository;
import assignment.utils.JsonUtil;
import assignment.validator.AddTransactionRequestValidator;
import spark.Request;
import spark.Response;

import static assignment.constants.TransactionConstants.BAD_REQUEST_HTTP_CODE;
import static assignment.constants.TransactionConstants.FAILED_STATUS;
import static assignment.constants.TransactionConstants.SUCCESS_HTTP_CODE;
import static assignment.constants.TransactionConstants.SUCCESS_STATUS;
import static assignment.constants.TransactionConstants.TRANSACTION_ID_PARAM;

public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final AddTransactionRequestValidator validator;

    public TransactionController(TransactionRepository transactionRepository, AddTransactionRequestValidator validator) {
        this.transactionRepository = transactionRepository;
        this.validator = validator;
    }

    public GenericContractResponse addTransaction(Request request, Response response) {
        Long transactionId = Long.valueOf(request.params(TRANSACTION_ID_PARAM));
        AddTransactionRequest transactionRequest = JsonUtil.deserialize(request.body(), AddTransactionRequest.class);

        GenericResponse validationResponse = validator.validate(transactionRequest);
        if (!validationResponse.isSuccess()) {
            return getContractResponse(response, BAD_REQUEST_HTTP_CODE, FAILED_STATUS);
        }
        Transaction transaction = TransactionRequestMapper.mapFromAddTransactionRequest(transactionRequest, transactionId);
        GenericResponse dbInsertResponse = transactionRepository.insert(transaction);
        if (dbInsertResponse.isSuccess()) {
            return getContractResponse(response, SUCCESS_HTTP_CODE, SUCCESS_STATUS);
        } else {
            return getContractResponse(response, BAD_REQUEST_HTTP_CODE, FAILED_STATUS);
        }
    }

    private GenericContractResponse getContractResponse(Response response, int badRequestHttpCode, String failedStatus) {
        response.status(badRequestHttpCode);
        return new GenericContractResponse(failedStatus);
    }
}
