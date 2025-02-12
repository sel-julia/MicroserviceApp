package org.song.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.song.validation.ValidDurationFormat;
import org.song.validation.ValidYear;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Size(min = 1, max = 100)
    private String artist;

    @NotNull
    @Size(min = 1, max = 100)
    private String album;


    @NotNull
    @ValidDurationFormat(timeFormat = "mm:ss", message = "Duration must be in the format MM:SS")
    private String duration;

    @NotNull
    @ValidYear(yearFormat = "YYYY", minValue = "1900", maxValue="2099", message = "Year must be in a YYYY format, between 1900-2099.")
    private String year;
}
