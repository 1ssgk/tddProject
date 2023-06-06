package com.wonseok.subject.domain.manager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@PreAuthorize("permitAll()")
public class HelloController {

    @GetMapping("/ws/hello")
    public String hello() {
        log.info("hello comcom");
        return "hi";
    }
}
