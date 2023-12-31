package com.chung.lifusic.musicservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "music_in_playlist")
public class MusicInPlaylist extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Music music;

    @ManyToOne(fetch = FetchType.LAZY)
    private Playlist playlist;
}
