package com.higradius;
//import java.sql.Date;
import java.util.Date;

public class Invoiceifo {
	
	public String name;
	public String cust_no;
	public Double invoice_id;
	public double amount;
	public Date due_date;
	public Date pred_date;
	public String notes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	public Double getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(Double invoice_id) {
		this.invoice_id = invoice_id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public Date getPred_date() {
		return pred_date;
	}
	public void setPred_date(Date pred_date) {
		this.pred_date = pred_date;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
