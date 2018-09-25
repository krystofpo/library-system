package com.kr.librarysystem.library

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.sql.DataSource


@SpringBootTest
@ActiveProfiles("test")
class LibraryIntegrationSpec extends Specification {

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

    //todo spirng profile test, add applicaiton tes tpoo, datasource
    //todo add pord proifle, prod dataoruce
    //todo search test datasoure spring boot

}
