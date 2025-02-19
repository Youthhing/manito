package com.youth.manito.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id")
    private User giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_vote_group_id")
    private UserVoteGroup userVoteGroup;

    @Column(name = "result")
    private boolean result;

    private Vote(User giver, User receiver, UserVoteGroup userVoteGroup) {
        this.giver = giver;
        this.receiver = receiver;
        this.userVoteGroup = userVoteGroup;
    }

    public static Vote of(User giver, User receiver, UserVoteGroup userVoteGroup) {
        return new Vote(giver, receiver, userVoteGroup);
    }

    public void correct() {
        this.result = true;
    }
}
