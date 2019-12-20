package modules.api.repository.implementation;

import domain.Category;
import domain.TodoItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modules.api.repository.TodoRepository;
import modules.api.dto.TodoItemDto;
import modules.api.repository.mapper.CategoryMapper;
import modules.api.repository.mapper.TodoItemMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class TodoRepositoryJdbc implements TodoRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public Integer saveTodoItem(TodoItemDto todoItemDto) {
    try {
      String query =
          "INSERT INTO todo_item (text, createdat, active) VALUES (:todoText, now(), true) RETURNING id";

      KeyHolder keyHolder = new GeneratedKeyHolder();
      SqlParameterSource param =
          new MapSqlParameterSource().addValue("todoText", todoItemDto.getMessage());
      jdbcTemplate.update(query, param, keyHolder);

      return keyHolder.getKey().intValue();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Override
  public Boolean setStatusForTodoItem(Integer todoItemId, boolean isActive) {
    try {
      String query = "UPDATE todo_item SET active = :isActive WHERE id =:todoItemId";

      SqlParameterSource param =
          new MapSqlParameterSource()
              .addValue("isActive", isActive)
              .addValue("todoItemId", todoItemId);
      return jdbcTemplate.update(query, param) > 0;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return false;
    }
  }

  @Override
  public List<Integer> addCategories(List<String> categories) {
    try {
      List<Integer> categoriesIds = new ArrayList<>();
      for (String category : categories) {
        String query = "INSERT INTO category (category_name) VALUES (:categoryName) RETURNING id";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("categoryName", category);
        jdbcTemplate.update(query, param, keyHolder);

        categoriesIds.add(keyHolder.getKey().intValue());
      }
      return categoriesIds;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  @Override
  public List<Category> getCategories() {
    try {
      String query = "SELECT id, category_name FROM category";

      return jdbcTemplate.query(query, new CategoryMapper());

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  @Override
  public Boolean connectTodoItemAndCategories(Integer todoItemId, List<Integer> categoriesIds) {
    try {
      for (Integer categoryId : categoriesIds) {
        String query =
            "INSERT INTO todo_item_category (todo_item_id, category_id) VALUES (:todoItemId, :categoryId)";

        SqlParameterSource param =
            new MapSqlParameterSource()
                .addValue("todoItemId", todoItemId)
                .addValue("categoryId", categoryId);
        jdbcTemplate.update(query, param);
      }
      return true;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return false;
    }
  }

  @Override
  public List<TodoItem> getTodoItems() {
    try {
      List<TodoItem> listOfTodoItemsWithCategories = new ArrayList<>();

      String query = "SELECT id, text, createdat, active FROM todo_item ORDER BY id DESC";

      List<TodoItem> todoItems = jdbcTemplate.query(query, new TodoItemMapper());

      for (TodoItem todoItem : todoItems) {
        List<Category> categoriesForTodoItem = getCategoriesForTodoItem(todoItem.getId());
        listOfTodoItemsWithCategories.add(
            new TodoItem(
                todoItem.getId(),
                todoItem.getMessage(),
                categoriesForTodoItem,
                todoItem.getCreatedTime(),
                todoItem.isActive()));
      }

      return listOfTodoItemsWithCategories;

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  private List<Category> getCategoriesForTodoItem(Integer todoItemId) {
    try {
      String query =
          "SELECT id, category_name FROM category ct, todo_item_category tdc WHERE ct.id = tdc.category_id AND tdc.todo_item_id = :todoItemId";

      SqlParameterSource param = new MapSqlParameterSource().addValue("todoItemId", todoItemId);
      return jdbcTemplate.query(query, param, new CategoryMapper());

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Collections.emptyList();
    }
  }
}
