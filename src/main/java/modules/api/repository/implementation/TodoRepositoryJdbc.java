package modules.api.repository.implementation;

import domain.TodoItem;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modules.api.repository.TodoRepository;
import modules.api.repository.mapper.TodoItemMapper;
import modules.api.repository.models.DeactivateTodoResponse;
import modules.api.repository.models.SaveTodoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class TodoRepositoryJdbc implements TodoRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public ResponseEntity<SaveTodoResponse> saveTodoItem(String todoText, List<String> categories) {
    try {
      String query = "INSERT INTO todo_item (text) VALUES (:todoText)";

      KeyHolder holder = new GeneratedKeyHolder();
      SqlParameterSource param = new MapSqlParameterSource().addValue("todoText", todoText);
      jdbcTemplate.update(query, param, holder);

      return new ResponseEntity(new SaveTodoResponse(holder.getKey(), ""), HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity(
          new SaveTodoResponse(-1, "Error while saving todo item"),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<DeactivateTodoResponse> deactivateTodoItem(Integer todoItemId) {
    try {
      String query = "UPDATE todo_item SET active = 0 WHERE id =:todoItemId";

      SqlParameterSource param = new MapSqlParameterSource().addValue("todoItemId", todoItemId);
      jdbcTemplate.update(query, param);

      return new ResponseEntity(new DeactivateTodoResponse(""), HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity(
          new DeactivateTodoResponse("Error while deactivating todo item"),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<List<TodoItem>> getTodoItemsByCategory(String category) {
    try {
      String query = "SELECT id, text, createdat, active FROM todo_item";

      List<TodoItem> todoItemList = jdbcTemplate.query(query, new TodoItemMapper());

      return new ResponseEntity(todoItemList, HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return new ResponseEntity(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
