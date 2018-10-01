package Common;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;

public abstract class ValidatorVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {
        vertx.eventBus().consumer(getEndPointAddress(), this::validate);
    }

    protected abstract String getEndPointAddress();
    protected abstract void validate(Message message);
}
