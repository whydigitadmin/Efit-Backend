package com.efitops.basesetup.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.service.SalesService;

@CrossOrigin
@RestController
@RequestMapping("/api/sales")
public class SalesController extends BaseController{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CostInvoiceController.class);
	
	@Autowired
	SalesService salesService;
	
}
