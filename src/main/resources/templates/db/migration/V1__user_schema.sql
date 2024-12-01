CREATE TABLE google_user_info (
      uid BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
      created_at DATETIME NOT NULL DEFAULT UTC_TIMESTAMP,
      updated_at DATETIME NOT NULL DEFAULT UTC_TIMESTAMP ON UPDATE UTC_TIMESTAMP,
      deleted_at DATETIME NULL,
      google_id VARCHAR(50) NOT NULL,
      email VARCHAR(255) NULL,
      name VARCHAR(100) NULL,
      picture VARCHAR(255) NULL,
      locale VARCHAR(11) NULL, -- locale 크기 최적화
      CONSTRAINT pk_google_user_info PRIMARY KEY (uid),
      CONSTRAINT uc_google_user_info_googleid UNIQUE (google_id)
);

CREATE TABLE user (
      id BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
      created_at DATETIME NOT NULL DEFAULT UTC_TIMESTAMP,
      updated_at DATETIME NOT NULL DEFAULT UTC_TIMESTAMP ON UPDATE UTC_TIMESTAMP,
      deleted_at DATETIME NULL,
      email VARCHAR(255) NULL,
      CONSTRAINT pk_user PRIMARY KEY (id),
);
