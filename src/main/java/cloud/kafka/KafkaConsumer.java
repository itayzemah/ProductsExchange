package cloud.kafka;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cloud.boundaries.ExchangeBoundary;
import cloud.logic.ExchangeService;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaConsumer {
	private ExchangeService exchangeService;
	
	@Bean
	public Consumer<ExchangeBoundary> recieveAndHandleExchangeBoundary(){
		return r->exchangeService.create(r);
	}
}
