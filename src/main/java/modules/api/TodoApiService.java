package modules.api;

import domain.TodoItem;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface TodoApiService {

  @GetMapping("/allTodoItems")
  ResponseEntity<List<TodoItem>> getTodoItems();

  @GetMapping("/deactivateItem")
  ResponseEntity<Boolean> deactivateItem(Integer itemId);

  @GetMapping("/deactivateItem")
  ResponseEntity<Boolean> saveItem(String todoText, List<String> categories);
}
