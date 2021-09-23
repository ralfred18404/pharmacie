package com.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.dao.CategoryDao;
import com.dao.DirectorDao;
import com.dao.DrugsDao;
import com.dao.DrugsRequestDao;
import com.dao.PharmacieDao;
import com.dao.RequestDao;
import com.domain.Category;
import com.domain.Director;
import com.domain.Drugs;
import com.domain.DrugsRequest;
import com.domain.Pharmacie;
import com.domain.Request;

@ManagedBean
@SessionScoped
public class Access extends Message1{

	private Director director;
	private Drugs drugs;
	private Pharmacie pharmacie;
	private List<Drugs>drugList;
	private StreamedContent image = null;
	private Category category;
	private List<Category>categoryList;
	private List<Pharmacie>pharmacieList;
	private int boxes=0;private int boxes1 =0;
	private List<Request>requestList;
	private List<DrugsRequest>drugsRequestList,drugsRequestList1,hospitalDrugs,drugsRequestList2;
	private DrugsRequest drugsrequest;
	private Double totalAmount=0.0,totalAmount1=0.0;
	private String pharmacieName = "";

	
	public Access(){
		hospitalDrugs = new ArrayList<DrugsRequest>();
		drugsrequest = new DrugsRequest();
		drugsRequestList1 = new ArrayList<DrugsRequest>();
		requestList = new ArrayList<Request>();
		drugsRequestList  = new ArrayList<DrugsRequest>();
		pharmacieList = new ArrayList<Pharmacie>();
		categoryList = new ArrayList<Category>();
		category = new Category();
		drugList = new ArrayList<Drugs>();
		director = new Director();
		drugs = new Drugs();
		pharmacie = new Pharmacie();
	}

	public void login() {
		try {
			if(director.getPhoneNumber().equalsIgnoreCase("admin")&& director.getPassword().equals("123") ) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("ProvinceRegistration.xhtml");
			}else {
			Director d = new DirectorDao().getPhone(director.getPhoneNumber());
			if(d != null && d.getPassword() != null) {
				if(d.getPassword().matches(director.getPassword())) {
				if(d.getPharmacie() != null) {
					director = d;
					drugList = new DrugsDao().medecine(d.getPharmacie().getIds());
					drugsRequestList1 = new DrugsRequestDao().drugsRequest(director.getPharmacie().getIds());
					FacesContext.getCurrentInstance().getExternalContext().redirect("RegisterDrugs.xhtml");
				}else if(d.getHospital() != null) {
					director = d;
					pharmacieList = new PharmacieDao().pharmacieList(director.getHospital().getDistrict().getId());
					drugsRequestList1 = new DrugsRequestDao().medecine(director.getId());
					FacesContext.getCurrentInstance().getExternalContext().redirect("PharmacieListPage.xhtml");
				}
				}else {
					errorMessage("error", "wrong phone number and password...");
				}
			}else {
				errorMessage("error", "phone number and password does not exist...");
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error", e.getMessage());
		}
	}
	public void MyrequestPage()throws Exception{
		drugsRequestList1 = new DrugsRequestDao().medecine(director.getId());
		FacesContext.getCurrentInstance().getExternalContext().redirect("HViewMyRequest.xhtml");
	}
	public void pharmaciePageRequest()throws Exception{
		drugsRequestList1 = new DrugsRequestDao().drugsRequest(director.getPharmacie().getIds());
		FacesContext.getCurrentInstance().getExternalContext().redirect("PharmacieRequest.xhtml");
	}
	public void registerDrugs() {
		try {
			Director d = new DirectorDao().getOne(director.getId());
			drugs.setPharmacie(d.getPharmacie());
			drugs.setId(UUID.randomUUID().toString());
			new DrugsDao().record(drugs);
			successMessage("success", "new medicine is well added..");
			drugList = new DrugsDao().medecine(director.getPharmacie().getIds());
			drugs = new Drugs();
			image = null;
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error", e.getMessage());
		}
	}
	public void logout()throws Exception{
		director = new Director();
		FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
	}
	
	public void uploadPic(FileUploadEvent event) {
		try {
			UploadedFile upload = event.getFile();
			byte[] image1 = upload.getContents();
			drugs.setImage(image1);
			image = new DefaultStreamedContent(upload.getInputstream(), "image/jpeg");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void registerDrugsCategory() {
		try {
			Drugs d = new DrugsDao().getOne(drugs.getId());
			category.setId(UUID.randomUUID().toString());
			category.setDrugs(d);
			category.setStatus("Used");
			category.setRegistrationDate(new Date());
			new CategoryDao().record(category);
			category = new Category();
			categoryList = new CategoryDao().medecine(drugs.getId());
			successMessage("success", "new drugs Category is well added....");
			
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error", e.getMessage());
		}
	}
	public void drugsCategoryPage(Drugs drugs)throws Exception {
		this.drugs = drugs;
		categoryList = new CategoryDao().medecine(drugs.getId());
		FacesContext.getCurrentInstance().getExternalContext().redirect("DrugsCategory.xhtml");
	}
	public void pharmacyDrugsPage(Pharmacie pharmacie)throws Exception{
		this.pharmacie = pharmacie;
		drugList = new ArrayList<>();
		List<Drugs>list = new DrugsDao().medecine(pharmacie.getIds());
		for(Drugs d: list){
			Long count = new CategoryDao().getAll("From Category").stream().filter(i->i.getDrugs().getId().equals(d.getId()) 
					&& i.getStatus().equals("Used")).count();
			if(count != 0){
				drugList.add(d);
			}
		}
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("HMedecine.xhtml");
	}
	public void pharmaciePage()throws Exception{
		pharmacieList = new PharmacieDao().pharmacieList(director.getHospital().getDistrict().getId());
		FacesContext.getCurrentInstance().getExternalContext().redirect("PharmacieListPage.xhtml");
	}
	public StreamedContent getProPic() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String product11 = context.getExternalContext().getRequestParameterMap().get("image");
			Drugs p = new DrugsDao().getOne(product11);
			return new DefaultStreamedContent((new ByteArrayInputStream(p.getImage())));
		}
	}
	public void hospitalMedecinePage(Drugs drugs)throws Exception {
		categoryList = new CategoryDao().medecine(drugs.getId());
		FacesContext.getCurrentInstance().getExternalContext().redirect("HDrugsCategory.xhtml");
	}
	public void categoryDialog(Category ca) {
		this.category = ca;
	}
	public List<Category>drugsList(Drugs d){
		System.out.println("out putis----------------- ");
		return new CategoryDao().getAll("From Category").stream().
				filter(i->i.getDrugs().getId().equals(d.getId()) && i.getStatus().equals("Used")).collect(Collectors.toList());
	}
	public void updateDrugsCategory() {
		try {
			Category ca =new CategoryDao().getOne(category.getId());
			if(boxes >=0) {
				ca.setBox(ca.getBox()+boxes);
				new CategoryDao().update(ca);
				boxes =0;
				categoryList = new CategoryDao().medecine(drugs.getId());
				successMessage("success", "drugs category is well successfull updated..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error", e.getMessage());
		}
	}
	public void updateExpiredDrugs(Category cat) {
		try {
			Category ca =new CategoryDao().getOne(cat.getId());
			ca.setStatus("Expired");
			new CategoryDao().update(ca);
			categoryList = new CategoryDao().medecine(drugs.getId());
			successMessage("success", "drugs category is well successfull updated..");
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error", e.getMessage());
		}
	}
	public void updateDrugsRequest(DrugsRequest request) {
		List<DrugsRequest>list =  new DrugsRequestDao().getAll("from DrugsRequest");
		hospitalDrugs.stream().filter(i->i.getId().equals(request.getId())).forEach(b->{
			b.setStatus("Experied");
			new DrugsRequestDao().update(b);
		});
		hospitalDrugs =list.stream()
				.collect(Collectors.groupingBy(dis -> dis.getCategory().getId())).entrySet()
				.stream()
				.map(e -> e.getValue().stream()
						.reduce((f1, f2) -> new DrugsRequest(f1.getId(),f1.getQuantity(),
								f1.getReceivedQuantity() + f2.getReceivedQuantity(), f1.getCategory(),
								f1.getRequest())))
				.map(f -> f.get()).filter(i->i.getStatus().equals("New")).collect(Collectors.toList());
		
	}
	public void addList() {
		try {
			Category ca =new CategoryDao().getOne(category.getId());
			DrugsRequest r = new DrugsRequest();
			r.setId(UUID.randomUUID().toString());
			r.setCategory(ca);
			r.setReceivedQuantity(0);
			r.setQuantity(boxes);
			totalAmount +=ca.getAmount()*boxes;
			r.setAmountPaid(ca.getAmount()*boxes);
			drugsRequestList.add(r);
			successMessage("success", "drugs is well added to the list...");
			//drugsRequestList1 = new DrugsRequestDao().drugsRequest(director.getHospital().getId());
			boxes =0;
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error", e.getMessage());
		}
	}
	public void removeTocard(DrugsRequest r) {
		totalAmount -=r.getAmountPaid();
		drugsRequestList.remove(r);
	}
	
	public void makeRequest() {
		try {
			if(drugsRequestList.isEmpty() == false) {
			Director d = new DirectorDao().getOne(director.getId());
			Request re = new Request();
			re.setId(UUID.randomUUID().toString());
			re.setDirector(d);
			re.setStatus("sent");
			re.setRequestDate(new Date());
			new RequestDao().record(re);
			for(DrugsRequest dr : drugsRequestList) {
				Category c = new CategoryDao().getOne(dr.getCategory().getId());
				c.setBox(c.getBox()-dr.getQuantity());
				dr.setId(UUID.randomUUID().toString());
				dr.setRequest(re);
				new DrugsRequestDao().record(dr);
				new CategoryDao().update(c);
				pharmacieName = dr.getCategory().getDrugs().getPharmacie().getName();
			}
			drugsRequestList1 = drugsRequestList;
			totalAmount1 = totalAmount;
			successMessage("success", "new request is well sent...");
			drugsRequestList = new ArrayList<DrugsRequest>();
			totalAmount =0.0;
			}else {
				errorMessage("sorry", "Your list of drugs request is empty!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error", e.getMessage());
		}
	}
	public void drugsReqeustDialog(DrugsRequest re) {
		this.drugsrequest = re;
	}
	public void respondRequest() {
		try {
			DrugsRequest re = new DrugsRequestDao().getOne(drugsrequest.getId());
			Category ca = new CategoryDao().getOne(drugsrequest.getCategory().getId());
			int receive = re.getReceivedQuantity()+boxes1;
			int remain = re.getQuantity()-re.getReceivedQuantity();
			if(boxes1>=0) {
			if(remain>=boxes1) {
				re.setReceivedQuantity(receive);
				new DrugsRequestDao().update(re);
				ca.setBox(ca.getBox()-boxes1);
				ca.setGivenbox(ca.getGivenbox()+boxes1);
				new CategoryDao().update(ca);
				successMessage("success", "new drugs is well successfull received");
				drugsRequestList1 = new DrugsRequestDao().drugsRequest(director.getPharmacie().getIds());
				boxes =0;
			}else {
				errorMessage("error", "you provide boxes more than requested...");
			}
			}else {
				errorMessage("error", "please provide boxes not less than 0...");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error", e.getMessage());
		}
	}
	public void hospitalDrugsPage()throws Exception{
		List<DrugsRequest>list =  new DrugsRequestDao().getAll("from DrugsRequest");
		hospitalDrugs =list.stream()
				.collect(Collectors.groupingBy(dis -> dis.getCategory().getId())).entrySet()
				.stream()
				.map(e -> e.getValue().stream()
						.reduce((f1, f2) -> new DrugsRequest(f1.getId(),f1.getQuantity(),
								f1.getReceivedQuantity() + f2.getReceivedQuantity(), f1.getCategory(),
								f1.getRequest())))
				.map(f -> f.get()).collect(Collectors.toList());
		FacesContext.getCurrentInstance().getExternalContext().redirect("HStockMedecine.xhtml");
	}
	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public Drugs getDrugs() {
		return drugs;
	}

	public void setDrugs(Drugs drugs) {
		this.drugs = drugs;
	}

	public Pharmacie getPharmacie() {
		return pharmacie;
	}

	public void setPharmacie(Pharmacie pharmacie) {
		this.pharmacie = pharmacie;
	}

	public List<Drugs> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<Drugs> drugList) {
		this.drugList = drugList;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Pharmacie> getPharmacieList() {
		return pharmacieList;
	}

	public void setPharmacieList(List<Pharmacie> pharmacieList) {
		this.pharmacieList = pharmacieList;
	}

	public StreamedContent getImage() {
		return image;
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	public int getBoxes() {
		return boxes;
	}

	public void setBoxes(int boxes) {
		this.boxes = boxes;
	}

	public List<Request> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<Request> requestList) {
		this.requestList = requestList;
	}

	public List<DrugsRequest> getDrugsRequestList() {
		return drugsRequestList;
	}

	public void setDrugsRequestList(List<DrugsRequest> drugsRequestList) {
		this.drugsRequestList = drugsRequestList;
	}

	public List<DrugsRequest> getDrugsRequestList1() {
		return drugsRequestList1;
	}

	public void setDrugsRequestList1(List<DrugsRequest> drugsRequestList1) {
		this.drugsRequestList1 = drugsRequestList1;
	}

	public int getBoxes1() {
		return boxes1;
	}

	public void setBoxes1(int boxes1) {
		this.boxes1 = boxes1;
	}

	public DrugsRequest getDrugsrequest() {
		return drugsrequest;
	}

	public void setDrugsrequest(DrugsRequest drugsrequest) {
		this.drugsrequest = drugsrequest;
	}

	public List<DrugsRequest> getHospitalDrugs() {
		return hospitalDrugs;
	}

	public void setHospitalDrugs(List<DrugsRequest> hospitalDrugs) {
		this.hospitalDrugs = hospitalDrugs;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<DrugsRequest> getDrugsRequestList2() {
		return drugsRequestList2;
	}

	public void setDrugsRequestList2(List<DrugsRequest> drugsRequestList2) {
		this.drugsRequestList2 = drugsRequestList2;
	}

	public Double getTotalAmount1() {
		return totalAmount1;
	}

	public void setTotalAmount1(Double totalAmount1) {
		this.totalAmount1 = totalAmount1;
	}

	public String getPharmacieName() {
		return pharmacieName;
	}

	public void setPharmacieName(String pharmacieName) {
		this.pharmacieName = pharmacieName;
	}
	
	
}
