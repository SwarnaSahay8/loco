package assignment.validator;

import assignment.contract.request.AddTransactionRequest;
import com.google.common.base.Strings;

public class AddTransactionRequestValidator {

    public boolean validate(AddTransactionRequest request) {
        return !(request.getAmount() == null) && !(Strings.isNullOrEmpty(request.getType()));
    }
}
