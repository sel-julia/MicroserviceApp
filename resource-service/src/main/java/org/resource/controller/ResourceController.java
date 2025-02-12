package org.resource.controller;

import jakarta.validation.constraints.Positive;
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
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT_ENCODING;

@RestController
@RequestMapping("/resources")
@Validated
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<Object> uploadResource(@RequestBody byte[] bytes) throws TikaException, IOException, SAXException {
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
    public ResponseEntity<Object> deleteResources(@RequestParam(required = false, name = "id") List<Long> ids) {
        List<Long> result = resourceService.delete(ids);

        return ResponseEntity.ok()
                .body(Collections.singletonMap("ids", result.toArray()));
    }

    @DeleteMapping("/{id}")
    public void deleteResourceById(@PathVariable("id") Long id) {
        resourceService.deleteById(id);
    }

}
