package com.erickmarques.ms_cloud_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder
				.routes()
					.route( r -> r.path("/api/clientes/**").uri("lb://ms-clientes") )
					.route( r -> r.path("/api/cliente-cartoes/**").uri("lb://ms-cartoes") )
					.route( r -> r.path("/api/cartoes/**").uri("lb://ms-cartoes") )
					.route( r -> r.path("/api/avaliacoes-credito/**").uri("lb://ms-avaliador-credito") )
				.build();
	}
}
