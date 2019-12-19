package modules.api.repository.dto;

import lombok.Value;

import java.util.List;

@Value
public class TodoItemDto {
    private final String todoText;
    private final List<String> categories;
}
