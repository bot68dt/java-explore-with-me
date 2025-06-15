package ru.practicum.explore.location.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationWithRadiusDto;
import ru.practicum.explore.location.model.Location;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {

    public static LocationDto mapToAdLocationDto(Location adLocation) {
        return new LocationDto(adLocation.getId(), adLocation.getTown(), adLocation.getStreet(), adLocation.getPlace(), adLocation.getLat(), adLocation.getLon());
    }

    public static LocationWithRadiusDto mapToAdLocationWithRadiusDto(Object[] obj) {
        return new LocationWithRadiusDto((String) obj[0], (String) obj[1], (String) obj[2], (Float) obj[3], (Float) obj[4], (Double) obj[5]);
    }

    public static List<LocationWithRadiusDto> mapToAdLocationWithRadiusDto(Iterable<Object[]> objects) {
        List<LocationWithRadiusDto> result = new ArrayList<>();

        for (Object[] object : objects) {
            result.add(mapToAdLocationWithRadiusDto(object));
        }

        return result;
    }

    public static List<LocationDto> mapToAdLocationDto(Iterable<Location> adLocation) {
        List<LocationDto> result = new ArrayList<>();

        for (Location adLocation1 : adLocation) {
            result.add(mapToAdLocationDto(adLocation1));
        }

        return result;
    }

    public static Location mapToAdLocation(LocationDto adLocationDto) {
        Location adLocation = new Location();
        adLocation.setTown(adLocationDto.getTown());
        adLocation.setStreet(adLocationDto.getStreet());
        adLocation.setPlace(adLocationDto.getPlace());
        adLocation.setLat(adLocationDto.getLat());
        adLocation.setLon(adLocationDto.getLon());
        return adLocation;
    }

    public static List<Location> mapToAdLocation(Iterable<LocationDto> adLocationDtos) {
        List<Location> result = new ArrayList<>();

        for (LocationDto adLocation : adLocationDtos) {
            result.add(mapToAdLocation(adLocation));
        }

        return result;
    }

    public static Location changeAdLocation(Location adLocation, LocationDto adLocationDto) {
        Location adLocation1 = new Location();
        adLocation1.setId(adLocation.getId());
        if (!adLocationDto.getTown().equals("null")) adLocation1.setTown(adLocationDto.getTown());
        else adLocation1.setTown(adLocation.getTown());
        if (!adLocationDto.getStreet().equals("null")) adLocation1.setStreet(adLocationDto.getStreet());
        else adLocation1.setStreet(adLocation.getStreet());
        if (!adLocationDto.getPlace().equals("null")) adLocation1.setPlace(adLocationDto.getPlace());
        else adLocation1.setPlace(adLocation.getPlace());
        if (!adLocationDto.getLat().equals(0f)) adLocation1.setLat(adLocationDto.getLat());
        else adLocation1.setLat(adLocation.getLat());
        if (!adLocationDto.getLon().equals(0f)) adLocation1.setLon(adLocationDto.getLon());
        else adLocation1.setLon(adLocation.getLon());
        return adLocation1;
    }

    public static byte[] getMap(Collection<Location> adLocations) throws IOException {
        BufferedImage image = ImageIO.read(new File("7JvahrDCkMA.png"));

        Graphics g = image.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        List<Float> result;
        for (Location adLocation : adLocations) {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.blue);
            result = coordinate(adLocation.getLat(), adLocation.getLon());
            g2.drawString(adLocation.getPlace() + " (" + adLocation.getLat() + "," + adLocation.getLon() + ")", result.getFirst(), result.getLast());
            g2.drawOval(Math.round(result.getFirst()), Math.round(result.getLast()), 4, 4);
        }

        g2.drawImage(image, null, null);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();

        final byte[] result1 = baos.toByteArray();
        baos.close();

        return result1;
    }

    public static byte[] getMapWithRadius(Float lat, Float lon, Collection<LocationWithRadiusDto> adLocations) throws IOException {
        BufferedImage image = ImageIO.read(new File("7JvahrDCkMA.png"));

        Graphics g = image.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        List<Float> result = coordinate(lat, lon);
        List<Float> result1;
        for (LocationWithRadiusDto adLocation : adLocations) {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.green);
            result1 = coordinate(adLocation.getLat(), adLocation.getLon());

            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(adLocation.getPlace() + " (" + adLocation.getLat() + "," + adLocation.getLon() + "), " + adLocation.getRadius().intValue() + "KM", result1.getFirst(), result1.getLast());
            g2.drawOval(Math.round(result1.getFirst()), Math.round(result1.getLast()), 4, 4);
            g2.drawLine(result.getFirst().intValue(), result.getLast().intValue(), result1.getFirst().intValue(), result1.getLast().intValue());
        }

        g2.drawImage(image, null, null);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();

        final byte[] result2 = baos.toByteArray();
        baos.close();

        return result2;
    }

    public static List<Float> coordinate(Float lat, Float lon) {
        lat = 683.0f + lat * 7.58888f;
        lon = 384.0f - lon * 2.13333f;
        return List.of(lat, lon);
    }
}