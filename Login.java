package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		try {
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "newYear2020");
		String userid=request.getParameter("userid");
		String pass=request.getParameter("upass");
		Statement st=conn.createStatement();
		HttpSession ss = request.getSession(true);
		ss.setAttribute("username", userid);
		Cookie ck=new Cookie("username",userid);
		response.addCookie(ck);
				
		
		String sql =("select password from user_details where username='" + userid + "'");
		ResultSet rs = st.executeQuery(sql);
		String msg1 = "Please Sign-up to Log-In";
		request.setAttribute("msg",msg1);
		/*if(!rs.next())
		{
			RequestDispatcher rd= request.getRequestDispatcher("needsi.jsp");
			rd.forward(request,response);
		}
		if()
		{
			System.out.println("entered else");*/
			while(rs.next())
			{
				
				String res1=rs.getString("password");
				res1=res1.trim();
				pass=pass.trim();
				//System.out.println(res1);
				//System.out.println(pass);
				System.out.println( "somethig is ------> " + res1.equals(pass));
				if(res1.equals(pass)) 
				{
					System.out.println("went to serve");
					request.setAttribute("username", userid);
					RequestDispatcher rd= request.getRequestDispatcher("main1.jsp");
					rd.forward(request,response);

				}
				else
				{
					System.out.println("went to needs");
					RequestDispatcher rd= request.getRequestDispatcher("needsi.jsp");
					rd.forward(request,response);

				}
			}
			
		
		/*request.setAttribute("again"," pls enter correct details");
		RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);*/
		}
		catch(Exception e) {
			e.printStackTrace();
			}
		
	}
}
