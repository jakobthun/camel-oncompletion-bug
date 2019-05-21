package se.jttl.camel.cameloncompletionbug;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example2PossibleBugBehaviour extends RouteBuilder {

    public static final String ORIGINAL_ROUTE_NAME  = "PossibleBug-original-route";
    public static final String SUBROUTE_NAME        = "PossibleBug-subroute";
    public static final String DIRECT_ENDPOINT      = "direct:possiblebug";

    @Override
    public void configure() throws Exception {

        from("timer:expected?period=10000&delay=5000")
                .routeId(ORIGINAL_ROUTE_NAME)
                .onCompletion().modeBeforeConsumer()
                    .log("This should be done once, when the original route is completed i.e. after log 3. But when onCompletion is defined in top of route AND it is in modeBeforeConsumer() it will also be applied to when the direct-route is completed. So it will be executed twice.")
                .end()
                .log("1")
                .to(DIRECT_ENDPOINT)
                .log("3");

        from(DIRECT_ENDPOINT)
                .routeId(SUBROUTE_NAME)
                .log("2");

    }

}
