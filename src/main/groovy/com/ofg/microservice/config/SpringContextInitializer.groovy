package com.ofg.microservice.config

import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@TypeChecked
@Slf4j
@Component
class SpringContextHolder {

    static ApplicationContext context
}
