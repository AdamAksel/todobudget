package todo;

import exceptions.ResourceNotFoundException;
import exceptions.UserNotAuthorizedException;
import user.User;
import utils.AuthenticationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public TodoResponse createTodo(TodoRequest todoRequest) throws DateTimeParseException {
        User user = AuthenticationValidator.getAuthenticatedUser();
        Todo todo = Todo.builder()
                .title(todoRequest.getTitle())
                .description(todoRequest.getDescription())
                .dueDate(todoRequest.getDueDate())
                .priority(todoRequest.getPriority())
                .status(todoRequest.getStatus())
                .user(user)
                .build();

        todoRepository.save(todo);
        return TodoResponse.builder().todo(todo).build();
    }

    public TodoResponse getAllTodosByUser() throws ResourceNotFoundException {
        User user = AuthenticationValidator.getAuthenticatedUser();
        List<Todo> todos = todoRepository.findAllByUser(user);

        if (todos.isEmpty()) {
            throw new ResourceNotFoundException("No todos found for the user");
        }
        return TodoResponse.builder()
                .todoList(todos)
                .build();
    }

    public TodoResponse getTodoById(Long id) throws UserNotAuthorizedException, ResourceNotFoundException {
        User user = AuthenticationValidator.getAuthenticatedUser();

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No todos found with ID: " + id));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new UserNotAuthorizedException("Unauthorized to get todo with ID: " + id);
        }
        return TodoResponse.builder()
                .todo(todo)
                .build();
    }


    public TodoResponse deleteTodoById(Long id) throws ResourceNotFoundException, UserNotAuthorizedException {
        User user = AuthenticationValidator.getAuthenticatedUser();

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No todos found with ID: " + id));


        if (!todo.getUser().getId().equals(user.getId())) {
            throw new UserNotAuthorizedException("No authorization to delete todo with ID: " + id);
        }

        todoRepository.deleteById(id);
        return TodoResponse.builder()
                .message("Successfully deleted todo with ID: " + id)
                .todo(todo)
                .build();
    }
}
