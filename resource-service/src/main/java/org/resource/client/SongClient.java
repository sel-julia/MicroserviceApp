package org.resource.client;

import org.resource.dto.SongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class SongClient {

    @Value("${songservice.baseAddress}")
    private String songServiceAddress;

    private final RestTemplate restTemplate;

    @Autowired
    public SongClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMetadata(SongDTO metadata) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SongDTO> requestEntity = new HttpEntity<>(metadata, headers);

        String url = songServiceAddress + "/songs";

        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    public String deleteSongsMetadata(List<Long> ids) {
        String url = songServiceAddress + "songs";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Integer>> httpEntity = new HttpEntity<>(headers);
        String fullUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id", String.join(",", ids.stream().map(String::valueOf).toList()))
                .toUriString();

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(fullUrl, HttpMethod.DELETE, httpEntity, String.class);

        return responseEntity.getBody();
    }

}
