package ru.practicum.explore.library.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.practicum.explore.library.dto.AdLocationDto;
import ru.practicum.explore.library.mapper.LocationMapper;
import ru.practicum.explore.library.model.AdLocation;
import ru.practicum.explore.library.service.AdLocationService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
@Validated
public class LocationController {

    private final AdLocationService adLocationService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @GetMapping("/location")
    public ResponseEntity<Collection<AdLocationDto>> getLocations() {
        log.info("Request to get locations received.");
        return ResponseEntity.ok().body(adLocationService.getLocations());
    }

    @PostMapping("/location")
    public ResponseEntity<AdLocationDto> createAdLocation(@RequestBody AdLocationDto adLocationDto) {
        log.info("Request to create new location received:");
        AdLocation adLocation = adLocationService.addAdLocation(adLocationDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(adLocation.getId()).toUri();
        log.info("New uriStatistics created with ID {}", adLocation.getId());
        return ResponseEntity.created(location).body(LocationMapper.mapToAdLocationDto(adLocation));
    }

    @GetMapping("/local")
    public ResponseEntity<Object> getL() throws IOException {
        log.info("Request to get locations received.");
        final byte[] image = getImage();
        return ResponseEntity.ok().header("Content-Type", "image/png").header("Content-Length", String.valueOf(image.length))
                .body(image);
    }

    public byte[] getImage() throws IOException {
        final int width = 1366;
        final int height = 768;
        //final BufferedImage image = new BufferedImage( width, height, TYPE_INT_RGB );
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Dim\\IdeaProjects\\java-explore-with-me\\7JvahrDCkMA.png"));

        Graphics g = image.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.black);
        g2.drawRect(10,10,480,480);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.blue);
        g2.drawString("Hockey ("+250+","+250+")", 250, 250);
        g2.drawOval(250,250,5,5);
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.blue);
        g2.drawOval(250-380/2,250-380/2,380,380);

        final Graphics2D graphics = g2;
        graphics.drawImage( image, null, null );

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( image, "png", baos );
        baos.flush();

        final byte[] result = baos.toByteArray();
        baos.close();

        return result;
    }
}