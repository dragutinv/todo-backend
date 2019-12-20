package modules.api.repository;

import domain.Category;
import domain.TodoItem;
import java.util.List;

import modules.api.dto.TodoItemDto;

public interface TodoRepository {
  Integer saveTodoItem(TodoItemDto todoItemDto);

  Boolean setStatusForTodoItem(Integer todoItemId, boolean isActive);

  List<TodoItem> getTodoItems();

  List<Integer> addCategories(List<String> categories);

  List<Category> getCategories();

  Boolean connectTodoItemAndCategories(Integer todoItemId, List<Integer> categoriesIds);
}
