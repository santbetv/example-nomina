package com.sbvdeveloper.poc_nomina.config;

import cl.ccla.app.lib_core_pdf_generator.dto.StorageDTO;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Configuration
public class BucketConfig {
    @Value("${google.cloud.bucketName}")
    private String googleCloudBucketName;
    @Value("${google.cloud.projectId}")
    private String googleCloudProjectId;
    @Value("${google.cloud.credentials.path}")
    private String credentialString;

    @Bean
    public StorageDTO initStorageBucket() throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(credentialString);
        ByteArrayInputStream credentialsStream = new ByteArrayInputStream(decodedBytes);
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        return new StorageDTO(googleCloudBucketName, googleCloudProjectId, credentials);
    }
}
