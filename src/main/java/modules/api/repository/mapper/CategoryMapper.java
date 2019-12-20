package modules.api.repository.mapper;

import domain.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
  @Override
  public Category mapRow(ResultSet resultSet, int i) throws SQLException {
    return new Category(
        resultSet.getInt("id"),
        resultSet.getString("category_name"));
  }
}
