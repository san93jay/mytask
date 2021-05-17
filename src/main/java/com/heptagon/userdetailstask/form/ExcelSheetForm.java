/**
 * 
 */
package com.heptagon.userdetailstask.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
@Data
public class ExcelSheetForm implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3586064472962890205L;
	private Long excelSheetId;
	private String fileName;
	private Integer rowError;
	private Integer totalCounter;
	private List<Integer> emailCommaSeperatedList = new ArrayList<Integer>();
	private List<Integer> emailDoesNotExistList = new ArrayList<Integer>();
	private List<Integer> invalidNameList = new ArrayList<Integer>();
	private List<Integer> invalidContactList = new ArrayList<Integer>();
	private List<Integer> emptyAndInvalidAge = new ArrayList<Integer>();
	private List<Integer> invalidDepartmentList = new ArrayList<Integer>();
}
