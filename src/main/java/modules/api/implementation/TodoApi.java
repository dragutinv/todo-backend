package modules.api.implementation;

import domain.TodoItem;

import java.util.Collections;
import java.util.Optional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modules.api.TodoApiService;
import modules.api.repository.TodoRepository;
import modules.api.repository.dto.TodoItemDto;
import modules.api.repository.models.DeactivateTodoResponse;
import modules.api.repository.models.SaveTodoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("api")
public class TodoApi implements TodoApiService {
  private final TodoRepository todoRepository;

  @Override
  public ResponseEntity<List<TodoItem>> getTodoItems() {
    try {
      return new ResponseEntity<>(todoRepository.getTodoItems(), HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<DeactivateTodoResponse> deactivateItem(Integer itemId) {
    try {
      return new ResponseEntity(
          new DeactivateTodoResponse(todoRepository.deactivateTodoItem(itemId), ""), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity(
          new DeactivateTodoResponse(false, "Error while deactivating todo item"),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<SaveTodoResponse> saveItem(@RequestBody TodoItemDto todoItemDto) {
    try {
      Optional<Integer> newTodoItemId = todoRepository.saveTodoItem(todoItemDto);
      return new ResponseEntity(new SaveTodoResponse(newTodoItemId.get(), ""), HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity(
          new SaveTodoResponse(-1, "Error while saving todo item"),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
