package com.board.basic.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Article findByTitle(String title);
    Article findByTitleAndContent(String title, String content);
    List<Article> findByTitleLike(String title);
    Page<Article> findAll(Pageable pageable);
}
