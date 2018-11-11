package domain;

import java.util.ArrayList;
import java.util.List;

public class MyData {
    List<Question> questionList;
    List<Article> articles;

    public MyData(){
        questionList = new ArrayList<Question>();
        articles = new ArrayList<Article>();
    }


    public void addQuestion(Question question){
        questionList.add(question);
    }

    public void addArticle(Article article){
        articles.add(article);
    }


    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
