package kz.qwertukg.springCloudAuthServer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpringCloudAuthServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(SpringCloudAuthServerApplication::class.java, *args)
}
