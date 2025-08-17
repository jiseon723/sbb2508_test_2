package com.board.basic.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Article findByTitle(String title);
    Article findByTitleAndContent(String title, String content);
    List<Article> findByTitleLike(String title);
    Page<Article> findAll(Pageable pageable);
    Page<Article> findAll(Specification<Article> spec, Pageable pageable);

    @Query("select "
            + "distinct A "
            + "from Article A "
            + "left outer join SiteUser U on A.author=U "
            + "where "
            + "   A.title like %:keyword% "
            + "   or A.content like %:keyword% "
            + "   or U.username like %:keyword% ")
    List<Article> findAllByKeyword(@Param("keyword") String keyword);

}
