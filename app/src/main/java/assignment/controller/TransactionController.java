package assignment.controller;

import assignment.contract.request.UpsertTransactionRequest;
import assignment.contract.response.GenericContractResponse;
import assignment.mapper.TransactionRequestMapper;
import assignment.model.GenericResponse;
import assignment.model.Transaction;
import assignment.repository.TransactionRepository;
import assignment.utils.JsonUtil;
import assignment.validator.AddTransactionRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static assignment.constants.TransactionConstants.BAD_REQUEST_HTTP_CODE;
import static assignment.constants.TransactionConstants.FAILED_STATUS;
import static assignment.constants.TransactionConstants.SUCCESS_HTTP_CODE;
import static assignment.constants.TransactionConstants.SUCCESS_STATUS;
import static assignment.constants.TransactionConstants.TRANSACTION_ID_PARAM;

public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionRepository transactionRepository;
    private final AddTransactionRequestValidator validator;

    public TransactionController(TransactionRepository transactionRepository, AddTransactionRequestValidator validator) {
        this.transactionRepository = transactionRepository;
        this.validator = validator;
    }

    public GenericContractResponse upsertTransaction(Request request, Response response) {
        Long transactionId = Long.valueOf(request.params(TRANSACTION_ID_PARAM));
        UpsertTransactionRequest transactionRequest = JsonUtil.deserialize(request.body(), UpsertTransactionRequest.class);

        GenericResponse validationResponse = validator.validate(transactionRequest);
        if (!validationResponse.isSuccess()) {
            logger.error("validation failed for add transaction");
            return getContractResponse(response, BAD_REQUEST_HTTP_CODE, FAILED_STATUS);
        }
        Transaction transaction = TransactionRequestMapper.mapFromUpsertTransactionRequest(transactionRequest, transactionId);
        GenericResponse dbInsertResponse = transactionRepository.upsert(transaction);
        if (dbInsertResponse.isSuccess()) {
            logger.debug("add transaction successful");
            return getContractResponse(response, SUCCESS_HTTP_CODE, SUCCESS_STATUS);
        } else {
            logger.error("add transaction failed");
            return getContractResponse(response, BAD_REQUEST_HTTP_CODE, FAILED_STATUS);
        }
    }

    private GenericContractResponse getContractResponse(Response response, int badRequestHttpCode, String failedStatus) {
        response.status(badRequestHttpCode);
        return new GenericContractResponse(failedStatus);
    }
}
