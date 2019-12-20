package modules.api;

import java.util.List;

import modules.api.dto.TodoItemDto;
import modules.api.repository.models.StatusChangeForTodoItemResponse;
import modules.api.repository.models.SaveTodoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@CrossOrigin
public interface TodoApiService {

  @GetMapping("/allTodoItems")
  ResponseEntity<List<TodoItemDto>> getTodoItems();

  @GetMapping("/setStatusForItem")
  ResponseEntity<StatusChangeForTodoItemResponse> setStatusForTodoItem(@RequestParam Integer itemId, @RequestParam boolean isActive);

  @PostMapping("/saveItem")
  ResponseEntity<SaveTodoResponse> saveItem(@RequestBody TodoItemDto todoItemDto);

  @GetMapping("/categories")
  ResponseEntity<List<String>> getCategories();
}
