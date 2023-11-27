package todo;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/all")
    public ResponseEntity<TodoResponse> getAllTodosByUser() {
        return ResponseEntity.ok(todoService.getAllTodosByUser());
    }
    @PostMapping("/create")
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest todoRequest) {
        return ResponseEntity.ok(todoService.createTodo(todoRequest));
    }
    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<TodoResponse> deleteTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.deleteTodoById(id));
    }
}
