package com.ar.laboratory.filestorageservice.file.infrastructure.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/** Tests de integración del flujo de archivos con PostgreSQL real. */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.jpa.hibernate.ddl-auto=validate"})
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@DisplayName("FileController - Integration Tests")
class FileControllerIT {

    private static final String BASE = "/file-storage-service/api/v1/files";

    @Container @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @LocalServerPort private int port;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    @DisplayName("solicitar subida → completar → READY con URLs")
    void fullFlow() throws Exception {
        byte[] bytes =
                client.post()
                        .uri(BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("filename", "foto.png", "contentType", "image/png"))
                        .exchange()
                        .expectStatus()
                        .isCreated()
                        .expectBody()
                        .jsonPath("$.uploadUrl")
                        .isNotEmpty()
                        .returnResult()
                        .getResponseBodyContent();
        JsonNode ticket = objectMapper.readTree(bytes);
        String id = ticket.get("fileId").asText();

        client.post()
                .uri(BASE + "/" + id + "/complete")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("sizeBytes", 4096))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.status")
                .isEqualTo("READY")
                .jsonPath("$.downloadUrl")
                .isNotEmpty()
                .jsonPath("$.thumbnailUrl")
                .isNotEmpty();
    }

    @Test
    @DisplayName("completar un archivo inexistente → 404")
    void completeMissing() {
        client.post()
                .uri(BASE + "/" + UUID.randomUUID() + "/complete")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("sizeBytes", 1))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("solicitar subida sin filename → 400")
    void invalidRequest() {
        client.post()
                .uri(BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("contentType", "image/png"))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}
