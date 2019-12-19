package modules.api;

import domain.TodoItem;
import java.util.List;

import modules.api.repository.dto.TodoItemDto;
import modules.api.repository.models.DeactivateTodoResponse;
import modules.api.repository.models.SaveTodoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TodoApiService {

  @GetMapping("/allTodoItems")
  ResponseEntity<List<TodoItem>> getTodoItems();

  @GetMapping("/deactivateItem")
  ResponseEntity<DeactivateTodoResponse> deactivateItem(Integer itemId);

  @PostMapping("/saveItem")
  ResponseEntity<SaveTodoResponse> saveItem(@RequestBody TodoItemDto todoItemDto);
}
