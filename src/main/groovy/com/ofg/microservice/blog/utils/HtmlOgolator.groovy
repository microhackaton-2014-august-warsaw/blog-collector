package com.ofg.microservice.blog.utils

import groovy.transform.TypeChecked
import org.jsoup.Jsoup

@TypeChecked
class HtmlOgolator {

    static String doTheJob(String htmlText) {
        return Jsoup.parse(htmlText).text()
    }
}
