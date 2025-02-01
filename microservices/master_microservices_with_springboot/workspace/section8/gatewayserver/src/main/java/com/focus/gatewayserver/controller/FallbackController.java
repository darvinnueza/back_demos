package com.focus.gatewayserver.controller;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport() {
        return Mono.just("An error occurred. Please try after some time or contact support team!!!");
    }
}