package AuthMethods;

import Common.IConfirmable;
import Common.IRecoverable;
import Common.AuthVerticle;
import io.vertx.ext.web.RoutingContext;

public class UserAuth extends AuthVerticle {

    @Override
    protected void signUp(RoutingContext context) {
        context.response().end("Registering");
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
