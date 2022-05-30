package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {
	
	List<Question> findAllByQuizRef(Long ref);

}
