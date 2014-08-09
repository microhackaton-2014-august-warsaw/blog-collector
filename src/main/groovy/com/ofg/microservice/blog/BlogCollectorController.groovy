package com.ofg.microservice.blog

import com.ofg.infrastructure.web.filter.correlationid.CorrelationIdHolder
import com.ofg.microservice.blog.eventbus.BlogCollectorActorSystem
import com.ofg.microservice.blog.rss_fetching.RssFetchRequest
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

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
    @RequestMapping(value = "/blogs/{encodedBlogUrl}/{pairId}", produces="application/json", method = RequestMethod.GET)
    void getBlogs(@PathVariable String encodedBlogUrl, @PathVariable Long pairId) {
        String url = URLDecoder.decode(encodedBlogUrl, "UTF-8")
        log.info("Received request with " + url + " and " + pairId)

        blogCollectorActorSystem.getActorSystem().eventStream()
                .publish(new RssFetchRequest("http://tomaszdziurko.pl/feed/", pairId,
                CorrelationIdHolder.get()))
    }
}
