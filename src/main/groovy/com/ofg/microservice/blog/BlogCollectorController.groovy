package com.ofg.microservice.blog

import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@TypeChecked
@RestController
@Slf4j
class BlogCollectorController {

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/blogs/{encodedBlogUrl}/{pairId}", produces="application/json", method = RequestMethod.GET)
    void getTweets(@PathVariable String encodedBlogUrl, @PathVariable Long pairId) {
        String url = URLDecoder.decode(encodedBlogUrl, "UTF-8")
        log.info("Received request with " + url + " and " + pairId)
    }
}
