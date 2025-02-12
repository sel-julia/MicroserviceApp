package org.song.mapper;

import org.song.dto.SongDTO;
import org.song.entity.Song;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {

    public Song fromDTO(SongDTO dto) {
        return Song.builder()
                .id(dto.getId())
                .name(dto.getName())
                .artist(dto.getArtist())
                .album(dto.getAlbum())
                .year(dto.getYear())
                .duration(dto.getDuration())
                .build();
    }

    public SongDTO toDTO(Song song) {
        return SongDTO.builder()
                .id(song.getId())
                .name(song.getName())
                .artist(song.getArtist())
                .album(song.getAlbum())
                .year(song.getYear())
                .duration(song.getDuration())
                .build();
    }
}
