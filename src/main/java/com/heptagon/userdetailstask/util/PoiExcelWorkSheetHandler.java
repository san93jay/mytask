package com.heptagon.userdetailstask.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import java.beans.PropertyDescriptor;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import lombok.extern.log4j.Log4j2;
@Log4j2
public class PoiExcelWorkSheetHandler<T> implements SheetContentsHandler {
	@Autowired
	private MessageSource messageSource;
	private Locale locale;

	

	private boolean verifiyHeader = true;
	private int skipRows = 0;
	private int HEADER_ROW = 0;
	private int currentRow = 0;
	private List<T> valueList;
	private Class<T> type;
	private Map<String, String> cellMapping = null;
	private T objCurrentRow = null;

	/**
	 * Constructor
	 * 
	 * <br>
	 * <br>
	 * <strong>For Example:</strong> Reading rows (zero based) starting from
	 * Zero<br>
	 * <code>ExcelWorkSheetHandler&lt;PersonVO> workSheetHandler = new ExcelWorkSheetHandler&lt;PersonVO>(PersonVO.class, cellMapping);</code>
	 * 
	 * @param type
	 *            a {@link Class} object
	 * @param cellMapping
	 *            a {@link Map} object
	 * @throws Exception
	 * @throws NoSuchMessageException
	 */
	public PoiExcelWorkSheetHandler(Class<T> type, Map<String, String> cellMapping)
			throws NoSuchMessageException, Exception {
		try {
			this.type = type;
			this.cellMapping = cellMapping;
			this.valueList = new ArrayList<T>();
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}
	}

	/**
	 * Constructor
	 * 
	 * <br>
	 * <br>
	 * <strong>For Example:</strong> Reading rows (zero based) starting from Row
	 * 11<br>
	 * <code>ExcelWorkSheetHandler&lt;PersonVO> workSheetHandler = new ExcelWorkSheetHandler&lt;PersonVO>(PersonVO.class, cellMapping, 10);</code>
	 * 
	 * @param type
	 *            a {@link Class} object
	 * @param cellMapping
	 *            a {@link Map} object
	 * @param skipRows
	 *            a <code>int</code> object - Number rows to skip (zero based).
	 *            default is 0
	 * @throws Exception
	 * @throws NoSuchMessageException
	 */
	public PoiExcelWorkSheetHandler(Class<T> type, Map<String, String> cellMapping, int skipRows)
			throws NoSuchMessageException, Exception {
		try {
			this.type = type;
			this.cellMapping = cellMapping;
			this.valueList = new ArrayList<T>();
			this.skipRows = skipRows;
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}
	}

	/**
	 * Returns Value List (List&lt;T>) read from Excel Workbook, Row represents one
	 * Object in a List.
	 * 
	 * <br>
	 * <br>
	 * <strong>For Example:</strong><br>
	 * <code>List&lt;PersonVO> persons = workSheetHandler.getValueList();</code>
	 * 
	 * @return List&lt;T>
	 * @throws Exception
	 * @throws NoSuchMessageException
	 */
	public List<T> getValueList() throws NoSuchMessageException, Exception {
		try {
			return valueList;
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}
	}

	/**
	 * Returns Excel Header check state, default it is enabled
	 * 
	 * @return boolean
	 * @throws Exception
	 * @throws NoSuchMessageException
	 */
	public boolean isVerifiyHeader() throws NoSuchMessageException, Exception {
		try {
			return verifiyHeader;
		}  catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}
	}

	/**
	 * To set the Excel Header check state, default it is enabled
	 * 
	 * @param verifiyHeader
	 *            a boolean
	 * @throws Exception
	 * @throws NoSuchMessageException
	 */
	public void setVerifiyHeader(boolean verifiyHeader) throws NoSuchMessageException, Exception {
		try {
			this.verifiyHeader = verifiyHeader;
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}
	}

	/**
	 * @see org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler#startRow(int)
	 */
	@Override
	public void startRow(int rowNum) {
		try {
			this.currentRow = rowNum;

			if (verifiyHeader) {
			}

			if (rowNum > HEADER_ROW && rowNum >= skipRows) {
				objCurrentRow = this.getInstance();
			}
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			try {
				throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		}
	}

	/**
	 * @see org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler#cell(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	//public void cell(String cellReference, String formattedValue) {
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {

		try {
			if (currentRow >= skipRows) {
				if (StringUtils.isBlank(formattedValue)) {
					return;
				}
				if (HEADER_ROW == currentRow && verifiyHeader) {
				}

				this.assignValue(objCurrentRow, getCellReference(cellReference), formattedValue);
			}
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			try {
				throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
			} catch (NoSuchMessageException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * @see org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler#endRow()
	 */
	@Override
//	public void endRow() {
	public void endRow(int rowNum) {

		try {
			if (null != objCurrentRow) {
				// Current row data is populated in the object, so add it to
				// list
				this.valueList.add(objCurrentRow);
			}

			// Row object is added, so reset it to null
			objCurrentRow = null;
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			try {
				throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
			} catch (NoSuchMessageException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Currently not considered for implementation
	 * 
	 * @see org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler#headerFooter(java.lang.String,
	 *      boolean, java.lang.String)
	 */
	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {
		// currently not consider for implementation
	}

	private String getCellReference(String cellReference) throws NoSuchMessageException, Exception {
		try {
			if (StringUtils.isBlank(cellReference)) {
				return "";
			}

			return cellReference.split("[0-9]*$")[0];
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}
	}

	private void assignValue(Object targetObj, String cellReference, String value)
			throws NoSuchMessageException, Exception {
		try {
			if (null == targetObj || StringUtils.isEmpty(cellReference) || StringUtils.isEmpty(value)) {
				return;
			}

			try {
				String propertyName = this.cellMapping.get(cellReference);
				if (null == propertyName) {
					log.error("Cell mapping doesn't exists!");
				} else {
					String[] fieldNames = propertyName.split("\\.");
					if (fieldNames.length > 1) {
						setNestedProperties(targetObj, fieldNames, value);
					} else {
						PropertyUtils.setSimpleProperty(targetObj, propertyName, value);
					}
				}
			} catch (IllegalAccessException iae) {
				log.error(iae.getMessage());
			} catch (InvocationTargetException ite) {
				log.error(ite.getMessage());
			} catch (NoSuchMethodException nsme) {
				log.error(nsme.getMessage());
			}
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}
	}

	private T getInstance() throws NoSuchMessageException, Exception {
		try {
			return type.newInstance();
		} catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}
	}

	private void setNestedProperties(Object mainObj, String[] fieldNames, String value)
			throws NoSuchMessageException, Exception {
		try {
			Object currentObj = null;
			String strObjName = fieldNames[fieldNames.length - 2];
			if (fieldNames.length >= 3) {
				String subObjStr = fieldNames[fieldNames.length - 3];
				// get sub object
				currentObj = PropertyUtils.getProperty(mainObj, subObjStr);
				if (currentObj == null) {
					PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(mainObj, subObjStr);
					Class<?> propertyType = propertyDescriptor.getPropertyType();
					Object newInstance = propertyType.newInstance();
					PropertyUtils.setProperty(mainObj, subObjStr, newInstance);
					currentObj = PropertyUtils.getProperty(mainObj, subObjStr);
				}
				// get new object
				Object currentObj1 = PropertyUtils.getProperty(currentObj, strObjName);
				if (currentObj1 == null) {
					PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(currentObj, strObjName);
					Class<?> propertyType = propertyDescriptor.getPropertyType();
					Object newInstance = propertyType.newInstance();
					PropertyUtils.setProperty(currentObj, strObjName, newInstance);
				}
				currentObj1 = PropertyUtils.getProperty(currentObj, strObjName);
				PropertyUtils.setSimpleProperty(currentObj1, fieldNames[fieldNames.length - 1], value);
			} else {
				currentObj = PropertyUtils.getProperty(mainObj, strObjName);
				if (currentObj == null) {
					PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(mainObj, strObjName);
					Class<?> propertyType = propertyDescriptor.getPropertyType();
					Object newInstance = propertyType.newInstance();
					PropertyUtils.setProperty(mainObj, strObjName, newInstance);
				}
				currentObj = PropertyUtils.getProperty(mainObj, strObjName);
				PropertyUtils.setSimpleProperty(currentObj, fieldNames[fieldNames.length - 1], value);
			}

		}catch (Exception e) {
			log.warn("Error: " + e.getMessage() + " - " + e.getStackTrace()[0].getLineNumber());
			throw new Exception(messageSource.getMessage("common.commonErrorMsgService", new String[0], locale));
		}
	}
}

