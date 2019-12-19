package modules.api.repository;

import domain.TodoItem;
import java.util.List;
import java.util.Optional;

import modules.api.repository.dto.TodoItemDto;
import modules.api.repository.models.DeactivateTodoResponse;
import modules.api.repository.models.SaveTodoResponse;
import org.springframework.http.ResponseEntity;

public interface TodoRepository {
  Optional<Integer> saveTodoItem(TodoItemDto todoItemDto);

  Boolean deactivateTodoItem(Integer todoItemId);

  List<TodoItem> getTodoItems();
}
