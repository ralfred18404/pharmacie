package com.controller;

import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.dao.DirectorDao;
import com.dao.DrugsRequestDao;
import com.dao.HospitalDao;
import com.dao.PharmacieDao;
import com.domain.Director;
import com.domain.DrugsRequest;
import com.domain.Hospital;
import com.domain.Pharmacie;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

@ManagedBean
@SessionScoped
public class Report extends Message1 {
	
	private String hospital ="";
	private String pharmacie ="";
	private List<Pharmacie>pharmacieList = new PharmacieDao().getAll("from Pharmacie");
	private List<Hospital>hospitalList = new HospitalDao().getAll("from Hospital");
	private String username ="";
	
	
	public void hospitalPharmaciePage()throws Exception{
		pharmacieList = new PharmacieDao().getAll("from Pharmacie");
		hospitalList = new HospitalDao().getAll("from Hospital");
		FacesContext.getCurrentInstance().getExternalContext().redirect("adminreportForm.xhtml");
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Pharmacie> getPharmacieList() {
		return pharmacieList;
	}

	public void setPharmacieList(List<Pharmacie> pharmacieList) {
		this.pharmacieList = pharmacieList;
	}


	public List<Hospital> getHospitalList() {
		return hospitalList;
	}


	public void setHospitalList(List<Hospital> hospitalList) {
		this.hospitalList = hospitalList;
	}


	public String getHospital() {
		return hospital;
	}


	public void setHospital(String hospital) {
		this.hospital = hospital;
	}


	public String getPharmacie() {
		return pharmacie;
	}


	public void setPharmacie(String pharmacie) {
		this.pharmacie = pharmacie;
	}


	public void reportHospital() {

		try {
			Hospital h = new HospitalDao().getOne(hospital);
			Director d = new DirectorDao().getHospital(hospital);
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			String fileName = "HospitalReport.pdf";
			String contentType = "application/pdf";
			ec.responseReset();
			ec.setResponseContentType(contentType);
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
			OutputStream out = ec.getResponseOutputStream();
			Document doc = new Document();
			PdfWriter.getInstance(doc, out);
			LineSeparator ls = new LineSeparator();
			doc.open();
			Image img = Image.getInstance("C:\\Users\\andym\\Desktop\\Pharmacie\\src\\main\\webapp\\resources\\demo\\images\\logo2.JPG");
			img.scaleAbsolute(150f, 100f);
			Paragraph header = new Paragraph();
			header.add(img);
			header.setAlignment(Image.ALIGN_LEFT);
			doc.add(header);
			doc.add(new Chunk(ls));
			doc.add(new Paragraph("                                          "));
			//Mission m = new MissionDao().getOne(mission.getMissionID());
			
			Paragraph p = new Paragraph("Drugs requested by "+h.getName()+" Hospital",
					FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.DARK_GRAY));
			p.setAlignment(Element.ALIGN_LEFT);
			doc.add(p);
			doc.add(new Paragraph("                                          "));
			Paragraph pp = new Paragraph("Director Name: "+d.getFirstName()+" "+ d.getLastName(),
					FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD, BaseColor.DARK_GRAY));
			pp.setAlignment(Element.ALIGN_LEFT);
			doc.add(pp);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));

			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			doc.add(table);
			// BaseColor color = new BaseColor(10, 113, 156);

			Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.BLACK);
			
			
			PdfPCell assignedBy = new PdfPCell(new Phrase("Drugs \n Model\n\n", font0));
			assignedBy.setBorder(Rectangle.BOTTOM);
			
			table.addCell(assignedBy);
			PdfPCell givenDate = new PdfPCell(new Phrase("Drugs \n Grammes\n\n", font0));
			givenDate.setBorder(Rectangle.BOTTOM);
			table.addCell(givenDate);
			

			PdfPCell reservationDate = new PdfPCell(new Phrase("Requested \n Boxes\n\n", font0));
			reservationDate.setBorder(Rectangle.BOTTOM);
			table.addCell(reservationDate);
			
			PdfPCell quantity = new PdfPCell(new Phrase("Received \n Boxes\n\n", font0));
			quantity.setBorder(Rectangle.BOTTOM);
			table.addCell(quantity);
			
			PdfPCell provider = new PdfPCell(new Phrase("Pharmacie \n Provider\n\n", font0));
			provider.setBorder(Rectangle.BOTTOM);
			table.addCell(provider);
			
			
			

			for (DrugsRequest emp : new DrugsRequestDao().drugsReportH(hospital)) {
				PdfPCell status = new PdfPCell(new Phrase(emp.getCategory().getDrugs().getName() +" "+emp.getCategory().getName()));
				
				table.addCell(status);
				PdfPCell title = new PdfPCell(new Phrase(emp.getCategory().getGramme()));
				
				table.addCell(title);
				
				PdfPCell start1 = new PdfPCell(
						new Phrase(emp.getQuantity()+" "));
				
				table.addCell(start1);

				PdfPCell start = new PdfPCell(
						new Phrase(emp.getReceivedQuantity()+" "));
				
				table.addCell(start);

				PdfPCell star = new PdfPCell(
						new Phrase(emp.getCategory().getDrugs().getPharmacie().getName()));
				
				table.addCell(star);
				

			}

			doc.add(table);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));
			String dd = new SimpleDateFormat("dd/MMM/yyyy").format(new Date());
			Paragraph printedOn = new Paragraph("Printed On:" + dd+"\n\n Printed By: "+username);
			printedOn.setAlignment(Element.ALIGN_RIGHT);
			doc.add(printedOn);
			doc.close();

			fc.responseComplete();

		} catch (Exception ex) {
			System.err.println("Error:" + ex.getMessage());
			errorMessage("Error:", ex.getMessage());
		}
	}
}
