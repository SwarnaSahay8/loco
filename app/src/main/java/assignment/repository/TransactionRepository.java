package assignment.repository;

import assignment.model.Error;
import assignment.model.GenericResponse;
import assignment.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);
    private final Connection connection;

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    public GenericResponse upsert(Transaction transaction) {
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
            return exceptionResponse(e);
        }
    }

    public GenericResponse getByType(String type) {
        String sql = "select transaction_id from transactions where type=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type);
            ResultSet resultSet = statement.executeQuery();
            List<Long> transactionIds = new ArrayList<>();
            while (resultSet.next()) {
                transactionIds.add(resultSet.getLong(1));
            }
            return new GenericResponse<>(true, transactionIds);
        } catch (SQLException e) {
            return exceptionResponse(e);
        }
    }

    public GenericResponse getTotalTransactionAmount(Long transactionId) {
        String sql = "with recursive transactions_result as (select transaction_id, amount, parent_id from transactions " +
                "where transaction_id in (select t.transaction_id from transactions as t where t.parent_id = ?) " +
                "union all select child.transaction_id, child.amount, child.parent_id from transactions as child join" +
                " transactions_result as parent on parent.transaction_id = child.parent_id) select sum(amount) from transactions_result;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, transactionId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new GenericResponse<>(true, resultSet.getDouble(1));
        } catch (SQLException e) {
            return exceptionResponse(e);
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

    private GenericResponse exceptionResponse(Exception e) {
        logger.error("sql exception: ", e);
        return new GenericResponse(new Error("db", "sql exception"));
    }
}
