package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DummyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter print_writer;
    static Gson gson = new Gson();
    public void init() {
 	System.out.println("in");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String off_set = request.getParameter("skip");
		try {
			Connection connection = DatabaseConnection.con();
			Statement statement = connection.createStatement();
			//add
			if(request.getParameter("search").equals("true")) {
				 long invoice_id = Long.parseLong(request.getParameter("invoiceId"));
				    System.out.println(invoice_id);     	// what is ID here it might be invoice_id?
				  //  String search_query =    "select * from Kshitij limit 10 offset " + 10;
				  //  String query = "select * from Kshitij limit 10 offset " + off_set;
				  //ResultSet reslt = statement.executeQuery(query);
			    	String search_query = "select * from Kshitij where invoice_id = '"+invoice_id+"'";
			    	ResultSet searchreslt = statement.executeQuery(search_query);
			    	System.out.println(search_query);
			     	try {
			     		
				     	Statement st = connection.createStatement();
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
						connection.close();
						}
			     	    catch (SQLException e) {
			     	    	e.printStackTrace();
					    }
			}else {
			int off_set = Integer.parseInt(request.getParameter("skip"));
			String query = "select * from Kshitij limit 10 offset " + off_set;
			ResultSet reslt = statement.executeQuery(query);
			//add shayd
			print_writer = response.getWriter();
			List<Invoiceifo> list = new ArrayList<>();
			while (reslt.next()) {
				Invoiceifo ifo = new Invoiceifo();
				ifo.setName(reslt.getString(4));
				ifo.setCust_no(reslt.getString(3));
				ifo.setAmount(reslt.getDouble(15));
				ifo.setInvoice_id(reslt.getDouble(18));
				ifo.setDue_date(reslt.getDate(11));
				ifo.setPred_date(reslt.getDate(20));
				ifo.setNotes(reslt.getString(21));
				list.add(ifo);
			}

			String gson = new Gson().toJson(list);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(gson);
			//add
			}
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String str = request.getReader().lines().collect(Collectors.joining());
			JsonObject json = new JsonParser().parse(str).getAsJsonObject();
			String cust_name = json.get("cust_name").getAsString();
			String cust_num = json.get("cust_num").getAsString();
			int invoice_num = Integer.parseInt(json.get("invoice_num").getAsString());
			double invoice_amt = Double.parseDouble(json.get("invoice_amt").getAsString());
			
			//String due = dt.toString();
			java.sql.Date due = java.sql.Date.valueOf(json.get("due").getAsString());
			String note = json.get("note").getAsString();
			//String query = "INSERT INTO Kshitij values(default," + null + ",'" + cust_num + "','" + cust_name + "',"
			//		+ null + "," + null + "," + null + "," + null + "," + null + "," + null + ",'" + due + "'," + null
			//		+ "," + null + "," + null + ",'" + invoice_amt + "'," + null + "," + null + ",'" + invoice_num
			//		+ "'," + null + "," + null + ",'" + note + "')";
			String query = "INSERT INTO Kshitij values(default,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			Connection conn = DatabaseConnection.con();
			PreparedStatement prep = conn.prepareStatement(query);
			prep.setNull(1, Types.NULL);
			prep.setString(2, cust_num);
			prep.setString(3, cust_name);
			prep.setNull(4, Types.NULL);
			prep.setNull(5, Types.NULL);
			prep.setNull(6, Types.NULL);
			prep.setNull(7, Types.NULL);
			prep.setNull(8, Types.NULL);
			prep.setNull(9, Types.NULL);
			prep.setDate(10, due);
			prep.setNull(11, Types.NULL);
			prep.setNull(12, Types.NULL);
			prep.setNull(13, Types.NULL);
			prep.setDouble(14, invoice_amt);
			prep.setNull(15, Types.NULL);
			prep.setNull(16, Types.NULL);
			prep.setDouble(17, invoice_num);
			prep.setNull(18, Types.NULL);
			prep.setNull(19, Types.NULL);
			prep.setString(20, note);
			prep.addBatch();
			prep.executeBatch();
	//		Statement st = conn.createStatement();
//			st.executeUpdate(query);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
