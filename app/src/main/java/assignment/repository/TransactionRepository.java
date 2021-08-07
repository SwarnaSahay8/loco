package assignment.repository;

import assignment.model.Error;
import assignment.model.GenericResponse;
import assignment.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRepository {

    private final Connection connection;

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    public GenericResponse insert(Transaction transaction) {
        String sql = "insert into transactions (transaction_id, amount, type, parent_id) values (?, ?, ?, ?)";
        if (transaction.getParentId() == null) {
            sql = "insert into transactions (transaction_id, amount, type) values (?, ?, ?)";
        }
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            setPreparedStatementForInsert(transaction, statement);
            return new GenericResponse(statement.execute());
        } catch (SQLException e) {
            return exceptionResponse();
        }
    }

    private void setPreparedStatementForInsert(Transaction transaction, PreparedStatement statement) throws SQLException {
        statement.setLong(1, transaction.getTransactionId());
        statement.setDouble(2, transaction.getAmount());
        statement.setString(3, transaction.getType());
        if (transaction.getParentId() != null) {
            statement.setLong(4, transaction.getParentId());
        }
    }

    private GenericResponse exceptionResponse() {
        return new GenericResponse(new Error("db", "sql exception"));
    }
}
