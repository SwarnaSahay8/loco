package assignment.mapper;

import assignment.contract.request.UpsertTransactionRequest;
import assignment.model.Transaction;
import assignment.model.TransactionBuilder;

public class TransactionRequestMapper {

    public static Transaction mapFromUpsertTransactionRequest(UpsertTransactionRequest upsertTransactionRequest, Long transactionId) {
        return new TransactionBuilder()
                .withTransactionId(transactionId)
                .withAmount(upsertTransactionRequest.getAmount())
                .withType(upsertTransactionRequest.getType())
                .withParentId(upsertTransactionRequest.getParentId())
                .build();
    }
}
