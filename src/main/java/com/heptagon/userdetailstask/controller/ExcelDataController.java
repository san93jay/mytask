package com.heptagon.userdetailstask.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heptagon.userdetailstask.dto.EmployeeDTO;
import com.heptagon.userdetailstask.exception.MyException;
import com.heptagon.userdetailstask.service.EmployeeService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(value = "/excel/emp")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExcelDataController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private MessageSource messageSource;
	private Locale locale;

	
	// Download All Employee List in Excel Sheet
	@GetMapping("/download/list")
	public void getAllEmployeeListInExcelSheet(HttpServletResponse httpServletResponse,
			HttpServletRequest httpServletRequest) throws IOException {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Employee List");
			XSSFRow header = worksheet.createRow(0);

			XSSFCellStyle style = workbook.createCellStyle();
			short border = HSSFCellStyle.BORDER_THIN;
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderTop(border);
			style.setBorderBottom(border);

			header.createCell(0).setCellValue("employeeName");
			header.createCell(1).setCellValue("Departments");
			header.createCell(2).setCellValue("Email");
			header.createCell(3).setCellValue("Contact");
			header.createCell(4).setCellValue("Age");

			for (int n = 0; n <= 4; n++) {
				XSSFCellStyle style1 = workbook.createCellStyle();
				short border1 = HSSFCellStyle.BORDER_THIN;
				style1.setBorderLeft(border1);
				style1.setBorderRight(border1);
				style1.setBorderTop(border1);
				style1.setBorderBottom(border1);
				style1.setFillForegroundColor(HSSFColor.YELLOW.index);
				style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				XSSFFont font = workbook.createFont();
				font.setBold(true);
				style1.setFont(font);
				header.getCell(n).setCellStyle(style1);
			}
			String filename = "Employee_List.xlsx";
			List<EmployeeDTO> emplList = employeeService.findAllEmployee();
			if (emplList != null) {
				int i = 1;
				for (EmployeeDTO empDto : emplList) {

					XSSFRow header1 = worksheet.createRow(i);
					header1.createCell(0).setCellValue(empDto.getEmployeeName());
					header1.createCell(1).setCellValue(empDto.getDepartment());
					header1.createCell(2).setCellValue(empDto.getEmail());
					header1.createCell(3).setCellValue(empDto.getContact());
					header1.createCell(4).setCellValue(empDto.getAge());
					for (int n = 0; n <= 4; n++) {
						header1.getCell(n).setCellStyle(style);
					}
					i++;
				}
			}
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + filename);
			workbook.write(httpServletResponse.getOutputStream());
			log.info("Sample " + messageSource.getMessage("file.download.success", new String[0], locale));
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());

		}

	}

	// Download Sample Excel Sheet For Employee Upload
	@GetMapping("/download/sample")
	public void downloadSampleData(HttpServletResponse response) {

		try {
			ClassPathResource classPathResource = new ClassPathResource("Employee_Data_Sample.xlsx");

			byte[] sampleData = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());

			response.setHeader("Content-disposition", "attachment; filename=" + "Employee_Data_Sample.xlsx");
			response.getOutputStream().write(sampleData);

			log.info("Sample " + messageSource.getMessage("file.download.success", new String[0], locale));

		} catch (IOException e) {
			throw new MyException(e.getMessage());
		} catch (Exception e) {
			throw new MyException(e.getMessage());
		}
	}

}
