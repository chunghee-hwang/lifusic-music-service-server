package com.chung.lifusic.musicservice.service;

import com.chung.lifusic.musicservice.dto.CommonResponseDto;
import com.chung.lifusic.musicservice.dto.FileCreateResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.chung.lifusic.musicservice.common.constants.Kafka.GROUP_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final SimpMessagingTemplate messagingTemplate;
    private final AdminMusicService adminMusicService;

    @KafkaListener(topics = "CREATE_FILE_COMPLETE", groupId = GROUP_ID)
    public void consumeFileCreateComplete(ConsumerRecord<String, String> record) throws JsonProcessingException {
        FileCreateResponseDto response = new ObjectMapper().readValue(record.value(), FileCreateResponseDto.class);
        boolean isSuccess = false;
        if (response.isSuccess()) {
            try {
                adminMusicService.createMusic(response);
                isSuccess = true;
            } catch (Exception exception) {
                log.error("Fail to create music: {}", exception.getMessage());
            }
        }

        messagingTemplate.convertAndSend(
                "/topic/post/admin/music/" + response.getRequestUserId(),
                CommonResponseDto.builder().success(isSuccess).build());
    }
}
