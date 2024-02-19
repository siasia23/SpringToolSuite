package com.oracle.oBootMybatis01.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.oracle.oBootMybatis01.handler.SocketHandler;

@Configuration
@EnableWebSocket		// socket으로 인식하게 하는 annotation
public class WebSocketConfig implements WebSocketConfigurer {

	// WebSocket에서는 SocketHandler가 server 역할 함
	@Autowired
	SocketHandler socketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		// 누군가 URL에 /chating 치면 SocketHandler 발동
		registry.addHandler(socketHandler, "/chating");
		
	}

}
