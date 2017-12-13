package com.llvision.security.web.websocket;

import com.llvision.security.domain.User;
import com.llvision.security.security.SecurityUtils;
import com.llvision.security.service.UserService;
import com.llvision.security.service.dto.RecognitionRecordDTO;
import com.llvision.security.service.dto.RecognitionRecordOutputDTO;
import com.llvision.security.service.recognition.RecognitionService;
import com.llvision.security.service.recognition.RecognitionServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeService {
    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private UserService userService;
    @Autowired
    private RecognitionServiceFactory recognitionServiceFactory;
    @Autowired
    private RealTimeService realTimeService;

    // 同步到客户端
    @SubscribeMapping("/topic/realTimeFace")
    public void sendrecognitionRecordOutputDTO(@Payload RecognitionRecordOutputDTO recognitionRecordOutputDTO) {
        if(recognitionRecordOutputDTO != null){
            // 区分逻辑进行定点推送
            String username = SecurityUtils.getCurrentUserLogin();
            if (!"".equals(username)) {
                for (User user : SecurityUtils.getLoginAdminUsers()) {
                    if (username.equals(user.getLogin())) continue;
                    template.convertAndSendToUser(user.getLogin(), "/queue/realTime", recognitionRecordOutputDTO);
                }
                if (recognitionRecordOutputDTO.getType() == 1) {
                    template.convertAndSendToUser(username, "/queue/car", recognitionRecordOutputDTO);
                } else {
                    template.convertAndSendToUser(username, "/queue/face", recognitionRecordOutputDTO);
                }
            }
        }
    }

    // 同步到客户端
    @SubscribeMapping("/topic/realTimeApp")
    public void sendrecognition(@Payload RecognitionRecordDTO recognitionRecordDTO){
        if(recognitionRecordDTO != null){
            String username = SecurityUtils.getCurrentUserLogin();
            if (!"".equals(username)) {
                try{
                    RecognitionService recognitionService = recognitionServiceFactory.getServiceByType(recognitionRecordDTO.getType());
                    User user = userService.getUserWithAuthorities();
                    RecognitionRecordOutputDTO recognitionRecordOutputDTO = recognitionService.insert(recognitionRecordDTO, user);
                    //实时同步到前端
                    try{
                        realTimeService.sendrecognitionRecordOutputDTO(recognitionRecordOutputDTO);
                    }catch (NullPointerException e){
                        log.error(e.getLocalizedMessage());
                        log.error("推送到前端失败");
                    }
                    template.convertAndSendToUser(username, "/queue/realTimeApp", recognitionRecordOutputDTO);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
