package com.ofg.microservice.blog.eventbus

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.routing.RoundRobinRouter
import com.ofg.microservice.blog.rss_fetching.data_extraction.RssData
import com.ofg.microservice.blog.rss_fetching.data_extraction.RssDataExtractor
import com.ofg.microservice.blog.rss_fetching.RssFetchRequest
import com.ofg.microservice.blog.rss_fetching.RssFetcher
import com.ofg.microservice.blog.rss_fetching.data_extraction.RssDataExtractorProducer
import com.ofg.microservice.config.SpringContextHolder
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@TypeChecked
@Slf4j
@Component
class BlogCollectorActorSystem {

    private ActorSystem actorSystem

    private @Autowired ApplicationContext applicationContext

    BlogCollectorActorSystem() {
        log.info("Initializing ActorSystem")
        actorSystem = ActorSystem.create("BlogCollectorActorSystem")
    }

    @PostConstruct
    void setAppContext() {
        SpringContextHolder.context = applicationContext

        ActorRef fetcherRouter = actorSystem.actorOf(Props.create(RssFetcher)
                .withRouter(new RoundRobinRouter(5)),
                "RssFetcher");

        ActorRef rssDataExtractorRouter = actorSystem
                .actorOf(Props.create(RssDataExtractorProducer)
                .withRouter(new RoundRobinRouter(5)),
                "RssDataExtractor");

        actorSystem.eventStream().subscribe(fetcherRouter, RssFetchRequest)
        actorSystem.eventStream().subscribe(rssDataExtractorRouter, RssData)
    }

    ActorSystem getActorSystem() {
        return actorSystem
    }

}
