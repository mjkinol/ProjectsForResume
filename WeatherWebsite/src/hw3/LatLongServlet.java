package hw3;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LatLongServlet")
public class LatLongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LatLongServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeatherInfo3 myInfo = new WeatherInfo3();
		WeatherSQLFunc sqlFunc = new WeatherSQLFunc();
		String nextPage = "/SearchResults.jsp";
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		ArrayList<String> cityData = new ArrayList<String>();
		request.setAttribute("CurrentcityData", cityData);
		
		if(request.getParameter("SignO") != null) {
			HttpSession s=request.getSession();
			s.setAttribute("currentUser", null);
			nextPage = "/index.jsp";
		}
		else {
			cityData = myInfo.getAPIWeatherDataLatLon(lat, lng);
			request.setAttribute("CurrentcityData", cityData);
			HttpSession s=request.getSession();
			String currUser = (String)s.getAttribute("currentUser");
			if(cityData != null && currUser != null) {
				String[] data = cityData.get(0).split("\\|");
				String histLat = "("+data[6]+", "+data[7]+")";
				sqlFunc.insertProfileHistory(histLat, currUser);
			}
		}	

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
