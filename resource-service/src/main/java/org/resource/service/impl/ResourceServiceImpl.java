package org.resource.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.apache.coyote.BadRequestException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.resource.client.SongClient;
import org.resource.dto.SongDTO;
import org.resource.entity.Resource;
import org.resource.mapper.Mp3MetadataMapper;
import org.resource.repository.ResourceRepository;
import org.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final int MAX_ALLOWED_IDS_LENGTH = 200;

    private final ResourceRepository resourceRepository;
    private final Mp3MetadataMapper mapper;

    private final SongClient songClient;

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository, Mp3MetadataMapper mapper,
                               SongClient songClient) {
        this.resourceRepository = resourceRepository;
        this.mapper = mapper;
        this.songClient = songClient;
    }

    @Override
    public Long save(byte[] bytes) throws TikaException, IOException, SAXException, HttpClientErrorException {
        if (!validateFile(bytes)) {
            throw new BadRequestException("Mp3 file is invalid");
        }
        Resource resource = new Resource();
        resource.setFileData(bytes);

        Resource savedFile = resourceRepository.save(resource);

        Long fileId = savedFile.getId();
        SongDTO metadata = mapper.mapToSong(bytes, fileId);
        songClient.sendMetadata(metadata);

        return fileId;
    }

    @Override
    public Resource get(Long id) {
        return resourceRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Resource with ID = %d not found", id)));
    }

    @Override
    public List<Long> delete(String ids) {
        if (ids.length() > MAX_ALLOWED_IDS_LENGTH) {
            throw new ValidationException("The length of ids should not exceed " + MAX_ALLOWED_IDS_LENGTH);
        }

        List<Long> idList =
                Arrays.stream(ids.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());

        Iterable<Resource> existingResourcesList = resourceRepository.findAllById(idList);

        List<Long> existingIds = StreamSupport.stream(existingResourcesList.spliterator(), false)
                .map(Resource::getId)
                .toList();
        if (!existingIds.isEmpty()) {
            resourceRepository.deleteAllById(existingIds);
            songClient.deleteSongsMetadata(existingIds);
        }

        return existingIds;
    }

    @Override
    public void deleteById(Long id) {
        resourceRepository.deleteById(id);
    }


    public boolean validateFile(byte[] file) {
        if (file == null || file.length == 0) {
            return false;
        }

        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        return mimeType != null && "audio/mpeg".equalsIgnoreCase(mimeType);
    }

}
