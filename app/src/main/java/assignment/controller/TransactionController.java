package assignment.controller;

import assignment.contract.request.UpsertTransactionRequest;
import assignment.contract.response.GenericContractResponse;
import assignment.contract.response.TransactionAmountResponse;
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

import java.util.Collections;
import java.util.List;

import static assignment.constants.TransactionConstants.BAD_REQUEST_HTTP_CODE;
import static assignment.constants.TransactionConstants.FAILED_STATUS;
import static assignment.constants.TransactionConstants.SUCCESS_HTTP_CODE;
import static assignment.constants.TransactionConstants.SUCCESS_STATUS;
import static assignment.constants.TransactionConstants.TRANSACTION_ID_PARAM;
import static assignment.constants.TransactionConstants.TYPE_PARAM;

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

    public List<Long> getTransactionsByType(Request request, Response response) {
        String type = request.params(TYPE_PARAM);
        GenericResponse<List<Long>> genericResponse = transactionRepository.getByType(type);
        if (genericResponse.isSuccess()) {
            logger.debug("get transaction ids request is successful");
            response.status(SUCCESS_HTTP_CODE);
            return genericResponse.getData();
        } else {
            logger.error("get transaction ids request failed");
            response.status(BAD_REQUEST_HTTP_CODE);
            return Collections.emptyList();
        }
    }

    public TransactionAmountResponse getTotalTransactionAmount(Request request, Response response) {
        Long transactionId = Long.valueOf(request.params(TRANSACTION_ID_PARAM));
        GenericResponse<Double> genericResponse = transactionRepository.getTotalTransactionAmount(transactionId);
        if (genericResponse.isSuccess()) {
            logger.debug("get total transaction amount request is successful");
            response.status(SUCCESS_HTTP_CODE);
            return new TransactionAmountResponse(genericResponse.getData());
        } else {
            logger.error("get total transaction amount request failed");
            response.status(BAD_REQUEST_HTTP_CODE);
            return null;
        }
    }

    private GenericContractResponse getContractResponse(Response response, int badRequestHttpCode, String failedStatus) {
        response.status(badRequestHttpCode);
        return new GenericContractResponse(failedStatus);
    }
}
