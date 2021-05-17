/**
 * 
 */
package com.heptagon.userdetailstask.serviceImpl;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.poi.util.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.heptagon.userdetailstask.dto.EmployeeDTO;
import com.heptagon.userdetailstask.entity.Employee;
import com.heptagon.userdetailstask.enums.Departments;
import com.heptagon.userdetailstask.exception.EmailNotUniqueException;
import com.heptagon.userdetailstask.exception.MyException;
import com.heptagon.userdetailstask.form.ExcelSheetForm;
import com.heptagon.userdetailstask.repository.EmployeeRepository;
import com.heptagon.userdetailstask.service.EmployeeService;
import com.heptagon.userdetailstask.util.ExcelPoiReader;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EmployeeServicesImpl implements EmployeeService {

	@Autowired
	PasswordEncoder encoder;
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EmployeeDTO createEmployee(EmployeeDTO empDto) throws EmailNotUniqueException {
		Employee employee = null;
		EmployeeDTO result_empDto = null;
		try {
			employee = modelMapper.map(empDto, Employee.class);
			employee = employeeRepository.save(employee);
			result_empDto = modelMapper.map(employee, EmployeeDTO.class);
			log.info("Employee Created successFully!");
		} catch (DataAccessException e) {
			throw new MyException(e.getCause().getCause().getMessage());
		} catch (Exception e) {
			throw new MyException(e.getMessage());
		}
		return result_empDto;
	}

	@Override
	public List<EmployeeDTO> findAllEmployee() {
		List<EmployeeDTO> employeeDtoList = null;
		List<Employee> employeeList = null;
		try {
			Optional<List<Employee>> empList = employeeRepository.findAllEmployeeList();
			if (empList.isPresent()) {
				employeeList = empList.get();
			}
			employeeDtoList = modelMapper.map(employeeList, new TypeToken<List<EmployeeDTO>>() {
			}.getType());
		} catch (Exception e) {

		}
		return employeeDtoList;
	}

	@Override
	public ExcelSheetForm createEmployeeByExcelSheet(MultipartFile uploadedFile) throws EmailNotUniqueException {
		ExcelSheetForm excelSheetForm = new ExcelSheetForm();
		File file = null;
		try {
			if (uploadedFile != null && !uploadedFile.isEmpty()) {
				excelSheetForm.setFileName(uploadedFile.getOriginalFilename());
				InputStream input = uploadedFile.getInputStream();
				String prefix = FilenameUtils.getBaseName(uploadedFile.getOriginalFilename());
				String suffix = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
				file = File.createTempFile(prefix + "-", "." + suffix, null);
				OutputStream output = new FileOutputStream(file);

				try {
					IOUtils.copy(input, output);
				} finally {
					IOUtils.closeQuietly(output);
					IOUtils.closeQuietly(input);
				}
				excelSheetForm = uploadExcelSheet(file, excelSheetForm);

			}

		} catch (DataAccessException e) {
			throw new MyException(e.getCause().getCause().getMessage());
		} catch (Exception e) {
			throw new MyException(e.getMessage());
		}
		return excelSheetForm;

	}

	private ExcelSheetForm uploadExcelSheet(File file, ExcelSheetForm excelSheetForm) {
		Employee employee = null;
		try {
			Map<String, String> cellMapping = new HashMap<String, String>();
			cellMapping.put("HEADER", "employeeName,email,contact,department,age");
			cellMapping.put("A", "employeeName");
			cellMapping.put("B", "email");
			cellMapping.put("C", "contact");
			cellMapping.put("D", "department");
			cellMapping.put("E", "age");
			
			// Convert Excel Sheet Data into EmployeeDTO Object List
			List<EmployeeDTO> list = ExcelPoiReader.readXLSXFile(file, EmployeeDTO.class, cellMapping);

			Integer noOfEntries = list.size();
			int rowIndex = 2;
			if (noOfEntries < 55000 && !list.isEmpty()) {
				if (!list.isEmpty()) {
					if (noOfEntries > 10000) {
						throw new MyException("File Size is Greater than 10000");
					}
					boolean allowLocal = true;
					List<Integer> invalidEmail = new ArrayList<Integer>();
					List<Integer> emptyEmail = new ArrayList<Integer>();
					List<Integer> emptyName = new ArrayList<Integer>();
					List<Integer> emptyContact = new ArrayList<Integer>();
					List<Integer> emptyDepartment = new ArrayList<Integer>();
					List<Integer> emptyAge = new ArrayList<Integer>();
					for (EmployeeDTO emp : list) {
						EmployeeDTO newEmpDTO = new EmployeeDTO();
						if (emp.getEmail() == null || StringUtils.isBlank(emp.getEmail())) {
							emptyEmail.add(rowIndex);
						}else if (EmailValidator.getInstance(allowLocal).isValid(emp.getEmail())) {
							newEmpDTO.setEmail(emp.getEmail());
						} else {
							invalidEmail.add(rowIndex);
						}
						if (emp.getEmployeeName() == null || StringUtils.isBlank(emp.getEmployeeName())) {
							emptyName.add(rowIndex);
						} else if(emp.getEmployeeName().matches("^[a-zA-Z0-9\\s]+$")) {
							newEmpDTO.setEmployeeName(emp.getEmployeeName());
						}

						if (emp.getContact()==null) {
							emptyContact.add(rowIndex);
						} else if (emp.getContact().matches("^\\d{10}$")) {
							newEmpDTO.setContact(emp.getContact());
						}
						if (emp.getDepartment()== null) {
							emptyDepartment.add(rowIndex);						
						} else if(Departments.valueOf(emp.getDepartment()) != null) {
							newEmpDTO.setDepartment(emp.getDepartment());
						}
						if (emp.getAge()==null) {							
							emptyAge.add(rowIndex);
						} else if (emp.getAge().matches("^\\d{1,3}$")) {
							newEmpDTO.setAge(emp.getAge());
						}
						employee = modelMapper.map(newEmpDTO, Employee.class);
						employee = employeeRepository.save(employee);
						log.info("data save by file !", rowIndex);
						rowIndex++;
					}
					excelSheetForm.setEmailCommaSeperatedList(invalidEmail);
					excelSheetForm.setEmailDoesNotExistList(emptyEmail);
					excelSheetForm.setEmptyAndInvalidAge(emptyAge);
					excelSheetForm.setInvalidContactList(emptyContact);
					excelSheetForm.setInvalidDepartmentList(emptyDepartment);
					excelSheetForm.setInvalidNameList(emptyName);
					excelSheetForm.setTotalCounter(noOfEntries);
				}
			}
		} catch (Exception e) {
			log.error("Error in file");
		}
		// ExcelSheet Error Response
		return excelSheetForm;
	}
}
