CREATE TABLE IF NOT EXISTS app.files (
    id            UUID         PRIMARY KEY,
    filename      VARCHAR(300) NOT NULL,
    content_type  VARCHAR(150),
    storage_key   VARCHAR(500) NOT NULL,
    thumbnail_key VARCHAR(500),
    size_bytes    BIGINT       NOT NULL DEFAULT 0,
    status        VARCHAR(20)  NOT NULL,
    owner_id      UUID,
    last_error    TEXT,
    created_at    TIMESTAMP    NOT NULL,
    updated_at    TIMESTAMP    NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_files_owner ON app.files(owner_id);
COMMENT ON TABLE app.files IS 'Metadatos de archivos (el binario vive en el object store)';
