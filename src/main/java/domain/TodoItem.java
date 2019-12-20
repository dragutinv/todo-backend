package domain;

import java.util.List;
import lombok.Data;

@Data
public class TodoItem {
  private final Integer id;
  private final String message;
  private final List<Category> categories;
  private final long createdTime;
  private final boolean active;
}
