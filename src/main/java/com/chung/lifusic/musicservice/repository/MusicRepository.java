package com.chung.lifusic.musicservice.repository;

import com.chung.lifusic.musicservice.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {
    // 아티스트의 음악 목록 가져오기
    @Query(value = "select m from Music m " +
            "left join fetch m.thumbnailImageFile " +
            "where m.artist.id = :artistId"
            , countQuery = "select count(m.id) from Music m " +
            "where m.artist.id = :artistId"
    )
    Page<Music> findMusics(Long artistId, Pageable page);

    @Query(value = "select m from Music m " +
            "left join fetch m.thumbnailImageFile " +
            "where m.artist.id = :artistId and m.name like %:keyword%"
            , countQuery = "select count(m.id) from Music m " +
            "where m.artist.id = :artistId and m.name like %:keyword%"
    )
    Page<Music> findMusics(Long artistId, String keyword, Pageable page);


    // 고객이 음악을 검색
    @Query(value = "select m from Music m " +
            "left join fetch m.musicFile " +
            "left join fetch m.thumbnailImageFile ",
            countQuery = "select count(m.id) from Music m "
    )
    Page<Music> searchMusics(Pageable page);

    @Query(value = "select m from Music m " +
            "left join fetch m.musicFile " +
            "left join fetch m.thumbnailImageFile " +
            "where m.artistName = :keyword or m.name like %:keyword%"
            , countQuery = "select count(m.id) from Music m " +
            "where m.artistName = :keyword or m.name like %:keyword%"
    )
    Page<Music> searchMusics(String keyword, Pageable page);

    // 음악 아이디 배열로 음악 검색
    @Query(value = "select m from Music m " +
            "left join fetch m.musicFile mf " +
            "left join fetch m.thumbnailImageFile tf " +
            "where m.id in :musicIds")
    List<Music> findMusicsByIds(List<Long> musicIds);

    // 음악 하나 찾기
    @Query(value = "select m from Music m " +
            "left join fetch m.musicFile mf " +
            "left join fetch m.thumbnailImageFile tf " +
            "where m.id = :musicId")
    Optional<Music> findMusic(Long musicId);
}
