package ru.espada.ep.iptip.s3;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.config.S3Config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class S3Service {

    @Autowired
    private S3Config config;
    private MinioClient minioClient;

    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(config.getEndpoint_user())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
    }

    @Async
    public CompletableFuture<String> uploadPng(byte[] file, String objectName, String bucketName) {
         try (ByteArrayInputStream inputStream = new ByteArrayInputStream(file)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(getBucket(bucketName))
                            .object(objectName)
                            .stream(inputStream, file.length, -1)
                            .contentType("image/png")
                            .build()
            );
            return CompletableFuture.completedFuture(objectName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<String> getFileUrl(String bucketName, String objectName) {
        try {
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(getBucket(bucketName))
                            .object(objectName)
                            .expiry(config.getImageExpiryTime(), TimeUnit.DAYS)
                            .build()
            );
            return CompletableFuture.completedFuture(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getBucket(String bucketName) {
        bucketName = "s3elma365-" + bucketName;
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
                .bucket(bucketName)
                .build();
        try {
            if (!minioClient.bucketExists(bucketExistsArgs)) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
        return bucketName;
    }

}
