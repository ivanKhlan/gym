package ua.vixdev.gym.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "folder_types", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class FolderTypes {

    @Id
    @SequenceGenerator(name = "folder_types_id_seq", sequenceName = "folder_types_id_seq")
    @GeneratedValue(generator = "folder_types_id_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(max = 70, message = "Title length should be less then 70 symbols length.")
    private String title;

    private boolean visible;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "folder")
    private List<Files> files;
}
