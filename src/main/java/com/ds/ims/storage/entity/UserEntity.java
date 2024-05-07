package com.ds.ims.storage.entity;

import com.ds.ims.storage.entity.status.EducationalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Сущность для пользователя
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "\"user\"")
public class UserEntity {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    Long id;

    /**
     * Имя
     */
    @Column(name = "first_name", nullable = false)
    String firstName;

    /**
     * Отчество
     */
    @Column(name = "middle_name")
    String middleName;

    /**
     * Фамилия
     */
    @Column(name = "last_name", nullable = false)
    String lastName;

    /**
     * Email
     */
    @Column(unique = true, nullable = false)
    String email;

    /**
     * Телефон
     */
    @Column(nullable = false)
    String phone;

    /**
     * Telegram ID
     */
    @Column(name = "telegram_id", nullable = false)
    String telegramId;

    /**
     * Информация о пользователе
     */
    @Column(name = "personal_information")
    String personalInformation;

    /**
     * День рождения
     */
    @Column(name = "birth_date", nullable = false)
    LocalDate birthDate;

    /**
     * Город
     */
    @Column(nullable = false)
    String city;

    /**
     * Статус образования
     */
    @Column(name = "educational_status", nullable = false)
    @Enumerated(EnumType.STRING)
    EducationalStatus educationalStatus;

    /**
     * Университет
     */
    String university;

    /**
     * Факультет
     */
    String faculty;

    /**
     * Специальность
     */
    String speciality;

    /**
     * Курс
     */
    Integer course;

    /**
     * Аккаунт к которому привязан пользователь
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "account_id")
    AccountEntity account;
}
