package com.borishop.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity클래스들이 BaseTimeEntity를 상속할 경우 필드들도 컬럼으로 인식하도록 해줌
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity클래스에 Auditing 기능 포함
public class BaseTimeEntity {
    @CreatedDate // Entity 생성 시간 자동 저장
    private LocalDateTime createdAt;
    @LastModifiedDate // 조회한 Entity의 값이 변경될 때 시간 자동 저장
    private LocalDateTime updatedAt;
}
