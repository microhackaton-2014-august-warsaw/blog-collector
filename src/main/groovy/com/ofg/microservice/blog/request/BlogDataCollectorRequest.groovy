package com.ofg.microservice.blog.request

import groovy.transform.Canonical
import groovy.transform.TypeChecked

@TypeChecked
@Canonical
class BlogDataCollectorRequest {

    String rssUrl
    long pairId
}
