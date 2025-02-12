package org.song.service;

import org.song.entity.Song;
import java.util.List;

public interface SongService {

    Long save(Song song);
    Song get(Long id);
    List<Long> delete(List<Long> ids);
    void deleteById(Long id);

}
