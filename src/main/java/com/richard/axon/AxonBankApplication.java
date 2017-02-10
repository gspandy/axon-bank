package com.richard.axon;

import com.richard.axon.core.api.account.AccountAggregate;
import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.transaction.TransactionManager;

//@EnableAxon --spring configuration
@SpringBootApplication
public class AxonBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxonBankApplication.class, args);
    }

    /**
     *@Bean
     * public EventStore eventStorageEngine(){
     *     return new InMemoryEventStorageEngine
     * }
     *
     *
     * @Autowired public CommandLineRunner commandLineRunner(CommandBus commandBus){
    return args -> {
    commandBus().dispatch(asCommandMessage(new CreateAccountCommand("321", 500), new CommandCallback(success -> {

    }, failure(error) -> {

    });
    commandBus().dispatch(asCommandMessage(new CreateAccountCommand("321", 250)));
    commandBus().dispatch(asCommandMessage(new CreateAccountCommand("321", 251)));
    };
    }
     */


    /**
     * Non Spring Implementation
     */
    /*public void run(){
		Configuration config = DefaultConfigurer.defaultConfiguration()
				.configureAggregate(AccountAggregate.class)
				.configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
				.buildConfiguration();

		config.start();


		config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("321", 500)));
		config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("321", 250)));
		config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("321", 251)));
	}*/


    //How to declare your own repository.
    @Bean
    public Repository<AccountAggregate> jpaAccountRepository(EventBus eventBus) {
        return new GenericJpaRepository<>(entityManagerProvider(), AccountAggregate.class, eventBus);
    }

    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Bean
    public TransactionManager axonTransactionManager(PlatformTransactionManager tx) {
        return (TransactionManager) new SpringTransactionManager(tx);
    }

}
