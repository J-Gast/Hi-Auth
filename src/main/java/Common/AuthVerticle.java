package Common;

import Utils.UtilsRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class AuthVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        final Router router = Router.router(vertx);
        router.get("/register").handler(this::signUp);
        router.get("/login").handler(this::signIn);
        router.get("/recovery").handler(this::recoverPassword);
        UtilsRouter.getInstance(vertx).mountSubRouter(getEndPointAddress(), router);

        vertx.createHttpServer().requestHandler(UtilsRouter.getInstance(vertx)::accept).listen(8888, ar -> {
            if(ar.succeeded()) {
                LOGGER.info("Server is running");
                startFuture.complete();
            }
            else {
                startFuture.fail(ar.cause());
            }
        });
    }

    protected abstract void signUp(RoutingContext context);
    protected abstract void signIn(RoutingContext context);
    protected abstract void recoverPassword(RoutingContext context);
    protected abstract String getEndPointAddress();
}
