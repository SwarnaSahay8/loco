package assignment.mapper;

import assignment.contract.request.AddTransactionRequest;
import assignment.model.Transaction;
import assignment.model.TransactionBuilder;

public class TransactionRequestMapper {

    public static Transaction mapFromAddTransactionRequest(AddTransactionRequest addTransactionRequest, Long transactionId) {
        return new TransactionBuilder()
                .withTransactionId(transactionId)
                .withAmount(addTransactionRequest.getAmount())
                .withType(addTransactionRequest.getType())
                .withParentId(addTransactionRequest.getParentId())
                .build();
    }
}
