package cn.ccpm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.dsna.util.images.ValidateCode;

/**
 * Servlet implementation class CAPTCHAServlet
 */
@WebServlet("/CAPTCHAServlet")
public class CAPTCHAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CAPTCHAServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 使用第三方生成验证码的jar包
		 * 	 1.拷贝ValidateCode.jar到工程lib目录
		 * 	 2.创建ValidateCode的对象
		 * 	 3.获取响应对象输出流
		 * 	 4.输出到浏览器
		 */
		//创建ValidateCode的对象
		//参数详解：1:图像宽度 2.图像高度 3.数字的格式 4.干扰线条数
		ValidateCode code = new ValidateCode(300,50,4,10);
        String str=code.getCode();  
		HttpSession session = request.getSession(true);  
		session.setAttribute("code", str);  
		code.write(response.getOutputStream());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
