package AuthMethods;

import Common.IConfirmable;
import Common.IRecoverable;
import Common.AuthVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class UserAuth extends AuthVerticle {

    @Override
    protected void signUp(RoutingContext context) {
        context.request().bodyHandler(bodyHandler -> {
            try {
                JsonObject body = bodyHandler.toJsonObject();

                vertx.eventBus().send(this.getEndPointAddress(), body, ar -> {
                    if (ar.succeeded()) {
                        if((Boolean)ar.result().body()) {
                            listOfUser.add(body);
                            context.response().end("Registered: " + body.encodePrettily());
                        } else context.response().end("Fail.");
                    } else {
                        context.response().end("Fail.");
                    }
                });
            } catch (Exception e) {
                context.response().end("Fail.");
            }
        });
    }

    @Override
    protected void signIn(RoutingContext context) {
        context.response().end("Login");
    }

    @Override
    protected void recoverPassword(RoutingContext context) {
        context.response().end("Recovering");
    }

    @Override
    protected String getEndPointAddress() {
        return "/userauth";
    }
}
