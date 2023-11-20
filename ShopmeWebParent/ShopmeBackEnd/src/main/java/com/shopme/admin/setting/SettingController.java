package com.shopme.admin.setting;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Products;
import com.shopme.common.entity.Setting;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SettingController {
	
	@Autowired
	private SettingService settingService;
	
	@GetMapping("/setting")
	public String listAll(Model model)
	{
		List<Setting> listSetting = settingService.ListAll();
		
		model.addAttribute("listsetting", listSetting);
		for(Setting setting : listSetting)
		{
			model.addAttribute(setting.getKey(), setting.getValue());
		}	
	
		return "setting";
	}
	
	@PostMapping("/setting/save_general")
	public String savesetting(@RequestParam("fileImage") MultipartFile multipartFile,
			HttpServletRequest request, RedirectAttributes ra) throws IOException
	{
		GeneralSettingBag settingBag = settingService.getGeneralSettings();
		
		saveSiteLogo(multipartFile, settingBag);
		
		updateSettingValuesFromForm(request, settingBag.list());
		
		ra.addFlashAttribute("message", "General settings have been saved.");
		
		return "redirect:/settings";
	}
	
	private void saveSiteLogo(MultipartFile multipartFile, GeneralSettingBag settingBag) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String value = "/site-logo/" + fileName;
			settingBag.updateSiteLogo(value);
			String uploadDir = "site-logo";
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
	}
	
	private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {
		for (Setting setting : listSettings) {
			String value = request.getParameter(setting.getKey());
			if (value != null) {
				setting.setValue(value);
			}
			else
			{
				setting.setValue(setting.getValue());
			}
		}
		
		settingService.saveAll(listSettings);
	}
}
