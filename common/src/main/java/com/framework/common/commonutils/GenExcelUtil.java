package com.framework.common.commonutils;

import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.lang.Number;

public class GenExcelUtil {

	protected static Logger logger = LoggerFactory.getLogger(GenExcelUtil.class);


	/**
	 *
	 * @param filename： excel文件名
	 * @param title: 表内sheet名字
	 * @param destListBean: 数据对象(item)列表
	 * @param excelstr: 第一行数据
	 * @param objstr：对象(item)的所有属性
	 * @param response
	 */
	public static void genExcel(String filename, String title, List<?> destListBean, String[] excelstr, String[] objstr,
			HttpServletResponse response) {
		int[] widthstr = new int[objstr.length];
		String[] colorstr = new String[objstr.length];
		for (int i = 0; i < objstr.length; i++) {
			colorstr[i] = "0";
			widthstr[i] = objstr[i].length() < 18 ? 18 : objstr[i].length();
		}
		genExcel(filename, title, destListBean, excelstr, objstr, colorstr, widthstr, response);
	}




	public static void genExcel(String filename, String title, List<?> destListBean, String[] excelstr, String[] objstr,
			String[] colorstr, int[] widthstr, HttpServletResponse response) {
		BufferedOutputStream bos = null;
		jxl.write.WritableWorkbook wwb = null;
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			bos = new BufferedOutputStream(out);
			// response.setCharacterEncoding("UTF-8"); // 定义字符集
			response.setContentType("application/vnd.ms-excel"); // 导出格式为excel文档
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859_1") + ".xls"); // excel文档名称
			wwb = Workbook.createWorkbook(bos);
			WritableSheet ws = wwb.createSheet("数据", 0);
			WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat format1 = new WritableCellFormat(font1);
			format1.setWrap(true);

			// 获取起始行
			int colStart = 0;
			if (title != null) {
				// 设置title
				WritableCellFormat headerFormat = new WritableCellFormat();
				//水平居中对齐
				headerFormat.setAlignment(Alignment.CENTRE);
				//竖直方向居中对齐
				headerFormat.setVerticalAlignment
						(VerticalAlignment.CENTRE);
				jxl.write.Label labelC = new jxl.write.Label(colStart, 0, title,headerFormat);
				ws.addCell(labelC);
				ws.setColumnView(colStart, 500);
				ws.mergeCells(0, 0, 10, 0);
				colStart++;
			}

			for (int q = 0; q < excelstr.length; q++) {
			/*	if (colorstr[q].equals("1")) {
					jxl.write.Label labelC = new jxl.write.Label(q, colStart, excelstr[q], format1);
					ws.addCell(labelC);
				} else {
					jxl.write.Label labelC = new jxl.write.Label(q, colStart, excelstr[q]);
					ws.addCell(labelC);
				}*/

				WritableCellFormat headerFormat = new WritableCellFormat();
				//水平居中对齐
				headerFormat.setAlignment(Alignment.CENTRE);
				//竖直方向居中对齐
				headerFormat.setVerticalAlignment
						(VerticalAlignment.CENTRE);


				jxl.write.Label labelC = new jxl.write.Label(q, colStart, excelstr[q],headerFormat);
				ws.addCell(labelC);
				// 设置行高
				ws.setColumnView(q, widthstr[q]);
			}
			colStart++;
			if (destListBean != null) {
				DisplayFormat DisplayFormat = NumberFormats.PERCENT_FLOAT;
				WritableCellFormat wcfF = new WritableCellFormat(DisplayFormat);
				// 设置数据
				for (int i = 0; i < destListBean.size(); i++) {
					Object bean = destListBean.get(i);
					Object[] strs = genRowData(bean, objstr);

					for (int j = 0; j < strs.length; j++) {
						try {
							Object obj = strs[j];
							if (obj instanceof String) {
								ws.addCell(new jxl.write.Label(j, i + colStart, (String) obj, format1));
							} else if (obj instanceof Number) {
								ws.addCell(new jxl.write.Number(j, i + colStart, Double.parseDouble(obj.toString())));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			}
			wwb.write();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wwb != null || bos != null) // 关闭流
			{
				try {
					wwb.close();
					bos.close();
				} catch (IOException e) {
					logger.debug(e.getMessage());
				} catch (WriteException e) {
					logger.debug(e.getMessage());
				}
			}

			if (out != null) {
				try {
					out.flush();
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}



	public static Object[] genRowData(Object bean, String[] names) {
		Object[] rowData = new Object[names.length];
		try {
			if (bean instanceof Map) {
				Map<String, Object> map = (Map) bean;
				for (int j = 0; j < names.length; j++) {
					rowData[j] = dataToString(map.get(names[j]));
				}
			} else {
				for (int j = 0; j < names.length; j++) {
					String strCol = names[j];
					rowData[j] = dataToString(PropertyUtils.getProperty(bean, strCol));
				}
			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return rowData;
	}

	public static Object dataToString(Object objValue) {
		if (objValue != null) {
			if (objValue instanceof java.util.Date) {
				java.util.Date objDate = (java.util.Date) objValue;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(objDate);
				// 如果大于2000则是
				if (calendar.get(Calendar.YEAR) > 2000) {
					return DateFormatUtils.format(objDate, "yyyy-MM-dd");
				} else {
					return DateFormatUtils.format(objDate, "HH:mm:ss");
				}

			} else if (objValue instanceof Number) {
				return objValue;
			} else {
				return objValue.toString();
			}
		}
		return null;
	}

}
