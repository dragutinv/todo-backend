package modules.api.dto;

import domain.TodoItem;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class TodoItemDto {
  private final Integer id;
  private final String message;
  private final List<String> categories;
  private boolean done;

  public static TodoItemDto from(TodoItem todoItem) {
    List<String> categories = new ArrayList<>();
    if (todoItem.getCategories() != null) {
      categories.addAll(
          todoItem.getCategories().stream()
              .map(x -> x.getCategoryName())
              .collect(Collectors.toList()));
    }
    return new TodoItemDto(
        todoItem.getId(), todoItem.getMessage(), categories, !todoItem.isActive());
  }
}
