package org.resource.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.apache.tika.exception.TikaException;
import org.resource.entity.Resource;
import org.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.ACCEPT_ENCODING;

@RestController
@RequestMapping("/resources")
@Validated
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(consumes = "audio/mpeg", produces = "application/json")
    public ResponseEntity<Map<String, Long>> uploadResource(@RequestBody byte[] bytes) throws TikaException, IOException, SAXException {
        Long savedResourceId = resourceService.save(bytes);

        return ResponseEntity.ok()
                .body(Collections.singletonMap("id", savedResourceId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> getResourceById(@PathVariable("id") @Positive Long id) {
        Resource resource = resourceService.get(id);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("audio","mpeg"));
        httpHeaders.add(ACCEPT_ENCODING, "identity");

        return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .body(resource.getFileData());
    }

    @DeleteMapping
    public ResponseEntity<Map<String, List<Long>>> deleteResources(@RequestParam(required = false, name = "id") @Size(max = 200)  String ids) {
        List<Long> idList =
                Arrays.stream(ids.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
        List<Long> result = resourceService.delete(idList);

        return ResponseEntity.ok()
                .body(Collections.singletonMap("ids", result));
    }

    @DeleteMapping("/{id}")
    public void deleteResourceById(@PathVariable("id") Long id) {
        resourceService.deleteById(id);
    }

}
