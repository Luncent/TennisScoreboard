package org.example.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "matches")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder(access = AccessLevel.PUBLIC, builderClassName = "builder")
@Getter
@Setter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="player1", nullable = false)
    private Player player1;

    @OneToOne
    @JoinColumn(name="player2", nullable = false)
    private Player player2;

    @OneToOne
    @JoinColumn(name="winner", nullable = false)
    private Player winner;
}
