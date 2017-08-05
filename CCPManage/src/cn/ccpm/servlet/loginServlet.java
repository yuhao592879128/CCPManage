package cn.ccpm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ccpm.Service.usersService;
import cn.ccpm.beans.users;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		users u=new users();
		u.setUsersname(request.getParameter("usersname"));  
		u.setPassword(request.getParameter("password"));
		String code=(String)request.getSession().getAttribute("code");
		if(!request.getParameter("valicateCode").equals(code))
		{
			request.setAttribute("msg", "验证码错误！");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		}
		else {
		usersService us=new usersService();
		try {
			if(us.login(u).equals("success"))
			{
			HttpSession session = request.getSession(true);  
			session.setAttribute("user", u);  
			request.getRequestDispatcher("/index.jsp").forward(request,
						response);
			}
			else
			{
				request.setAttribute("msg", "用户名或错误！");

				request.getRequestDispatcher("/login.jsp").forward(request,
						response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
