package assignment;

import assignment.controller.TransactionController;
import assignment.transformer.JsonResponseTransformer;
import com.gojek.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.after;
import static spark.Spark.awaitStop;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.stop;

public class Server {

    private final Logger logger = LoggerFactory.getLogger(Server.class);

    private final ApplicationConfiguration appConfig;
    private final TransactionController transactionController;
    private final JsonResponseTransformer jsonResponseTransformer;

    public Server(ApplicationConfiguration appConfig, TransactionController transactionController, JsonResponseTransformer jsonResponseTransformer) {
        this.appConfig = appConfig;
        this.transactionController = transactionController;
        this.jsonResponseTransformer = jsonResponseTransformer;
    }

    public void startServer() {
        logger.debug("server has started");
        port(appConfig.getValueAsInt("APP_PORT"));

        put("/transactionservice/transaction/:transaction_id", transactionController::upsertTransaction, jsonResponseTransformer);

        after("/*", (request, response) -> response.type("application/json"));
    }

    public void stopServer() throws InterruptedException {
        long shutdownTimeout = appConfig.getValueAsLong("SHUTDOWN_TIMEOUT_MS", 20000);
        stopServer(shutdownTimeout);
    }

    public void stopServer(long timeout) throws InterruptedException {
        Thread.sleep(timeout);
        stop();
        awaitStop();
    }
}
