package com.yongha.toy1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class ScheduleAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleUserId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ElementCollection
    public List<Integer> possibleCell;
    @ElementCollection
    public List<Integer> impossibleCell;
}