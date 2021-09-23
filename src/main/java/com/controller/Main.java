package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.dao.DirectorDao;
import com.dao.DistrictDao;
import com.dao.HospitalDao;
import com.dao.PharmacieDao;
import com.dao.ProvinceDao;
import com.domain.Category;
import com.domain.Director;
import com.domain.District;
import com.domain.Drugs;
import com.domain.Hospital;
import com.domain.Pharmacie;
import com.domain.Province;

@ManagedBean
@SessionScoped
public class Main extends Message1 {

	private Province province;
	private District district;
	private Pharmacie pharmacie;
	private Hospital hospital;
	private Drugs drugs;
	private Category category;
	private List<Province> provinceList;
	private List<District> districtList;
	private List<Pharmacie> pharmacieList;
	private List<Drugs> drugsList;
	private List<Hospital> hospitalList;
	private List<Category> categoryList;
	private Director director, director1, director2;
	private String confirm = "";

	public Main() {
		director2 = new Director();
		director = new Director();
		director1 = new Director();
		districtList = new ArrayList<>();
		pharmacieList = new ArrayList<>();
		provinceList = new ProvinceDao().getAll("from Province");
		province = new Province();
		district = new District();
		pharmacie = new Pharmacie();
		hospital = new Hospital();
	}

	public void saveProvince() {
		try {
			province.setId(UUID.randomUUID().toString());
			new ProvinceDao().record(province);
			successMessage("success", "province is well successfull saved...");
			province = new Province();
			provinceList = new ProvinceDao().getAll("from Province");
		} catch (Exception e) {
			errorMessage("error", e.getMessage());
		}
	}

	public void districtPage(Province pr) throws Exception {
		this.province = pr;
		districtList = new DistrictDao().districtList(pr.getId());
		FacesContext.getCurrentInstance().getExternalContext().redirect("DistrictRegistration.xhtml");
	}

	public void saveDistrict() {
		try {
			Province pr = new ProvinceDao().getOne(province.getId());
			district.setProvince(pr);
			district.setId(UUID.randomUUID().toString());
			new DistrictDao().record(district);
			successMessage("success", "district is well successfull saved...");
			district = new District();
			districtList = new DistrictDao().districtList(province.getId());
		} catch (Exception e) {
			errorMessage("error", e.getMessage());
		}
	}

	public void savePharmacie() {
		try {
			District pr = new DistrictDao().getOne(district.getId());
			pharmacie.setDistrict(pr);
			pharmacie.setIds(UUID.randomUUID().toString());
			new PharmacieDao().record(pharmacie);
			successMessage("success", "pharmacie is well successfull saved...");
			pharmacie = new Pharmacie();
			pharmacieList = new PharmacieDao().pharmacieList(district.getId());
		} catch (Exception e) {
			errorMessage("error", e.getMessage());
		}
	}

	public void pharmacieDialog(Pharmacie pharmacie) {
		this.pharmacie = pharmacie;
	}

	public void hospitalDialog(Hospital hospital) {
		this.hospital = hospital;
	}

	public void saveDirectorP() {
		try {
			Director d = new DirectorDao().getPhone(director2.getPhoneNumber());
			if (d == null) {
				if (validatePhone(director2.getPhoneNumber())) {
					if (validateId(director2.getId(),
							new SimpleDateFormat("dd/MM/yyyy").format(director2.getDateOfbirth()))) {

						Pharmacie pha = new PharmacieDao().getOne(pharmacie.getIds());
						director2.setPharmacie(pha);
						new DirectorDao().record(director2);
						director2 = new Director();
						pharmacie = new Pharmacie();
						pharmacieList = new PharmacieDao().pharmacieList(district.getId());
						successMessage("success", "Director well successfull saved...");
					} else {
						errorMessage("error",
								"National id and date of birth are wrong! please verify before you save...");
					}
				} else {
					errorMessage("error", "phone number must be started with 078/073/072..");
				}
			} else {
				errorMessage("error", "phone number is arleady used..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error----------------------------");
			errorMessage("error", e.getMessage());
		}
	}

	public void saveDirectorH() {
		try {
			Director d = new DirectorDao().getPhone(director1.getPhoneNumber());
			if (d == null) {
				if (validatePhone(director1.getPhoneNumber())) {
					if (validateId(director1.getId(),
							new SimpleDateFormat("dd/MM/yyyy").format(director1.getDateOfbirth()))) {
						Hospital ho = new HospitalDao().getOne(hospital.getId());
						director1.setHospital(ho);
						;
						new DirectorDao().record(director1);
						director1 = new Director();
						hospitalList = new HospitalDao().hospitalList(district.getId());
						hospital = new Hospital();
						successMessage("success", "Director well successfull saved...");
					} else {
						errorMessage("error",
								"National id and date of birth are wrong! please verify before you save...");
					}
				} else {
					errorMessage("error", "phone number must be started with 078/073/072..");
				}
			} else {
				errorMessage("error", "phone number is arleady used..");
			}
		} catch (Exception e) {
			errorMessage("error", e.getMessage());
		}
	}

	public void hospitalPage(District district) throws Exception {
		this.district = district;
		hospitalList = new HospitalDao().hospitalList(district.getId());
		pharmacieList = new PharmacieDao().pharmacieList(district.getId());
		FacesContext.getCurrentInstance().getExternalContext().redirect("PharmacieHospitalRegistration.xhtml");
	}

	public Director findDirectorP(Pharmacie n) {
		Director dir = new DirectorDao().getPharmacie(n.getIds());
		return dir;
	}

	public Director findDirectorH(Hospital n) {
		Director dir = new DirectorDao().getHospital(n.getId());
		return dir;
	}

	public void directorDialogP(Pharmacie di) {
		director = new DirectorDao().getPharmacie(di.getIds());
	}

	public void directorDialogH(Hospital di) {
		director = new DirectorDao().getHospital(di.getId());
	}

	public void saveHospital() {
		try {
			District pr = new DistrictDao().getOne(district.getId());
			hospital.setDistrict(pr);
			hospital.setId(UUID.randomUUID().toString());
			new HospitalDao().record(hospital);
			successMessage("success", "hospital is well successfull saved...");
			hospital = new Hospital();
			hospitalList = new HospitalDao().hospitalList(district.getId());
		} catch (Exception e) {
			errorMessage("error", e.getMessage());
		}
	}

	public void createAccount() {
		try {
			Director d = new DirectorDao().getPhone(director.getPhoneNumber());
			if (director.getPassword().equalsIgnoreCase(confirm)) {
				if (d != null) {
					if (d.getPassword() == null) {
						d.setPassword(director.getPassword());
						new DirectorDao().update(d);
						successMessage("success", "new account is well created....");
						director = new Director();
						confirm = "";
					} else {
						errorMessage("error", "you are arleady created an account...");
					}
				} else {
					errorMessage("error", "your phone number does not exist...");
				}
			} else {
				errorMessage("error", "please re-type your password well...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("error", e.getMessage());
		}
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Pharmacie getPharmacie() {
		return pharmacie;
	}

	public void setPharmacie(Pharmacie pharmacie) {
		this.pharmacie = pharmacie;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Drugs getDrugs() {
		return drugs;
	}

	public void setDrugs(Drugs drugs) {
		this.drugs = drugs;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}

	public List<District> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<District> districtList) {
		this.districtList = districtList;
	}

	public List<Pharmacie> getPharmacieList() {
		return pharmacieList;
	}

	public void setPharmacieList(List<Pharmacie> pharmacieList) {
		this.pharmacieList = pharmacieList;
	}

	public List<Drugs> getDrugsList() {
		return drugsList;
	}

	public void setDrugsList(List<Drugs> drugsList) {
		this.drugsList = drugsList;
	}

	public List<Hospital> getHospitalList() {
		return hospitalList;
	}

	public void setHospitalList(List<Hospital> hospitalList) {
		this.hospitalList = hospitalList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public Director getDirector1() {
		return director1;
	}

	public void setDirector1(Director director1) {
		this.director1 = director1;
	}

	public Director getDirector2() {
		return director2;
	}

	public void setDirector2(Director director2) {
		this.director2 = director2;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

}
