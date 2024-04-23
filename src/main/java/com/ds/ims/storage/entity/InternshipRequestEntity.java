package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.InternshipRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "internship_request")
public class InternshipRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internship_request_seq")
    @SequenceGenerator(name = "internship_request_seq", sequenceName = "internship_request_seq", allocationSize = 1)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    InternshipEntity internship;

    @Enumerated(EnumType.STRING)
    InternshipRequestStatus status;
}
