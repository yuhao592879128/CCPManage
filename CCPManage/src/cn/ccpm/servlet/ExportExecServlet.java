package cn.ccpm.servlet;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.ccpm.Service.partyService;
import cn.ccpm.beans.party;
@WebServlet("/ExportExecServlet")

public class ExportExecServlet extends HttpServlet {

	private static final long serialVersionUID = -2912332646148125437L;

	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// 声明一个工作薄
		System.out.println("ser");
		HSSFWorkbook  workbook = new HSSFWorkbook ();  
		// 生成一个表格
        HSSFSheet sheet = workbook.createSheet("党员信息表");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short 
        HSSFRow row = sheet.createRow((int) 0);  
        CellStyle cellStyle =workbook.createCellStyle();  
        // 设置这些样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        row.createCell(0).setCellStyle(cellStyle);  
		 Cell cell = row.createCell((short) 0);
		 cell.setCellValue("id");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 1);  
         cell.setCellValue("姓名");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 2);  
         cell.setCellValue("身份证");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 3);  
         cell.setCellValue("地址");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 4);  
         cell.setCellValue("工资");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 5);  
         cell.setCellValue("职位");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 7);  
         cell.setCellValue("入党时间");  
         cell.setCellStyle(style);  

		partyService ps=new partyService();
		List<party> p;
		try {
			p = ps.show();
		

        for (int i = 0; i < p.size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            party par = (party) p.get(i);  
            row.createCell((short) 0).setCellValue(par.getId());  
            row.createCell((short) 1).setCellValue(par.getName());  
            row.createCell((short) 2).setCellValue(par.getIdcard());  
            row.createCell((short) 3).setCellValue(par.getAddress());  
            row.createCell((short) 4).setCellValue(par.getSalary());  
            row.createCell((short) 5).setCellValue(par.getRole());  
            row.createCell((short) 6).setCellValue(par.getData());  
        }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OutputStream out = arg1.getOutputStream();
		String fileName = "导出数据"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+ ".xls"; // 生成文件名字
		// 设置响应头和下载保存的文件名
		arg1.setHeader("content-disposition","attachment;filename="+ new String(fileName.getBytes("gb2312"),"iso8859-1"));
		// 定义输出类型
		arg1.setContentType("application/vnd.ms-excel");
		arg1.setContentType("application/download");
		try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("export --------------------------");
		out.close();
		} 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
