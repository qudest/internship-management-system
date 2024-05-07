package com.ds.ims.storage.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Сущность для администратора
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "admin")
public class AdminEntity {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_seq")
    @SequenceGenerator(name = "admin_seq", sequenceName = "admin_seq", allocationSize = 1)
    Long id;

    /**
     * Имя
     */
    @Column(nullable = false)
    String name;

    /**
     * Привязка к аккаунту
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "account_id")
    AccountEntity account;
}
