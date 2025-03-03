package com.sbvdeveloper.poc_nomina.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class ImageUtils {

    public static BufferedImage loadImage(String imagePath) throws IOException {
        try {
            InputStream imageFile = Objects.requireNonNull(ImageUtils.class.getResourceAsStream(imagePath), "No se pudo encontrar la imagen en el directorio de recursos.");
            return Objects.requireNonNull(ImageIO.read(imageFile), "Error al cargar la imagen.");
        } catch (NullPointerException | IOException e) {
            log.error("No se encontro el documento : {0}", e.getMessage());
            throw new RuntimeException("Error al cargar la imagen: " + e.getMessage());
        }
    }
}
