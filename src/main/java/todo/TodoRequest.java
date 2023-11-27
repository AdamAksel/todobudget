package todo;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import user.User;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class TodoRequest {
    @NotBlank(message = "Title cannot be null or empty")
    private String title;
    @NotBlank(message = "Description cannot be null or empty")
    private String description;
    @NotNull(message = "DueDate cannot be null")
    @Future(message = "Date must be in the future or present")
    private LocalDate dueDate;
    @ColumnDefault(value = "medium")
    private String priority;
    private String status;
}
