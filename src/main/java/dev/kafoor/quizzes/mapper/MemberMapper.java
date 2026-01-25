package dev.kafoor.quizzes.mapper;

import dev.kafoor.quizzes.dto.v1.response.MemberResponse;
import dev.kafoor.quizzes.entity.MemberEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    default MemberResponse toMemberResponse(MemberEntity memberEntity){
        return MemberResponse.builder()
                .id(memberEntity.getId())
                .userId(memberEntity.getUserId())
                .createdAt(memberEntity.getCreatedAt())
                .build();
    }
}
