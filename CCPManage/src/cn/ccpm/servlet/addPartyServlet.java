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
        
		// 文件上传form 和 普通form 处理方式不同
		Map<String, String> parameterMap = new HashMap<String, String>();

		if (ServletFileUpload.isMultipartContent(request)) {
			// 1 工厂
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			// 2 工厂获得解析器
			ServletFileUpload fileUpload = new ServletFileUpload(
					diskFileItemFactory);
			// 3 设置解析器参数
			fileUpload.setFileSizeMax(1024 * 1024 * 5);// 5MB 文件不能超过5MB
			fileUpload.setHeaderEncoding("utf-8");// 处理上传附件名乱码
			// 4 解析器解析请求 request
			try {
				List<FileItem> list = fileUpload.parseRequest(request);
				// 5 遍历 list 获得每一个FileItem
				for (FileItem fileItem : list) {
					// 6 判断FileItem 是文件上传项、普通form项
					if (fileItem.isFormField()) {
						// 普通form 项
						// 获得name 和 value
						String name = fileItem.getFieldName();
						String value = fileItem.getString("utf-8");
						parameterMap.put(name, value);// 手动将普通form输入参数 封装自定义map
					} else {
						// 文件上传项
						// 判断用户是否上传文件
						String fileName = fileItem.getName();
						if (fileName == null || fileName.trim().equals("")) {
							// 没有上传图片
							throw new RuntimeException("必须要上传头像！");
						}
						// 获得真实文件名 --- 早期浏览器上传文件时 带有路径
						fileName = UploadUtils.subFileName(fileName);
						// 校验上传文件 格式 -- 根据文件扩展名
						String contentType = fileItem.getContentType();
						boolean isValid = UploadUtils.checkImgType(contentType);
						if (!isValid) {
							// 格式无效
							throw new RuntimeException("上传图片格式不正确的！");
						}
						// 唯一UUID 随机文件名
						String uuidname = UploadUtils
								.generateUUIDName(fileName);

						// 分散目录生成
						String randomDir = UploadUtils
								.generateRandomDir(uuidname);
						// 创建随机目录
						File dir = new File(getServletContext().getRealPath(
								"/img/upload" + randomDir));
						dir.mkdirs();

						// 文件上传
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

						// 删除临时文件
						fileItem.delete();

						// 添加自动生成小图 代码
						PicUtils picUtils = new PicUtils(getServletContext()
								.getRealPath("/img/upload" + randomDir)
								+ "/" + uuidname);
						picUtils.resize(75, 75);

						// 将img 保存路径 存放parameterMap
						parameterMap.put("img", "/img/upload" + randomDir + "/"
								+ uuidname);
					}
				}

				// 保存 信息 到数据库 --- 封装JavaBean
				party p=new party();				
				BeanUtils.populate(p, parameterMap);
				// 传递javabean 给业务层
				partyService ps=new partyService();
				 try {
						if(ps.add(p).equals("success"))
							{
							request.setAttribute("msg", "上传成功！");
							request.getRequestDispatcher("/addParty.jsp").forward(request,
									response);
							}
						else
							{
								request.setAttribute("msg", "添加失败！");
								request.getRequestDispatcher("/addParty.jsp").forward(request,
										response);
							}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			        
				// 决定跳转页面

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
			throw new RuntimeException("添加商品，必须使用文件上传form 表单！");
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
