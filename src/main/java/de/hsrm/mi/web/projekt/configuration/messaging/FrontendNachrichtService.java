package de.hsrm.mi.web.projekt.configuration.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class FrontendNachrichtService {
    private static final Logger logger = LoggerFactory.getLogger(FrontendNachrichtService.class);

    private final SimpMessagingTemplate messagingTemplate;

    public FrontendNachrichtService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendEvent(FrontendNachrichtEvent ev) {
        String destination = "/topic/quiz";

        messagingTemplate.convertAndSend(destination, ev);

        logger.info("FrontendNachrichtEvent sent to destination '{}': {}", destination, ev);
    }
}
