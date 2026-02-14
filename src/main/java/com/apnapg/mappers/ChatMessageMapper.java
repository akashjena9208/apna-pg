//package com.apnapg.mappers;
//
//import com.apnapg.config.CentralMapperConfig;
//import com.apnapg.dto.chat.ChatMessageResponseDTO;
//import com.apnapg.entity.ChatMessage;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(config = CentralMapperConfig.class)
//public interface ChatMessageMapper {
//
//    @Mapping(source = "sender.id", target = "senderId")
//    @Mapping(source = "recipient.id", target = "recipientId")
//    ChatMessageResponseDTO toResponseDTO(ChatMessage message);
//}
package com.apnapg.mappers;

import com.apnapg.config.CentralMapperConfig;
import com.apnapg.dto.chat.ChatMessageResponseDTO;
import com.apnapg.entity.ChatMessage;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface ChatMessageMapper {

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "recipientId", source = "recipient.id")
    ChatMessageResponseDTO toResponseDTO(ChatMessage message);
}
