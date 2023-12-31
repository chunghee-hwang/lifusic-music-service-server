package com.chung.lifusic.musicservice.repository;

import com.chung.lifusic.musicservice.entity.MusicInPlaylist;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicInPlaylistRepository extends JpaRepository<MusicInPlaylist, Long> {

    // 플레이리스트에 있는 음악 목록 가져오기
    @Query(value = "select mp " +
            "from MusicInPlaylist mp " +
            "left join fetch mp.music m " +
            "left join fetch m.musicFile mf " +
            "left join fetch m.thumbnailImageFile mt " +
            "where mp.playlist.id = :playlistId")
    List<MusicInPlaylist> findMusicsInPlaylist(Long playlistId, Sort sort);

    // 플레이리스트에 음악이 있는 지 확인
    @Query(value = "select if(count(id) > 0, 'true', 'false')" +
            "from music_in_playlist where music_id = :musicId and playlist_id = :playlistId", nativeQuery = true)
    boolean existsByPlaylistIdAndMusicId(Long playlistId, Long musicId);

    @Query(value = "select id from music_in_playlist where music_id in :musicIds", nativeQuery = true)
    List<Long> findMusicInPlaylistIdsByMusicIds(List<Long> musicIds);

    // 음악 아이디 배열로 삭제
    @Query(value = "delete from music_in_playlist " +
            "where music_id in :musicIds", nativeQuery = true)
    void deleteAllByMusicId(List<Long> musicIds);
}
