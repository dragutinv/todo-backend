package configuration.component;

import configuration.infrastructure.DatabaseConfiguration;
import modules.api.TodoApiService;
import modules.api.implementation.TodoApi;
import modules.api.repository.TodoRepository;
import modules.api.repository.implementation.TodoRepositoryJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@Import({DatabaseConfiguration.class})
public class TodoHandlerConfiguration {

  @Bean
  TodoRepository todoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    return new TodoRepositoryJdbc(jdbcTemplate);
  }

  @Bean
  TodoApiService todoApiService(TodoRepository todoRepository) {
    return new TodoApi(todoRepository);
  }
}
