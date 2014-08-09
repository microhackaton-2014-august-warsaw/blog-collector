package com.ofg.microservice.blog.rss_fetching

import akka.actor.UntypedActor
import com.ofg.infrastructure.web.filter.correlationid.CorrelationIdHolder
import com.ofg.microservice.blog.response.BlogDataResponse
import com.ofg.microservice.blog.utils.HtmlOgolator
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.slf4j.MDC

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
        BlogDataResponse response = extractData(rssData)

        response.toString()
    }

    private BlogDataResponse extractData(RssData rssData) {
        BlogDataResponse response = new BlogDataResponse(rssData.pairId, rssData.rssUrl)

        rssData.feed.entries.each {
            response.addPost(HtmlOgolator.doTheJob(it.contents.get(0).value))
            response.addTitle(it.title)
        }
        response
    }
}
