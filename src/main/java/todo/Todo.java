package todo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private String status;
    @Builder.Default
    private LocalDateTime createdTimeStamp = LocalDateTime.now(ZoneId.systemDefault());
    @Builder.Default
    private LocalDateTime updatedTimeStamp = LocalDateTime.now(ZoneId.systemDefault());

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "_user_id")
    private User user;
}