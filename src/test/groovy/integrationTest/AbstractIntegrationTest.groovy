package integrationTest

import com.kr.librarysystem.config.TestMockConfig
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@Import(TestMockConfig)
abstract class AbstractIntegrationTest extends Specification{
}
