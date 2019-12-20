package component.todohandler;

import java.util.TimeZone;
import javax.annotation.PostConstruct;

import configuration.component.TodoHandlerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Slf4j
@Import({TodoHandlerConfiguration.class})
public class TodoApplication {
  @PostConstruct
  public void postConstruct() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    log.info("Component started");
  }

  public static void main(String[] args) {
    SpringApplication.run(TodoApplication.class, args);
  }
}
