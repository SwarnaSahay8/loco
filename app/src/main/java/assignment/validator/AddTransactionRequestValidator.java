package assignment.validator;

import assignment.contract.request.UpsertTransactionRequest;
import assignment.model.Error;
import assignment.model.GenericResponse;
import com.google.common.base.Strings;

public class AddTransactionRequestValidator {

    public GenericResponse validate(UpsertTransactionRequest request) {
        if (request.getAmount() == null) {
            return new GenericResponse(new Error("amount", "amount should be present"));
        } else if (Strings.isNullOrEmpty(request.getType())) {
            return new GenericResponse(new Error("type", "type should be present"));
        }
        return new GenericResponse(true);
    }
}
