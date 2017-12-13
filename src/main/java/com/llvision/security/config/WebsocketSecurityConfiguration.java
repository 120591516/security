package com.llvision.security.config;

import com.llvision.security.security.AuthoritiesConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebsocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            .nullDestMatcher().authenticated()
            .simpDestMatchers("/topic/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
            .simpSubscribeDestMatchers("/user/queue/face").hasAuthority(AuthoritiesConstants.USER)
            .simpSubscribeDestMatchers("/user/queue/car").hasAuthority(AuthoritiesConstants.USER)
            .simpSubscribeDestMatchers("/user/queue/realTime").hasAuthority(AuthoritiesConstants.USER)
            .simpSubscribeDestMatchers("/user/queue/realTimeApp").hasAuthority(AuthoritiesConstants.USER)

            .simpSubscribeDestMatchers("/topic/realTime").hasAuthority(AuthoritiesConstants.ADMIN)

            // matches any destination that starts with /topic/
            // (i.e. cannot send messages directly to /topic/)
            // (i.e. cannot subscribe to /topic/messages/* to get messages sent to
            // /topic/messages-user<id>)
            .simpDestMatchers("/topic/**").authenticated()
            .simpDestMatchers("/queue/**").authenticated()
            // message types other than MESSAGE and SUBSCRIBE
            .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll()
            // catch all
            .anyMessage().denyAll();
    }

    /**
     * Disables CSRF for Websockets.
     */
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
