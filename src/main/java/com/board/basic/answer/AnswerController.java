package com.board.basic.answer;

import com.board.basic.article.Article;
import com.board.basic.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/article")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final ArticleService articleService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
        Article article = this.articleService.getArticle(id);
        this.answerService.create(article, content);
        return String.format("redirect:/article/detail/%s", id);
    }
}