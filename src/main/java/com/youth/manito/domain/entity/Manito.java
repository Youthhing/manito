package com.youth.manito.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id")
    private User giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manito_group")
    private ManitoGroup manitoGroup;

    private boolean giverOpen;

    private boolean resultOpen;

    @Builder(access = AccessLevel.PRIVATE)
    private Manito(Mission mission, User giver, User receiver, ManitoGroup manitoGroup) {
        this.mission = mission;
        this.giver = giver;
        this.receiver = receiver;
        this.manitoGroup = manitoGroup;
    }

    public static Manito of(final User giver, final User receiver, final ManitoGroup manitoGroup) {
        return Manito.builder()
                .giver(giver)
                .receiver(receiver)
                .manitoGroup(manitoGroup)
                .build();
    }

    public void assignMission(final Mission mission) {
        this.mission = mission;
    }

    public void updateGiverOpen(boolean open) {
        this.giverOpen = open;
    }
}
