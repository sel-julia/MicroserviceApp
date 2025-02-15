package org.song.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.song.entity.Song;
import org.song.repository.SongRepository;
import org.song.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SongServiceImpl implements SongService {

    private static final int MAX_ALLOWED_IDS_LENGTH = 200;

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Long save(Song song) {
        if (songRepository.existsById(song.getId())){
            throw new EntityExistsException(String.format("Song metadata for resource with id %s already exists",
                    song.getId()));
        }

        Song result = songRepository.save(song);
        return result.getId();
    }

    @Override
    public Song get(Long id) {
        return songRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Song with ID = %d not found", id)));
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

        Iterable<Song> existingListSongMetaData = songRepository.findAllById(idList);

        List<Long> existingIds = StreamSupport.stream(existingListSongMetaData.spliterator(), false)
                .map(Song::getId)
                .toList();
        songRepository.deleteAllById(existingIds);

        return existingIds;
    }

    @Override
    public void deleteById(Long id) {
        songRepository.deleteById(id);
    }
}
