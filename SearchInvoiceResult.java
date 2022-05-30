package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SearchInvoiceResult extends HttpServlet {
	Connection conn = null;
    static Gson gson = new Gson();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    long invoice_id = Integer.parseInt(request.getParameter("search"));
		    System.out.print(invoice_id);     	// what is ID here it might be invoice_id?
	     	String search_query = "SELECT * FROM Kshitij where invoice_id = "+invoice_id;
	     	try {
	     		if(conn == null) {
	     			conn  = DatabaseConnection.con();
		        }
		     	Statement st = conn.createStatement();
	     	    ResultSet search_data = st.executeQuery(search_query);
		     	PrintWriter pw = response.getWriter();
		     	List<Invoiceifo> result = new ArrayList<>();  
		     	while(search_data.next()) {
		     		//haven't fixed the number inside bracket yet
		     		Invoiceifo ifo = new Invoiceifo();
		     		ifo.setName(search_data.getString(1));
		     		ifo.setCust_no(search_data.getString(2));
		     		//did Double instead of setInt
		     		ifo.setInvoice_id(search_data.getDouble(3));
		     		//did Double instead of setString
		     		ifo.setAmount(search_data.getDouble(4));
		     		//did Date instead of setString
		     		ifo.setDue_date(search_data.getDate(5));
		     		ifo.setPred_date(search_data.getDate(6));
		     		ifo.setNotes(search_data.getString(7));
		     		result.add(ifo);
		        }
		     	String userjson = gson.toJson(result);
				response.setCharacterEncoding("UTF-8");
				pw.write(userjson);
				pw.close();
				conn.close();
				}
	     	    catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	     	    catch (SQLException e) {
	     	    	e.printStackTrace();
			    }
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	  ArrayList<Integer> arr = new ArrayList<>();
		    System.out.println("dell");
			String str = request.getReader().lines().collect(Collectors.joining());
			JsonObject jo = new JsonParser().parse(str).getAsJsonObject(); 
			JsonArray del_ids = jo.get("ids").getAsJsonArray();
			System.out.println(del_ids);
			String all_in = "";
			for(int i=0;i<del_ids.size();i++) {
				if(i==0) {
					all_in+=del_ids.get(i);
			    }
				else {
					all_in+=","+del_ids.get(i);
				}
		    }
			String delete_query = "DELETE FROM Kshitij where id IN("+all_in+")";
			Statement state;
			try {
				state = conn.createStatement();
			    state.executeUpdate(delete_query);

		    }
			catch (SQLException e) {
				// TODO Auto-generated catch block
			    e.printStackTrace();
		    }

	}
}
