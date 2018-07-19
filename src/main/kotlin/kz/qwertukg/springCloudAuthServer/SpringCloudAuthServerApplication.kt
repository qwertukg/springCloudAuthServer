package kz.qwertukg.springCloudAuthServer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer

@SpringBootApplication
class SpringCloudAuthServerApplication : SpringBootServletInitializer() {
    override fun configure(app: SpringApplicationBuilder): SpringApplicationBuilder {
        return app.sources(SpringCloudAuthServerApplication::class.java)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(SpringCloudAuthServerApplication::class.java, *args)
}
