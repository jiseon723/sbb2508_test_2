package com.board.basic;

import com.board.basic.answer.Answer;
import com.board.basic.answer.AnswerRepository;
import com.board.basic.article.Article;
import com.board.basic.article.ArticleRepository;
import com.board.basic.article.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BasicApplicationTests {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private ArticleService articleService;

	@Test
	void testJpa01() {
		Article q1 = new Article();
		q1.setTitle("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.articleRepository.save(q1);  // 첫번째 질문 저장

		Article q2 = new Article();
		q2.setTitle("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.articleRepository.save(q2);  // 두번째 질문 저장
	}

	@Test
	void testJpa02() {
		List<Article> all = this.articleRepository.findAll();
		assertEquals(2, all.size());

		Article q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getTitle());
	}

	@Test
	void testJpa03() {
		Optional<Article> oq = this.articleRepository.findById(1);
		if(oq.isPresent()) {
			Article q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getTitle());
		}
	}

	@Test
	void testJpa04() {
		Article q = this.articleRepository.findByTitle("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

	@Test
	void testJpa05() {
		Article q = this.articleRepository.findByTitleAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
	}

	@Test
	void testJpa06() {
		List<Article> qList = this.articleRepository.findByTitleLike("sbb%");
		Article q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getTitle());
	}

	@Test
	void testJpa07() {
		Optional<Article> oq = this.articleRepository.findById(1);
		assertTrue(oq.isPresent());
		Article q = oq.get();
		q.setTitle("수정된 제목");
		this.articleRepository.save(q);
	}

	@Test
	void testJpa08() {
		assertEquals(2, this.articleRepository.count());
		Optional<Article> oq = this.articleRepository.findById(1);
		assertTrue(oq.isPresent());
		Article q = oq.get();
		this.articleRepository.delete(q);
		assertEquals(1, this.articleRepository.count());
	}

	@Test
	void testJpa09() {
		Optional<Article> oq = this.articleRepository.findById(2);
		assertTrue(oq.isPresent());
		Article q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setArticle(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	void testJpa10() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getArticle().getId());
	}

	@Transactional
	@Test
	void testJpa11() {
		Optional<Article> oq = this.articleRepository.findById(2);
		assertTrue(oq.isPresent());
		Article q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}

	@Test
	void testJpa12() {
		for (int i = 1; i <= 300; i++) {
			String title = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.articleService.create(title, content);
		}
	}
}
