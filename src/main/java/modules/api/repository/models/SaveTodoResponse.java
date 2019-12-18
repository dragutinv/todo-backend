package modules.api.repository.models;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class SaveTodoResponse {
  private final Number todoItemId;
  private final String responseMessage;
}
