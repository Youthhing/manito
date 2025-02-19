package com.youth.manito.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVoteGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "submitted", nullable = false)
    private boolean submitted;

    @OneToMany(mappedBy = "userVoteGroup", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    private UserVoteGroup(User user) {
        this.user = user;
    }

    public static UserVoteGroup from(User user) {
        return new UserVoteGroup(user);
    }

    public List<Vote> getVotes() {
        return new ArrayList<>(votes);
    }

    public void updateVotes(List<Vote> votes) {
        this.votes.clear();
        this.votes.addAll(votes);
    }
}
