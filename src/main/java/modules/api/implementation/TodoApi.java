package modules.api.implementation;

import domain.Category;
import domain.TodoItem;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modules.api.TodoApiService;
import modules.api.repository.TodoRepository;
import modules.api.dto.TodoItemDto;
import modules.api.repository.models.StatusChangeForTodoItemResponse;
import modules.api.repository.models.SaveTodoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class TodoApi implements TodoApiService {
  private final TodoRepository todoRepository;

  @Override
  public ResponseEntity<List<TodoItemDto>> getTodoItems() {
    try {
      List<TodoItem> todoItems = todoRepository.getTodoItems();

      List<TodoItemDto> todoItemDtos = todoItems.stream().map(x -> TodoItemDto.from(x)).collect(Collectors.toList());

      return new ResponseEntity<>(todoItemDtos, HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<StatusChangeForTodoItemResponse> setStatusForTodoItem(Integer itemId, boolean isActive) {
    try {
      return new ResponseEntity<>(
              new StatusChangeForTodoItemResponse(todoRepository.setStatusForTodoItem(itemId, isActive), ""), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
              new StatusChangeForTodoItemResponse(false, "Error while changing status of todo item"),
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<SaveTodoResponse> saveItem(@RequestBody TodoItemDto todoItemDto) {
    try {
      Integer todoItemId = todoRepository.saveTodoItem(todoItemDto);
      List<Integer> categoriesIds = todoRepository.addCategories(todoItemDto.getCategories());
      todoRepository.connectTodoItemAndCategories(todoItemId, categoriesIds);

      return new ResponseEntity<>(new SaveTodoResponse(""), HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity<>(
          new SaveTodoResponse( "Error while saving todo item"),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<List<String>> getCategories() {
    try {
      List<Category> categories = todoRepository.getCategories();
      List<String> categoryNames = categories.stream().map(x -> x.getCategoryName()).collect(Collectors.toList());

      return new ResponseEntity<>(categoryNames, HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity<>(
              Collections.emptyList(),
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
