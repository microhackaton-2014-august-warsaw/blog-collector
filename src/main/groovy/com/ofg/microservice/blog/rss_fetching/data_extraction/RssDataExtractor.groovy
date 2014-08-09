package com.ofg.microservice.blog.rss_fetching.data_extraction

import akka.actor.UntypedActor
import com.ofg.infrastructure.discovery.ServiceResolver
import com.ofg.infrastructure.web.filter.correlationid.CorrelationIdHolder
import com.ofg.infrastructure.web.resttemplate.RestTemplate
import com.ofg.microservice.blog.response.BlogDataResponse
import com.ofg.microservice.blog.utils.HtmlOgolator
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.slf4j.MDC
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@TypeChecked
@Slf4j
class RssDataExtractor extends UntypedActor {

    static MediaType MEDIA_TYPE = new MediaType('application', 'vnd.com.ofg.blog-topics-analyzer.v1+json')

    public static final String ANALYZER_NAME = "blog-topics-analyzer"
    private RestTemplate restTemplate
    private ServiceResolver serviceResolver

    RssDataExtractor(ServiceResolver serviceResolver, RestTemplate restTemplate) {
        this.serviceResolver = serviceResolver
        this.restTemplate = restTemplate
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof RssData) {
            processRequest(message as RssData)
        } else {
            unhandled(message);
        }
    }

    void processRequest(RssData rssData) throws Exception {
        MDC.put(CorrelationIdHolder.CORRELATION_ID_HEADER, rssData.correlationId)
        log.info("Extracting data from " + rssData.feed.link)
        BlogDataResponse response = extractData(rssData)

        sendRequest(response)
    }

    private BlogDataResponse extractData(RssData rssData) {
        BlogDataResponse response = new BlogDataResponse(rssData.pairId, rssData.rssUrl)

        rssData.feed.entries.each {
            response.addPost(HtmlOgolator.doTheJob(it.contents.get(0).value))
            response.addTitle(it.title)
        }
        response
    }

    void sendRequest(BlogDataResponse blogDataResponse) {
        com.google.common.base.Optional<String> analyzerUrlOptional = serviceResolver.getUrl(ANALYZER_NAME)
        if (analyzerUrlOptional.isPresent()) {
            log.info("Sending data for pairId " + blogDataResponse.pairId)
            restTemplate.put("http://54.73.40.79:9109"+ "/api/{pairId}", createEntity(blogDataResponse), blogDataResponse.pairId)
            log.info("Sending data for pairId " + blogDataResponse.pairId + " successful")
        } else {
            log.error("No instance of " + ANALYZER_NAME + " found")
        }
    }

    private HttpEntity<Object> createEntity(Object object) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MEDIA_TYPE)
        headers.set(CorrelationIdHolder.CORRELATION_ID_HEADER, MDC.get(CorrelationIdHolder.CORRELATION_ID_HEADER))
        return new HttpEntity<Object>(object, headers);
    }
}
