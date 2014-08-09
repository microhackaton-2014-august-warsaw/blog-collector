package com.ofg.microservice.blog.rss_fetching.data_extraction

import akka.actor.Actor
import akka.actor.IndirectActorProducer
import com.ofg.infrastructure.discovery.ServiceResolver
import com.ofg.infrastructure.web.resttemplate.RestTemplate
import com.ofg.microservice.config.SpringContextHolder
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j

@TypeChecked
@Slf4j
class RssDataExtractorProducer implements IndirectActorProducer {

    @Override
    Actor produce() {
        return new RssDataExtractor(
                SpringContextHolder.context.getBean(ServiceResolver) as ServiceResolver,
                SpringContextHolder.context.getBean(RestTemplate) as RestTemplate
        )
    }

    @Override
    Class<? extends Actor> actorClass() {
        return RssDataExtractor
    }
}
