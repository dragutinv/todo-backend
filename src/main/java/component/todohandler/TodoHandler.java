package component.todohandler;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class TodoHandler {
  @PostConstruct
  public void postConstruct() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    log.info("Component started");
  }

  public static void main(String[] args) {
    SpringApplication.run(TodoHandler.class, args);
  }
}
