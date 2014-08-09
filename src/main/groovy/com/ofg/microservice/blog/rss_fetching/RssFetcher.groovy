package com.ofg.microservice.blog.rss_fetching

import akka.actor.UntypedActor
import com.ofg.infrastructure.web.filter.correlationid.CorrelationIdHolder
import com.rometools.fetcher.impl.HttpURLFeedFetcher
import com.rometools.rome.feed.synd.SyndFeed
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.jboss.logging.MDC

@TypeChecked
@Slf4j
public class RssFetcher extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        if (message instanceof RssFetchRequest) {
            processRequest(message as RssFetchRequest)
        } else {
            unhandled(message);
        }
    }

    void processRequest(RssFetchRequest fetchRequest) throws Exception {
        MDC.put(CorrelationIdHolder.CORRELATION_ID_HEADER, fetchRequest.correlationId)

        log.info("Fetching rss from " + fetchRequest.rssUrl)
        HttpURLFeedFetcher fetcher = new HttpURLFeedFetcher()
        SyndFeed feed = fetcher.retrieveFeed(new URL(fetchRequest.rssUrl))
        log.info("Fetched " + feed.entries.size())
    }


}
