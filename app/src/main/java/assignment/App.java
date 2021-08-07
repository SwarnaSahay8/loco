package assignment;

import assignment.controller.TransactionController;
import assignment.db.DbManager;
import assignment.repository.TransactionRepository;
import assignment.transformer.JsonResponseTransformer;
import assignment.validator.AddTransactionRequestValidator;
import com.gojek.ApplicationConfiguration;
import com.gojek.Figaro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        ApplicationConfiguration appConfig = Figaro.configure(null);
        DbManager dbManager = new DbManager(appConfig);
        TransactionRepository transactionRepository = new TransactionRepository(dbManager.getConnection());
        TransactionController transactionController = new TransactionController(transactionRepository, new AddTransactionRequestValidator());
        JsonResponseTransformer jsonResponseTransformer = new JsonResponseTransformer();
        Server server = new Server(appConfig, transactionController, jsonResponseTransformer);

        server.startServer();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Stopping service...");
            try {
                server.stopServer();
            } catch (InterruptedException e) {
                LOGGER.error("Unable to stop server", e);
            }
        }));
    }
}
