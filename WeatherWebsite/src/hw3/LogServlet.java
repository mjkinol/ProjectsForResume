package hw3;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogServlet")
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public LogServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeatherSQLFunc info = new WeatherSQLFunc();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nextpage = "/index.jsp";
		
		if(username.trim().length() == 0|| username == null) {
			request.setAttribute("userLogError", "This user does not exist");
			nextpage = "/LoginPage.jsp";
		}
		else if(password.trim().length() == 0|| password == null) {
			request.setAttribute("passLogError", "Incorrect password");
			nextpage = "/LoginPage.jsp";
		}
		else {
			String value = info.loginValidation(username, password);
			if(value.equals("userWrong")) {
				request.setAttribute("userLogError", "This user does not exist");
				nextpage = "/LoginPage.jsp";
			}
			else if(value.equals("passWrong")) {
				request.setAttribute("passLogError", "Incorrect password");
				nextpage = "/LoginPage.jsp";
			}
			else if(value.equals("valid")){
				nextpage = "/index.jsp";	
				HttpSession s=request.getSession();
				s.setAttribute("currentUser", username);
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
