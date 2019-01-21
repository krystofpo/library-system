package com.kr.librarysystem

import com.kr.librarysystem.config.TestMockConfig
import com.kr.librarysystem.library.Library
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.sql.DataSource


@SpringBootTest
@ActiveProfiles("test")
@Import(TestMockConfig)
class SpringApplicationContextSpec extends Specification {

    @Autowired
    private ApplicationContext context

    def "context loads"() {
        expect:
        context != null
    }

    def 'datasource is test DB'() {
        expect:
        context.getBean(DataSource).getConnection().getMetaData().getURL().contains('test')

    }
}
