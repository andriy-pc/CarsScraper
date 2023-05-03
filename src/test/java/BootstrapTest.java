import org.automotive.configuration.ApplicationConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class})
public class BootstrapTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test() {
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
    }
}
