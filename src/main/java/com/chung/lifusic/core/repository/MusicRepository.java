package com.chung.lifusic.core.repository;

import com.chung.lifusic.core.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MusicRepository extends JpaRepository<Music, Long> {
    // 아티스트의 음악 목록 가져오기
    @Query(value = "select m from Music m " +
            "left join fetch m.thumbnailImageFile " +
            "where m.artist.id = :artistId"
            , countQuery = "select count(m.id) from Music m " +
            "where m.artist.id = :artistId"
    )
    Page<Music> findMusics(Long artistId, Pageable page);

    // 고객이 음악을 키워드로 검색
    @Query(value = "select m from Music m " +
            "left join fetch m.thumbnailImageFile " +
            "where m.artistName = :keyword or m.name = :keyword"
            , countQuery = "select count(m.id) from Music m " +
            "where m.artistName = :keyword or m.name = :keyword"
    )
    Page<Music> searchMusics(String keyword, Pageable page);
}
