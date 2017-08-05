package cn.ccpm.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.ccpm.Service.partyService;
import cn.ccpm.beans.party;
import cn.ccpm.utils.PicUtils;
import cn.ccpm.utils.UploadUtils;
/**
 * Servlet implementation class addPartyServlet
 */
@WebServlet("/addPartyServlet")
public class addPartyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addPartyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		// �ļ��ϴ�form �� ��ͨform ����ʽ��ͬ
		Map<String, String> parameterMap = new HashMap<String, String>();

		if (ServletFileUpload.isMultipartContent(request)) {
			// 1 ����
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			// 2 ������ý�����
			ServletFileUpload fileUpload = new ServletFileUpload(
					diskFileItemFactory);
			// 3 ���ý���������
			fileUpload.setFileSizeMax(1024 * 1024 * 5);// 5MB �ļ����ܳ���5MB
			fileUpload.setHeaderEncoding("utf-8");// �����ϴ�����������
			// 4 �������������� request
			try {
				List<FileItem> list = fileUpload.parseRequest(request);
				// 5 ���� list ���ÿһ��FileItem
				for (FileItem fileItem : list) {
					// 6 �ж�FileItem ���ļ��ϴ����ͨform��
					if (fileItem.isFormField()) {
						// ��ͨform ��
						// ���name �� value
						String name = fileItem.getFieldName();
						String value = fileItem.getString("utf-8");
						parameterMap.put(name, value);// �ֶ�����ͨform������� ��װ�Զ���map
					} else {
						// �ļ��ϴ���
						// �ж��û��Ƿ��ϴ��ļ�
						String fileName = fileItem.getName();
						if (fileName == null || fileName.trim().equals("")) {
							// û���ϴ�ͼƬ
							throw new RuntimeException("����Ҫ�ϴ�ͷ��");
						}
						// �����ʵ�ļ��� --- ����������ϴ��ļ�ʱ ����·��
						fileName = UploadUtils.subFileName(fileName);
						// У���ϴ��ļ� ��ʽ -- �����ļ���չ��
						String contentType = fileItem.getContentType();
						boolean isValid = UploadUtils.checkImgType(contentType);
						if (!isValid) {
							// ��ʽ��Ч
							throw new RuntimeException("�ϴ�ͼƬ��ʽ����ȷ�ģ�");
						}
						// ΨһUUID ����ļ���
						String uuidname = UploadUtils
								.generateUUIDName(fileName);

						// ��ɢĿ¼����
						String randomDir = UploadUtils
								.generateRandomDir(uuidname);
						// �������Ŀ¼
						File dir = new File(getServletContext().getRealPath(
								"/img/upload" + randomDir));
						dir.mkdirs();

						// �ļ��ϴ�
						InputStream in = new BufferedInputStream(fileItem
								.getInputStream());
						OutputStream out = new BufferedOutputStream(
								new FileOutputStream(new File(dir, uuidname)));
						int b;
						while ((b = in.read()) != -1) {
							out.write(b);
						}
						in.close();
						out.close();

						// ɾ����ʱ�ļ�
						fileItem.delete();

						// ����Զ�����Сͼ ����
						PicUtils picUtils = new PicUtils(getServletContext()
								.getRealPath("/img/upload" + randomDir)
								+ "/" + uuidname);
						picUtils.resize(75, 75);

						// ��img ����·�� ���parameterMap
						parameterMap.put("img", "/img/upload" + randomDir + "/"
								+ uuidname);
					}
				}

				// ���� ��Ϣ �����ݿ� --- ��װJavaBean
				party p=new party();				
				BeanUtils.populate(p, parameterMap);
				// ����javabean ��ҵ���
				partyService ps=new partyService();
				 try {
						if(ps.add(p).equals("success"))
							{
							request.setAttribute("msg", "�ϴ��ɹ���");
							request.getRequestDispatcher("/addParty.jsp").forward(request,
									response);
							}
						else
							{
								request.setAttribute("msg", "���ʧ�ܣ�");
								request.getRequestDispatcher("/addParty.jsp").forward(request,
										response);
							}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			        
				// ������תҳ��

			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			throw new RuntimeException("�����Ʒ������ʹ���ļ��ϴ�form ����");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
