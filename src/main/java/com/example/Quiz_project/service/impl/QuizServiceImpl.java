package com.example.Quiz_project.service.impl;

import com.example.Quiz_project.entity.OptionChoice;
import com.example.Quiz_project.entity.Question;
import com.example.Quiz_project.entity.Quiz;
import com.example.Quiz_project.repository.OptionRepository;
import com.example.Quiz_project.repository.QuestionRepository;
import com.example.Quiz_project.repository.QuizRepository;
import com.example.Quiz_project.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

     private final QuestionRepository questionRepository;

    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;
    private final OptionRepository optionRepo;

    @Override
    public Quiz createQuiz(Quiz quiz) {
        if (quiz.getQuestions() != null) {
            quiz.getQuestions().forEach(q -> {
                q.setQuiz(quiz);

                if (q.getOptions() != null) {
                    q.getOptions().forEach(o -> o.setQuestion(q));
                }
            });
        }
        return quizRepo.save(quiz);
    }

    @Override
    public List<Quiz> getAll() {
        return quizRepo.findAll();
    }

    @Override
    public Quiz getQuiz(Long id) {
        return quizRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with ID: " + id));
    }

    @Override
    public Quiz updateQuiz(Long id, Quiz quizData) {
        Quiz existing = getQuiz(id);

        existing.setTitle(quizData.getTitle());
        existing.setDescription(quizData.getDescription());
        existing.setTimeLimit(quizData.getTimeLimit());

        return quizRepo.save(existing);
    }

    @Override
    public void deleteQuiz(Long id) {
        quizRepo.deleteById(id);
    }

    @Override
    public Question addQuestion(Long quizId, Question question) {
        Quiz quiz = getQuiz(quizId);
        question.setQuiz(quiz);

        if (question.getOptions() != null) {
            question.getOptions().forEach(o -> o.setQuestion(question));
        }

        return questionRepo.save(question);
    }

    @Override
    public Question updateQuestion(Long questionId, Question q) {
        Question existing = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        existing.setText(q.getText());
        return questionRepo.save(existing);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionRepo.deleteById(questionId);
    }

    @Override
    public OptionChoice addOption(Long questionId, OptionChoice option) {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        option.setQuestion(question);
        return optionRepo.save(option);
    }

    @Override
    public void deleteOption(Long optionId) {
        optionRepo.deleteById(optionId);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

   @Override
public Quiz getQuizById(Long id) {
    return quizRepository.fetchFullQuiz(id)
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
}

@Override
public List<Question> getQuestions(Long quizId) {
    return questionRepository.findByQuizIdFetch(quizId);
}


    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
public Question getQuestionById(Long id) {
    return questionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Question not found"));
}

@Override
    public List<Quiz> listAll() {
        return quizRepo.findAllByOrderByIdAsc();
    }

    @Override
    public Quiz loadQuiz(Long id) {
        return quizRepo.fetchFullQuiz(id)
                .orElseThrow(() -> new RuntimeException("Quiz Not Found"));
    }

    @Override
    public int totalQuestions(Long id) {
        return loadQuiz(id).getQuestions().size();
    }

    @Override
    public int gradeQuiz(Long id, Map<Long, Long> answers) {
        Quiz quiz = loadQuiz(id);
        int score = 0;

        for (Question q : quiz.getQuestions()) {
            Long selected = answers.get(q.getId());
            if (selected == null) continue;
            for (OptionChoice opt : q.getOptions()) {
                if (opt.getId().equals(selected) && opt.isCorrect()) {
                    score++;
                }
            }
        }
        return score;
    }

}


