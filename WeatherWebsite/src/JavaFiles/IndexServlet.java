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


@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public IndexServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeatherInfo3 myInfo = new WeatherInfo3();
		WeatherSQLFunc sqlFunc = new WeatherSQLFunc();
		String nextPage = "/SearchResults.jsp";
		String cityName = request.getParameter("cityName");
		ArrayList<String> cityData = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		
		if(request.getParameter("SignO") != null) {
			HttpSession s=request.getSession();
			s.setAttribute("currentUser", null);
			nextPage = "/index.jsp";
		}
		else {
			String nameGet = myInfo.getAPIWeatherDataLoc(cityName);
			if(nameGet != null)
			{
				String[] data = nameGet.split("\\|");
				String realPath = request.getServletContext().getRealPath("city.list.json");
				names = myInfo.getAPIWeatherDataLoc2(data[0], realPath);
				
				for(int i = 0; i < names.size(); i++) {
					String tempID = names.get(i).trim();
					tempID = tempID.substring(6, 13);
					String retVal = myInfo.getAPIWeatherDataID(tempID);
					cityData.add(retVal);
				}
				
				request.setAttribute("CurrentcityData", cityData);
				HttpSession s=request.getSession();
				String currUser = (String)s.getAttribute("currentUser");
				if(cityData != null && currUser != null) {
					String[] data2 = cityData.get(0).split("\\|");
					sqlFunc.insertProfileHistory(data2[0], currUser);
				}
			}
			else {
				cityData = null;
			}
		}

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
