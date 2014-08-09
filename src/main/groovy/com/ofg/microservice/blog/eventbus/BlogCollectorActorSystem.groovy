package com.ofg.microservice.blog.eventbus

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.routing.FromConfig
import akka.routing.RoundRobinRouter
import com.ofg.microservice.blog.rss_fetching.RssData
import com.ofg.microservice.blog.rss_fetching.RssDataExtractor
import com.ofg.microservice.blog.rss_fetching.RssFetchRequest
import com.ofg.microservice.blog.rss_fetching.RssFetcher
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

@TypeChecked
@Slf4j
@Component
class BlogCollectorActorSystem {

    private ActorSystem actorSystem

    BlogCollectorActorSystem() {
        log.info("Initializing ActorSystem")
        actorSystem = ActorSystem.create("BlogCollectorActorSystem")

        ActorRef fetcherRouter = actorSystem.actorOf(Props.create(RssFetcher)
                .withRouter(new RoundRobinRouter(5)),
                "RssFetcher");

        ActorRef rssDataExtractorRouter = actorSystem.actorOf(Props.create(RssDataExtractor)
                .withRouter(new RoundRobinRouter(5)),
                "RssDataExtractor");

        actorSystem.eventStream().subscribe(fetcherRouter, RssFetchRequest)
        actorSystem.eventStream().subscribe(rssDataExtractorRouter, RssData)
    }

    ActorSystem getActorSystem() {
        return actorSystem
    }

}
