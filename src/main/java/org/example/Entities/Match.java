package org.example.Entities;

import lombok.*; // .* imports are not really recommended

import javax.persistence.*;

@NamedEntityGraph(
    name="MatchAndPlayers",
    attributeNodes = {
        @NamedAttributeNode("player1"),
        @NamedAttributeNode("player2"),
        @NamedAttributeNode("winner")
    }
)
@Entity
@Table(name = "matches")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder(access = AccessLevel.PUBLIC, builderClassName = "builder")
@Getter
@Setter 
// lombok also has @Data, which is a combo of most annotations u used. 
// After java 17 devs tend to choose records due to their immutability
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
