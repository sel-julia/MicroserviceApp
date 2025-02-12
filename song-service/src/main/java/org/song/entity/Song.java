package org.song.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String artist;
    @Column
    private String album;
    @Column
    private String duration;
    @Column
    private String year;

}
