package com.shopme.admin.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Setting;
import com.shopme.common.entity.SettingCategory;

import jakarta.transaction.Transactional;

@Service
public class SettingService {

	@Autowired
	private SettingRepository settingrepo;
	
	public List<Setting> ListAll()
	{
		return (List<Setting>)settingrepo.findAll();
	}
	
	public GeneralSettingBag getGeneralSettings() {
		List<Setting> settings = new ArrayList<>();
		
		List<Setting> generalSettings = settingrepo.findByCategory(SettingCategory.GENERAL);
		
		settings.addAll(generalSettings);

		
		return new GeneralSettingBag(settings);
	}

	public void saveAll(Iterable<Setting> settings) {
		settingrepo.saveAll(settings);
	}
	
	public List<Setting> getMailServerSettings() {
		return settingrepo.findByCategory(SettingCategory.MAIL_SERVER);
	}
	
	public List<Setting> getMailTemplateSettings() {
		return settingrepo.findByCategory(SettingCategory.MAIL_TEMPLATES);
	}	
	
	public List<Setting> getCurrencySettings() {
		return settingrepo.findByCategory(SettingCategory.CURRENCY);
	}
	
}
