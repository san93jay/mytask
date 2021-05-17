package com.heptagon.userdetailstask.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import com.heptagon.userdetailstask.exception.ExcelReaderException;
import com.myjeeva.poi.ExcelReader;

import lombok.extern.log4j.Log4j2;
@Log4j2
public class ExcelPoiReader {
	@Autowired
	private static MessageSource messageSource;
	private static Locale locale;

	public static <T> List<T> readXLSXFile(File file, Class<T> T, Map<String, String> cellMapping)
			throws NoSuchMessageException, Exception {
		try {

			OPCPackage pkg = null;
			InputStream inputStream = null;
			try {

				// Input File initialize
				inputStream = new FileInputStream(file);

				// Create worksheetHanlder using provided cell mapping and object
				PoiExcelWorkSheetHandler<T> workSheetHandler = new PoiExcelWorkSheetHandler<T>(T, cellMapping);

				pkg = OPCPackage.open(inputStream);

				ExcelReader reader = new ExcelReader(file, workSheetHandler, null);
				reader.process();

				if (workSheetHandler.getValueList().isEmpty()) {
					// No data present
					log.error("Provided " + file.getAbsolutePath() + " is empty. Please check file.");
				} else {
					if (log.isDebugEnabled()) {
						log.debug(workSheetHandler.getValueList().size()
								+ " no. of records read from given excel worksheet successfully.");
					}
					return workSheetHandler.getValueList();
				}
			} catch (FileNotFoundException fnfe) {
				log.error(fnfe.getMessage(), fnfe.getCause());
				throw new ExcelReaderException(fnfe.getMessage(), fnfe.getCause());
			} catch (RuntimeException are) {
				are.printStackTrace();
				log.error(are.getMessage(), are.getCause());
				throw new ExcelReaderException(are.getMessage(), are.getCause());
			} catch (InvalidFormatException ife) {
				log.error(ife.getMessage(), ife.getCause());
				throw new ExcelReaderException(ife.getMessage(), ife.getCause());
			} catch (IOException ioe) {
				log.error(ioe.getMessage(), ioe.getCause());
				throw new ExcelReaderException(ioe.getMessage(), ioe.getCause());
			} catch (Exception e) {
				log.error(e.getMessage(), e.getCause());
				throw new ExcelReaderException(e.getMessage(), e.getCause());
			} finally {
				if (inputStream != null) {
					IOUtils.closeQuietly(inputStream);
				}
				try {
					if (null != pkg) {
						pkg.close();
					}
				} catch (IOException e) {
					// just ignore IO exception
				}
			}
			return null;
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}

	}
}
