package modules.api.repository.models;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class DeactivateTodoResponse {
  private final String responseMessage;
}
