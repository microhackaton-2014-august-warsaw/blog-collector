package com.ofg.microservice.blog.rss_fetching

import akka.actor.UntypedActor
import com.ofg.infrastructure.web.filter.correlationid.CorrelationIdHolder
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.jboss.logging.MDC

@TypeChecked
@Slf4j
class RssDataExtractor extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        if (message instanceof RssData) {
            processRequest(message as RssData)
        } else {
            unhandled(message);
        }
    }

    void processRequest(RssData rssData) throws Exception {
        MDC.put(CorrelationIdHolder.CORRELATION_ID_HEADER, rssData.correlationId)
        log.info("Extracting data from " + rssData.feed.link)
    }
}
