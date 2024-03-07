package ua.vixdev.gym.entity;

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
@Table(name = "files", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Files {

    @Id
    @SequenceGenerator(name = "files_id_seq", sequenceName = "files_id_seq")
    @GeneratedValue(generator = "files_id_seq", strategy = GenerationType.SEQUENCE)
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
    private FolderTypes folder;
}
