package modules.api.repository.implementation;

import domain.TodoItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modules.api.repository.TodoRepository;
import modules.api.repository.dto.TodoItemDto;
import modules.api.repository.mapper.TodoItemMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class TodoRepositoryJdbc implements TodoRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public Optional<Integer> saveTodoItem(TodoItemDto todoItemDto) {
    try {
      String query = "INSERT INTO todo_item (text) VALUES (:todoText)";

      KeyHolder holder = new GeneratedKeyHolder();
      SqlParameterSource param = new MapSqlParameterSource().addValue("todoText", todoItemDto.getTodoText());
      jdbcTemplate.update(query, param, holder);
      return Optional.of(holder.getKey().intValue());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Optional.empty();
    }
  }

  @Override
  public Boolean deactivateTodoItem(Integer todoItemId) {
    try {
      String query = "UPDATE todo_item SET active = 0 WHERE id =:todoItemId";

      SqlParameterSource param = new MapSqlParameterSource().addValue("todoItemId", todoItemId);
      return jdbcTemplate.update(query, param) > 0;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return false;
    }
  }

  @Override
  public List<TodoItem> getTodoItems() {
    try {
      String query = "SELECT id, text, createdat, active FROM todo_item";

      return jdbcTemplate.query(query, new TodoItemMapper());

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Collections.emptyList();
    }
  }
}
