package modules.api.repository.mapper;

import domain.TodoItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TodoItemMapper implements RowMapper<TodoItem> {
  @Override
  public TodoItem mapRow(ResultSet resultSet, int i) throws SQLException {
    return new TodoItem(
        resultSet.getInt("id"),
        resultSet.getString("text"),
        null,
        resultSet.getDate("createdat").getTime(),
        resultSet.getBoolean("isActive"));
  }
}
