package demo.actor;

import akka.actor.UntypedAbstractActor;
import demo.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("workerActor")
@Scope("prototype")
public class WorkerActor extends UntypedAbstractActor {

    @Autowired
    private BusinessService businessService;

    private int count = 0;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Request) {
            businessService.perform(this + " " + (++count));
        } else if (message instanceof Response) {
            getSender().tell(count, getSelf());
        } else {
            unhandled(message);
        }
    }

    public static class Request {
    }

    public static class Response {
    }
}