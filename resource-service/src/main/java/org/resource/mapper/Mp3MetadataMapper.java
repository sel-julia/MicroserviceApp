package org.resource.mapper;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.resource.dto.SongDTO;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.*;

@Service
public class Mp3MetadataMapper {

    public SongDTO mapToSong(byte[] fileData, Long resourceId) throws IOException, TikaException, SAXException {
        File tempFile = File.createTempFile("temp-", ".mp3");
        try(FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
            fileOutputStream.write(fileData);
            fileOutputStream.flush();
        }

        Metadata metadata = new Metadata();
        try (InputStream input = new FileInputStream(tempFile)) {
            BodyContentHandler handler = new BodyContentHandler();
            AutoDetectParser mp3Parser = new AutoDetectParser();
            mp3Parser.parse(input, handler, metadata);
        }

        return SongDTO.builder()
                .id(resourceId)
                .name(metadata.get("dc:title"))
                .album(metadata.get("xmpDM:album"))
                .artist(metadata.get("xmpDM:artist"))
                .duration(convertSecondsToDuration(metadata.get("xmpDM:duration")))
                .year(metadata.get("xmpDM:releaseDate"))
                .build();

    }

    private static String convertSecondsToDuration(String time) {
        int totalTime = (int)Double.parseDouble(time);
        int seconds = totalTime % 60;
        int minutes = totalTime / 60;

        String secondsStr = convertWithLeadingZero(seconds);
        String minutesStr = convertWithLeadingZero(minutes);

        return minutesStr + ":" + secondsStr;
    }

    private static String convertWithLeadingZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

}
