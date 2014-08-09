package com.ofg.microservice.blog.rss_fetching

import groovy.transform.Canonical
import groovy.transform.TypeChecked

@TypeChecked
@Canonical
abstract class BaseActorMessage {

    String pairId
    String correlationId
}
