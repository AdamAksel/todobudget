package todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponse {
    private boolean errorOccurred;
    private String message;
    private Todo todo;
    private List<Todo> todoList;
}
