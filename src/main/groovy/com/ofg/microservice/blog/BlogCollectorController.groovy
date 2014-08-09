package com.ofg.microservice.blog

import com.ofg.infrastructure.web.filter.correlationid.CorrelationIdHolder
import com.ofg.microservice.blog.eventbus.BlogCollectorActorSystem
import com.ofg.microservice.blog.request.BlogDataCollectorRequest
import com.ofg.microservice.blog.rss_fetching.RssFetchRequest
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@TypeChecked
@RestController
@Slf4j
class BlogCollectorController {

    private BlogCollectorActorSystem blogCollectorActorSystem

    @Autowired
    BlogCollectorController(BlogCollectorActorSystem blogCollectorActorSystem) {
        this.blogCollectorActorSystem = blogCollectorActorSystem
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/", produces="application/json",
            method = RequestMethod.POST)
    void getBlogs(@RequestBody BlogDataCollectorRequest request) {
        log.info("Received request with " + request.rssUrl + " and " + request.pairId)

        blogCollectorActorSystem.getActorSystem().eventStream()
                .publish(new RssFetchRequest(request.rssUrl, request.pairId,
                CorrelationIdHolder.get()))
    }
}
