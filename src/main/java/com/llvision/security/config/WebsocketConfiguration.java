package com.llvision.security.config;

import com.llvision.security.domain.Authority;
import com.llvision.security.domain.User;
import com.llvision.security.security.AuthoritiesConstants;
import com.llvision.security.security.SecurityUtils;
import com.llvision.security.service.UserService;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    private final Logger log = LoggerFactory.getLogger(WebsocketConfiguration.class);

    public static final String IP_ADDRESS = "IP_ADDRESS";

    private final JHipsterProperties jHipsterProperties;

    private final UserService userService;

    public WebsocketConfiguration(JHipsterProperties jHipsterProperties,UserService userService) {
        this.jHipsterProperties = jHipsterProperties;
        this.userService = userService;

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 订阅模式通道
        config.enableSimpleBroker("/topic", "/queue");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String[] allowedOrigins = Optional.ofNullable(jHipsterProperties.getCors().getAllowedOrigins()).map(origins -> origins.toArray(new String[0])).orElse(new String[0]);
        // 注册一个webSocket通道进行用户跟踪
        registry.addEndpoint("/websocket/tracker")
            .setHandshakeHandler(new DefaultHandshakeHandler() {
                @Override
                protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                    Principal principal = request.getPrincipal();
                    if (principal == null) {
                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
                        principal = new AnonymousAuthenticationToken("WebsocketConfiguration", "anonymous", authorities);
                    }
                    return principal;
                }
            })
            .setAllowedOrigins(allowedOrigins)
            .withSockJS()
            .setInterceptors(httpSessionHandshakeInterceptor());

        // 注册一个webSocket 通道 进行实时获取用户行为数据,比如:人像识别,车牌识别
        registry.addEndpoint("/websocket/realTime")
            .setHandshakeHandler(new DefaultHandshakeHandler() {
                @Override
                protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                    Principal principal = request.getPrincipal();
                    if (principal == null) {
                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
                        principal = new AnonymousAuthenticationToken("WebsocketConfiguration", "anonymous", authorities);
                    }
                    return principal;
                }
            })
            .setAllowedOrigins(allowedOrigins)
            .withSockJS()
            .setInterceptors(handsUserInterceptor());
    }

    @Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
        return new HandshakeInterceptor() {

            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                    attributes.put(IP_ADDRESS, servletRequest.getRemoteAddress());
                }
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
            }
        };
    }

    @Bean
    public HandshakeInterceptor handsUserInterceptor() {

        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> map) throws Exception {
                if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                    Principal principal = request.getPrincipal();
                    User user= userService.getUserWithAuthoritiesByLogin(principal.getName()).get();
                    for (Authority authority : user.getAuthorities()) {
                        if ("ROLE_ADMIN".equals(authority.getName())){
                            SecurityUtils.getLoginAdminUsers().add(user);
                            break;
                        }
                    }
                }
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

            }
        };
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setSendBufferSizeLimit(Integer.MAX_VALUE / 1000);
        registration.setMessageSizeLimit(Integer.MAX_VALUE / 1000);
        registration.setSendTimeLimit(Integer.MAX_VALUE / 1000);
    }

}
