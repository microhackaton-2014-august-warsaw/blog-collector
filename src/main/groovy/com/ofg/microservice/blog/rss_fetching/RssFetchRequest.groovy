package com.ofg.microservice.blog.rss_fetching

import groovy.transform.Canonical
import groovy.transform.TypeChecked

@TypeChecked
@Canonical
class RssFetchRequest extends BaseActorMessage {

    String rssUrl

    RssFetchRequest(String rssUrl, String pairId, String correlationId) {
        super(pairId, correlationId)
        this.rssUrl = rssUrl
    }
}
