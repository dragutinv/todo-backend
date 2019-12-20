package modules.api.repository.models;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class StatusChangeForTodoItemResponse {
  private final Boolean result;
  private final String responseMessage;
}
