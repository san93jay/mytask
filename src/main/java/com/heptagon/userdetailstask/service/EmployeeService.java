package com.heptagon.userdetailstask.service;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.heptagon.userdetailstask.dto.EmployeeDTO;
import com.heptagon.userdetailstask.exception.EmailNotUniqueException;
import com.heptagon.userdetailstask.form.ExcelSheetForm;

public interface EmployeeService {
	
    public EmployeeDTO createEmployee(EmployeeDTO userDto) throws EmailNotUniqueException;
    
    public ExcelSheetForm createEmployeeByExcelSheet( MultipartFile file) throws EmailNotUniqueException;
	
    public List<EmployeeDTO> findAllEmployee();
    
   



}
