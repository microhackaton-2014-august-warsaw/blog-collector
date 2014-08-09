package com.ofg.microservice.blog.rss_fetching

import com.ofg.infrastructure.web.filter.correlationid.CorrelationIdHolder
import groovy.transform.Canonical
import groovy.transform.TypeChecked

@TypeChecked
class RssFetchRequest {

    String rssUrl
    Long pairId
    String correlationId

    RssFetchRequest(String rssUrl, Long pairId, String correlationId) {
        this.rssUrl = rssUrl
        this.pairId = pairId
        this.correlationId = correlationId
    }
}
