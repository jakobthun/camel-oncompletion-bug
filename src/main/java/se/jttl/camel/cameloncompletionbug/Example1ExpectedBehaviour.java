package se.jttl.camel.cameloncompletionbug;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example1ExpectedBehaviour extends RouteBuilder {

    public static final String ORIGINAL_ROUTE_NAME = "Expected-original-route";
    public static final String SUBROUTE_NAME = "Expected-subroute";
    public static final String DIRECT_ENDPOINT = "direct:expected";

    @Override
    public void configure() throws Exception {

        from("timer:expected?period=10000&delay=100")
                .routeId(ORIGINAL_ROUTE_NAME)
                .log("1")
                .to(DIRECT_ENDPOINT)
                .log("3")
                .onCompletion().modeBeforeConsumer()
                    .log("This should be done once, when the original route is completed i.e. after log 3. Behaves the same regardless of modeBefore/AfterConsumer.")
                .end();

        from(DIRECT_ENDPOINT)
                .routeId(SUBROUTE_NAME)
                .log("2");

    }

}
