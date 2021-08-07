package assignment.repository;

import assignment.model.Error;
import assignment.model.GenericResponse;
import assignment.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRepository {

    private final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    private final Connection connection;

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    public GenericResponse insert(Transaction transaction) {
        String sql = "insert into transactions (transaction_id, amount, type, parent_id) values (?, ?, ?, ?) " +
                "on conflict (transaction_id) do update set amount=?, type=?, parent_id=?";
        if (transaction.getParentId() == null) {
            sql = "insert into transactions (transaction_id, amount, type, parent_id) values (?, ?, ?, null) " +
                    "on conflict (transaction_id) do update set amount=?, type=?, parent_id=null";
        }
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            setPreparedStatementForInsert(transaction, statement);
            int executeResult = statement.executeUpdate();
            logger.debug("statement execute row update: {}", executeResult);
            return new GenericResponse(true);
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
            statement.setDouble(5, transaction.getAmount());
            statement.setString(6, transaction.getType());
            statement.setLong(7, transaction.getParentId());
        } else {
            statement.setDouble(4, transaction.getAmount());
            statement.setString(5, transaction.getType());
        }
    }

    private GenericResponse exceptionResponse() {
        return new GenericResponse(new Error("db", "sql exception"));
    }
}
