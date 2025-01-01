package com.techdeveloper.lovepoet.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techdeveloper.lovepoet.constants.Category;
import com.techdeveloper.lovepoet.constants.PoetryStatus;
import com.techdeveloper.lovepoet.entity.Poetry;

public interface PoetryRepository extends JpaRepository<Poetry, Serializable> {

	List<Poetry> findByDeletedFalse();

	Long countByPoetryStatusAndDeletedFalse(PoetryStatus poetryStatus);

	Long countByDeletedFalse();

	List<Poetry> findByDeletedFalseAndCategory(Category categoryType);

}
