package com.yongha.toy1.entity;

import com.yongha.toy1.dto.AccountInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Account {
    @Id
    private Long id;
    private String name = "";
    private String thumbnailUri = "";
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScheduleAccount> scheduleAccounts;

    public Account(AccountInfo accountInfo) {
        id = accountInfo.getId();
        name = accountInfo.getName();
        thumbnailUri = accountInfo.getThumbnailUri();
    }
}