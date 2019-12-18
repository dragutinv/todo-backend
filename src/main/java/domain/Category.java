package domain;

import lombok.Value;

@Value
public class Category {
  private final Integer id;
  private final String categoryName;
}
