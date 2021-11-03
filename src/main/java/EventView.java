import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.TimeoutException;

@Named
@ApplicationScoped
public class EventView {

    private final Consumer consumer;
    private final EventService eventService;

    @Inject
    public EventView(Consumer consumer, EventService eventService) {
        this.consumer = consumer;
        this.eventService = eventService;
    }


    @PostConstruct
    private void init() throws IOException, TimeoutException {
        consumer.consume();

    }

//    @PreDestroy
//    private void destroy() throws IOException, TimeoutException {
//        consumer.stop();
//    }

    public List<EventDto> getEventsToday() {
        return eventService.getEventsToday();
    }
}
