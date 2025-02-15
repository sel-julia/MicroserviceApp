package org.song.controller;

import jakarta.validation.Valid;
import org.song.dto.SongDTO;
import org.song.entity.Song;
import org.song.mapper.SongMapper;
import org.song.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/songs", produces = "application/json")
public class SongServiceController {
    private SongService songService;
    private SongMapper mapper;

    @Autowired
    public SongServiceController(SongService songService, SongMapper songMapper) {
       this.songService = songService;
       this.mapper = songMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> saveSongMetadata(@Valid @RequestBody SongDTO song) {
        Song songEntity = mapper.fromDTO(song);
        long songId = songService.save(songEntity);
        return ResponseEntity.ok()
                .body(Collections.singletonMap("id", songId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongMetadata(@PathVariable("id") Long id) {
        Song song = songService.get(id);

        return ResponseEntity.ok()
                .body(mapper.toDTO(song));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, List<Long>>> deleteSongMetadata(@RequestParam("id") String ids) {
        List<Long> deletedIds = songService.delete(ids);
        return ResponseEntity.ok(Collections.singletonMap("ids", deletedIds));
    }

}
