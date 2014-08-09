package com.ofg.microservice.blog

import com.ofg.microservice.twitter.TwitterCollector
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@TypeChecked
@RestController
@Slf4j
class BlogCollectorController {
    private TwitterCollector collectorWorker

    @Autowired
    BlogCollectorController(TwitterCollector collectorWorker) {
        this.collectorWorker = collectorWorker
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/blogs/{encodedBlogUrl}/{pairId}", produces="application/json", method = RequestMethod.GET)
    void getTweets(@PathVariable String encodedBlogUrl, @PathVariable Long pairId) {
        String url = URLDecoder.decode(encodedBlogUrl, "UTF-8")
        log.info("Received request with" + url + " and " + pairId)
    }
}
