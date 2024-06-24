package com.errorCode.pandaOffice.recruitment.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "applicant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant {

    /* 면접자 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 면접자 이름 */
    @Column(nullable = false)
    private String name;

    /* 면접자 생년월일 */
    @Column(nullable = false)
    private LocalDate birthDate;

    /* 면접자 성별 */
    @Column(nullable = false)
    private String gender;

    /* 면접자 연락처 */
    @Column(nullable = false, unique = true)
    private String phone;

    /* 면접자 주소 */
    @Column(nullable = false)
    private String address;

    /* 면접자 이메일 */
    @Column(nullable = false, unique = true)
    private String email;

    /* 면접 일정 */
    @ManyToOne
    @JoinColumn(name = "interview_schedule_id")
    private InterviewSchedule interviewSchedule;

    public Applicant(String name, LocalDate birthDate, String gender, String phone, String address, String email) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public void modify(String name, LocalDate birthDate, String gender, String phone, String address, String email) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public static Applicant of (
            final String name,
            final LocalDate birthDate,
            final String gender,
            final String phone,
            final String address,
            final String email
    ) {
        return new Applicant(
                name,
                birthDate,
                gender,
                phone,
                address,
                email
        );
    }
}
