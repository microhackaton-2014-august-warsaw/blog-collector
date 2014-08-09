package com.ofg.microservice.blog.rss_fetching

import com.rometools.rome.feed.synd.SyndFeed
import groovy.transform.Canonical
import groovy.transform.TypeChecked

@TypeChecked
@Canonical
class RssData extends BaseActorMessage {
    SyndFeed feed

    RssData(SyndFeed feed, Long pairId, String correlationId) {
        super(pairId, correlationId)
        this.feed = feed
    }
}
