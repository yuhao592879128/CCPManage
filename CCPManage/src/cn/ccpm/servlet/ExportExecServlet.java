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
		// ����һ��������
		System.out.println("ser");
		HSSFWorkbook  workbook = new HSSFWorkbook ();  
		// ����һ�����
        HSSFSheet sheet = workbook.createSheet("��Ա��Ϣ��");
        // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short 
        HSSFRow row = sheet.createRow((int) 0);  
        CellStyle cellStyle =workbook.createCellStyle();  
        // ������Щ��ʽ  
        HSSFCellStyle style = workbook.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ  
        row.createCell(0).setCellStyle(cellStyle);  
		 Cell cell = row.createCell((short) 0);
		 cell.setCellValue("id");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 1);  
         cell.setCellValue("����");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 2);  
         cell.setCellValue("���֤");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 3);  
         cell.setCellValue("��ַ");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 4);  
         cell.setCellValue("����");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 5);  
         cell.setCellValue("ְλ");  
         cell.setCellStyle(style);  
         cell = row.createCell((short) 7);  
         cell.setCellValue("�뵳ʱ��");  
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
		String fileName = "��������"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+ ".xls"; // �����ļ�����
		// ������Ӧͷ�����ر�����ļ���
		arg1.setHeader("content-disposition","attachment;filename="+ new String(fileName.getBytes("gb2312"),"iso8859-1"));
		// �����������
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
