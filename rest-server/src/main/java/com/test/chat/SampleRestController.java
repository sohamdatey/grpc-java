package com.test.chat;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class SampleRestController {

    @GetMapping(value = "/rest-test", produces = MediaType.APPLICATION_JSON_VALUE)
    public String get() {
        Gson gson = new Gson();
        return gson.toJson(IntStream.range(0, 1000000).mapToObj(i -> new Test()).collect(Collectors.toList()));

    }
}
