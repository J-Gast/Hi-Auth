package Validators;

import Common.ValidatorVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class UserValidator extends ValidatorVerticle {
    @Override
    protected String getEndPointAddress() {
        return "/userauth";
    }

    @Override
    protected void validate(Message message) {
        JsonObject jsonMessage = (JsonObject) message.body();
        if(jsonMessage.getString("username").equals("Gorito"))
            message.reply(true);
        else
            message.reply(false);
    }
}
