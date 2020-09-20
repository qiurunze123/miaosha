import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class GlobalTransactionApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GlobalTransactionApp.class, args);
    }










































}
