import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime
import java.time.ZoneOffset

@MappedSuperclass
abstract class BaseTimestampEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)

    @PrePersist
    fun onCreate() {
        createdAt = LocalDateTime.now(ZoneOffset.UTC)
        updatedAt = LocalDateTime.now(ZoneOffset.UTC)
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now(ZoneOffset.UTC)
    }
}
