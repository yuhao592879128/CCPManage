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
		 * ʹ�õ�����������֤���jar��
		 * 	 1.����ValidateCode.jar������libĿ¼
		 * 	 2.����ValidateCode�Ķ���
		 * 	 3.��ȡ��Ӧ���������
		 * 	 4.����������
		 */
		//����ValidateCode�Ķ���
		//������⣺1:ͼ���� 2.ͼ��߶� 3.���ֵĸ�ʽ 4.����������
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
