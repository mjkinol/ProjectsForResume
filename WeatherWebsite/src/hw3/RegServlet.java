package hw3;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public RegServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeatherSQLFunc info = new WeatherSQLFunc();
		String Newusername = request.getParameter("Newuser");
		String Newpassword = request.getParameter("Newpass");
		String confirmPass = request.getParameter("confirmPass");
		String nextpage = "/index.jsp";
		
		request.setAttribute("userRegErrorEmpty", null);
		request.setAttribute("passRegErrorEmpty", null);
		request.setAttribute("passMatchError", null);
		request.setAttribute("usernameTaken", null);
		
		if(Newusername.trim().length() == 0|| Newusername == null) {
			request.setAttribute("userRegErrorEmpty", "Please enter an username.");
			nextpage = "/RegPage.jsp";
		}
		else if(Newpassword.trim().length() == 0|| Newpassword == null) {
			request.setAttribute("passRegErrorEmpty", "Please enter a password.");
			nextpage = "/RegPage.jsp";
		}
		else if(!Newpassword.equals(confirmPass)) {
			request.setAttribute("passMatchError", "The passwords do not match.");
			nextpage = "/RegPage.jsp";
		}
		else {
			boolean allowed = info.registerUser(Newusername, Newpassword);
			if(!allowed) {
				request.setAttribute("usernameTaken", "This username is already taken.");
				nextpage = "/RegPage.jsp";
			}
			else {
				nextpage = "/index.jsp";	
				HttpSession s=request.getSession();
				s.setAttribute("currentUser", Newusername);
			}
		}
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(nextpage);
		dispatch.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
