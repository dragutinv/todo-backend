package modules.api.repository;

import domain.TodoItem;
import java.util.List;
import modules.api.repository.models.DeactivateTodoResponse;
import modules.api.repository.models.SaveTodoResponse;
import org.springframework.http.ResponseEntity;

public interface TodoRepository {
  ResponseEntity<SaveTodoResponse> saveTodoItem(String todoText, List<String> categories);

  ResponseEntity<DeactivateTodoResponse> deactivateTodoItem(Integer todoItemId);

  ResponseEntity<List<TodoItem>> getTodoItemsByCategory(String category);
}
