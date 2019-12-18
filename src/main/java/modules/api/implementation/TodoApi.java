package modules.api.implementation;

import domain.TodoItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modules.api.TodoApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Slf4j
@Controller
public class TodoApi implements TodoApiService {

  @Override
  public ResponseEntity<List<TodoItem>> getTodoItems() {
    return null;
  }

  @Override
  public ResponseEntity<Boolean> deactivateItem(Integer itemId) {
    return null;
  }

  @Override
  public ResponseEntity<Boolean> saveItem(String todoText, List<String> categories) {
    return null;
  }
}
