package dev.danilosales.bank;

import org.springdoc.core.SpringDocUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.money.MonetaryAmount;

@EnableJpaAuditing
@SpringBootApplication
public class BankApplication {

	static {
		SpringDocUtils.getConfig().replaceWithClass(MonetaryAmount.class, org.springdoc.core.converters.models.MonetaryAmount.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

}
