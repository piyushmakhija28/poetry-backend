package com.techdeveloper.lovepoet.annotations.sequence;

import com.techdeveloper.lovepoet.annotations.group.LengthGroup;
import com.techdeveloper.lovepoet.annotations.group.NotBlankGroup;
import com.techdeveloper.lovepoet.annotations.group.NotEmptyGroup;
import com.techdeveloper.lovepoet.annotations.group.NotNullGroup;
import com.techdeveloper.lovepoet.annotations.group.RangeGroup;
import com.techdeveloper.lovepoet.annotations.group.SizeGroup;

import jakarta.validation.GroupSequence;

@GroupSequence(value = {
		NotNullGroup.class,
		NotEmptyGroup.class,
		NotBlankGroup.class,
		LengthGroup.class,
		SizeGroup.class,
		RangeGroup.class
})
public interface ValidationSequence {

}
