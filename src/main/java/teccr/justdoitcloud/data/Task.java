package teccr.justdoitcloud.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@Table("tasks")
public class Task {
    @Id
    private final Long id;
    @Size(min=3, message = "Descripcion debe tener al menos 3 caracteres")
    private final String description;
    @Column("created_at")
    private final LocalDateTime createdAt;
    private final LocalDate deadline;
    @NotNull
    private Status status;

    @Column("user_id")
    private Long userId;

    // se tuvo que insertar constructor, para poner establecer una propiedad setStatus
    // sobre estatus, para poderlo cambiar una tarea de estatus.
    public Task(Long id,
                String description,
                LocalDateTime createdAt,
                LocalDate deadline,
                Status status) {

        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.status = status;
    }

    public enum Status {
        PENDING,
        INPROGRESS,
        DONE
    }
}
