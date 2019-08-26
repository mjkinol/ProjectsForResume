package hw3;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SRServlet")
public class SRServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SRServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WeatherInfo3 myInfo = new WeatherInfo3();
		String realPath = request.getServletContext().getRealPath("/weather.txt");
		String nextPage = "/DetailWA.jsp";
		String cityName1 = request.getParameter("name");
		String FinalCityData = myInfo.getAPIWeatherDataID(cityName1);
		
		request.setAttribute("FinalCityData", FinalCityData);
				
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
