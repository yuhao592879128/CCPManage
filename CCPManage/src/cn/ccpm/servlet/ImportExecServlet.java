package cn.ccpm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
@WebServlet("/ImportExecServlet")
public class ImportExecServlet extends HttpServlet {

	private static final long serialVersionUID = -3113944177409704444L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> values=new ArrayList<String>();
		String name = "";
		// �����ϴ���execl�ļ�
		DiskFileItemFactory factory = new DiskFileItemFactory();
		String path = req.getSession().getServletContext().getRealPath("/execl");
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> list = (List<FileItem>) upload.parseRequest(req);
			for (FileItem item : list) {
				if (item.isFormField()) {
				} else {
					// ��ȡ�ϴ��ļ���
					name = item.getName();
					// ��ȡ��׺��
					name = name.substring(name.lastIndexOf("."));
					// ����ʱ���ȡ������
					name = new Date().getTime() + name;
					// �ֶ�д��
					OutputStream out = new FileOutputStream(new File(path, name));
					InputStream in = item.getInputStream();
					int length = 0;
					byte[] buf = new byte[1024];
					System.out.println("��ȡ�ϴ��ļ����ܹ���������" + item.getSize());
					// in.read(buf) ÿ�ζ��������ݴ���� buf ������
					while ((length = in.read(buf)) != -1) {
						// �� buf ������ ȡ������ д�� ���������������
						out.write(buf, 0, length);
					}
					in.close();
					out.close();
				}
			}
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream is = new FileInputStream(path + File.separator + name);
		Workbook wb = null;
		if(name.endsWith("xlsx")){
			wb = new XSSFWorkbook(is);
		}else{
			wb = new HSSFWorkbook(is); 
		}
		for (int j = 0; j < wb.getNumberOfSheets(); j++) {
			Sheet sheet = wb.getSheetAt(j);
			if (sheet == null) {
				break;
			}
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				// ÿ��
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				String s=getValue(row.getCell(0))+","+getValue(row.getCell(1))+","+getValue(row.getCell(2))+","+getValue(row.getCell(3))+","+getValue(row.getCell(4));
				System.out.println(getValue(row.getCell(0))+","+getValue(row.getCell(1))+","+getValue(row.getCell(2))+","+getValue(row.getCell(3))+","+getValue(row.getCell(4)));
				values.add(s);
			}
		}
		is.close();
		req.setAttribute("list", values);
		req.getRequestDispatcher("/poi/show.jsp").forward(req, resp);
	}

	// poi
	private String getValue(Cell cell) {
		String value = "";
		if (cell == null)
			return value;

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_FORMULA:
			break;
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_NUMERIC:
			value = String.format("%.0f", cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		default:
			break;
		}
		return value;
	}
}
