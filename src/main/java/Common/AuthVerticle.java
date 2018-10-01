package Common;

import Utils.UtilsRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

public abstract class AuthVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthVerticle.class);
    protected JsonArray listOfUser;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        final Router router = Router.router(vertx);
        router.post("/register").handler(this::signUp);
        router.post("/login").handler(this::signIn);
        router.post("/recovery").handler(this::recoverPassword);
        router.get("/getusers").handler(this::getListOfUsers);
        UtilsRouter.getInstance(vertx).mountSubRouter(getEndPointAddress(), router);

        listOfUser = new JsonArray();

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

    private void getListOfUsers(RoutingContext context) {
        JsonArray list = new JsonArray();
        for(int i = 0; i < listOfUser.size(); i++) {
            list.add(listOfUser.getJsonObject(i));
        }
        context.response().end(list.encode());
    }
}
