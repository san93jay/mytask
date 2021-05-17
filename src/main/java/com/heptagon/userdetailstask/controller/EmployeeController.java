package com.heptagon.userdetailstask.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.heptagon.userdetailstask.dto.EmployeeDTO;
import com.heptagon.userdetailstask.form.ExcelSheetForm;
import com.heptagon.userdetailstask.service.EmployeeService;

import lombok.extern.log4j.Log4j2;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
@RestController
@Log4j2
@RequestMapping(value = "/api/employee")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private MessageSource messageSource;
	private Locale locale;

	// save employee from Form(Using @RequestBody)
	@PostMapping(value = "/saveEmployee")
	public ResponseEntity<EmployeeDTO> createEmployee(@Validated @RequestBody EmployeeDTO employeeDTO) {
		EmployeeDTO emplDto = null;
		try {
			emplDto = employeeService.createEmployee(employeeDTO);

		} catch (Exception e) {
			log.error(messageSource.getMessage("employee.saveData", new String[0], locale));
		}
		return new ResponseEntity<>(emplDto, HttpStatus.OK);

	}

	// save employee By ExcelSheet
	@PostMapping(value = "/save", consumes = { "multipart/form-data" })
	public ResponseEntity<ExcelSheetForm> createEmployeeByExcelSheet(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		ExcelSheetForm excelSheetForm = null;
		try {
			excelSheetForm = employeeService.createEmployeeByExcelSheet(file);

		} catch (Exception e) {
			log.error(messageSource.getMessage("employee.saveData", new String[0], locale));
		}
		return new ResponseEntity<>(excelSheetForm, HttpStatus.OK);

	}

	//Get All Employee List
	@GetMapping("/allEmployee")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
		List<EmployeeDTO> emplList = new ArrayList<>();
		try {
			emplList = employeeService.findAllEmployee();

		} catch (Exception e) {
			log.error(messageSource.getMessage("employee.saveData", new String[0], locale));
		}
		return new ResponseEntity<>(emplList, HttpStatus.OK);

	}
}
