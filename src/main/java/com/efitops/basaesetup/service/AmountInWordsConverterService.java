package com.efitops.basaesetup.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public interface AmountInWordsConverterService {

	String convert(Long amount);
	
	

}
