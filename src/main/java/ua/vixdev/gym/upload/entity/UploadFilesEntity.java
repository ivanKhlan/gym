package ua.vixdev.gym.upload.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
public class UploadFilesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255, message = "File name should be less than 255 characters.")
    private String name;
    private boolean visible;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "folder_id", referencedColumnName = "id")
    private FolderTypesEntity folder;

    public String getFullPath() {
        return folder.getTitle() + name;
    }
}
