package com.ofg.microservice.blog.response

import groovy.transform.Canonical
import groovy.transform.TypeChecked

@TypeChecked
@Canonical
class BlogDataResponse {

    String rssUrl
    Long pairId

    Collection<String> posts = new ArrayList<>()
    Collection<String> titles = new ArrayList<>()

    BlogDataResponse(Long pairId, String rssUrl) {
        this.pairId = pairId
        this.rssUrl = rssUrl
    }

    void addPost(String post) {
        posts.add(post)
    }

    void addTitle(String title) {
        titles.add(title)
    }
}
