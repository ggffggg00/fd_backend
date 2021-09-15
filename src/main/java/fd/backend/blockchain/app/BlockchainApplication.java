package fd.backend.blockchain.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("fd.backend.blockchain.repo")
@EntityScan("fd.backend.blockchain.model")
@EnableScheduling
@ComponentScan(basePackages = {
        "fd.backend.blockchain.controller",
        "fd.backend.blockchain.service",
        "fd.backend.blockchain.app.conf"
})
@Slf4j
public class BlockchainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainApplication.class, args);
        log.info("APPLICATION STARTED");
    }

}
