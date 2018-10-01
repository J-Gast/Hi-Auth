package main;

import AuthMethods.UserAuth;
import Validators.UserValidator;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

    public static void main(String[] args) {
        LOGGER.info("Main entrance");

        Vertx v = Vertx.vertx();
        v.deployVerticle(new MainVerticle());
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        LOGGER.info("Starting main verticle");

        try {
            vertx.deployVerticle(new UserAuth());
            vertx.deployVerticle(new UserValidator());
        } catch(Exception e) {
            LOGGER.error(e.getMessage());
            System.exit(0);
        }
    }
}
