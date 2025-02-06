package org.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResourceController {

    @GetMapping("/resource")
    String get() {
        return "Hello resource controller";
    }

}