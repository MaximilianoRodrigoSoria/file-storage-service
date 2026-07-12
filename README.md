<!-- banner-badges -->
<p align="center">
  <a href="https://www.linkedin.com/in/soriamaximilianorodrigo/" target="_blank" rel="noopener noreferrer">
    <img width="100%" src="docs/img/banner.gif" alt="File Storage + CDN — Maximiliano Rodrigo Soria">
  </a>
</p>

<p align="center">
  <a href="LICENSE"><img src="https://img.shields.io/github/license/MaximilianoRodrigoSoria/file-storage-service?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="License"></a>
  <img src="https://img.shields.io/github/last-commit/MaximilianoRodrigoSoria/file-storage-service?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="Last commit">
  <img src="https://img.shields.io/github/repo-size/MaximilianoRodrigoSoria/file-storage-service?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="Repo size">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-06C69C?style=flat-square&labelColor=1A1C1F&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/S3_/_MinIO-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=amazons3&logoColor=white" alt="S3_/_MinIO">
  <img src="https://img.shields.io/badge/PostgreSQL-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/CDN-•-06C69C?style=flat-square&labelColor=1A1C1F" alt="CDN">
  <img src="https://img.shields.io/badge/Docker-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=docker&logoColor=white" alt="Docker">
</p>

# File Storage + CDN

Almacenamiento de archivos donde el cliente pide una presigned URL y sube directo al object store; al subir, un pipeline por eventos genera variantes y una CDN sirve el contenido.

> Proyecto de portafolio backend. Sigue el estandar de **arquitectura hexagonal (Ports & Adapters)**, Java 21 y Spring Boot, con quality gates (Spotless, Checkstyle, PMD, SpotBugs, ArchUnit), testing con Testcontainers y observabilidad (Micrometer + Prometheus).

## Caracteristicas

- Presigned URLs de subida y descarga (el backend no toca el binario)
- Upload directo a object store S3-compatible (MinIO en local)
- Pipeline por eventos: subida -> cola -> worker de variantes
- Generacion de miniaturas / redimensionado de imagenes
- Maquina de estados del archivo (PENDING -> READY)
- Entrega por CDN con invalidacion de cache
- Validaciones de content-type, tamano y expiracion
- Scoping por propietario

## Stack

Java 21 · S3 / MinIO · PostgreSQL · CDN · Docker · Gradle · Flyway · Docker · JUnit 5 · Testcontainers

## Arquitectura

Organizado por **feature** en capas `domain -> application -> infrastructure`, con la regla de dependencia verificada por ArchUnit. La logica de negocio (dominio y casos de uso) no depende de framework ni de infraestructura; los adaptadores (web, persistencia, mensajeria) implementan puertos definidos por la aplicacion.

## API

Contexto `/file-storage-service`. `POST /api/v1/files` devuelve una **presigned URL** de subida; `POST /api/v1/files/{id}/complete` confirma la subida y dispara el post-proceso (miniatura); `GET /api/v1/files/{id}` devuelve el estado y las URLs de descarga.

## Estado

✅ Nucleo funcional implementado: presigned URLs (stub S3/MinIO), maquina de estados del archivo (PENDING→UPLOADED→PROCESSING→READY), post-proceso que genera una miniatura, y consulta con URLs de descarga. Persistencia JPA/PostgreSQL + migracion Flyway, tests (unit + Testcontainers). Capa siguiente: object store real (MinIO/S3), post-proceso **asincrono por eventos** (cola/worker) y entrega por CDN.

---

<p align="center">
  <strong>Maximiliano Rodrigo Soria</strong><br>
  <a href="https://www.linkedin.com/in/soriamaximilianorodrigo/">LinkedIn</a> · <a href="mailto:maximilianorodrigosoria@gmail.com">maximilianorodrigosoria@gmail.com</a>
</p>
