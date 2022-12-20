package com.shopme.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil 
{
	public static void saveFile(String uploadDir , String fileName, MultipartFile multipartfile) throws IOException
	{
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath))
		{
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartfile.getInputStream())
		{
			Path filepath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filepath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch(IOException ex)
		{
			throw new IOException("Could not save file : " + fileName, ex);
		}
		
	}

	public static void cleanDir(String Dir) 
	{
		Path dirPath = Paths.get(Dir);
		try
		{
			Files.list(dirPath).forEach(file -> 
			{
				if(!Files.isDirectory(file))
				{
					try
					{
						Files.delete(file);
					}
					catch (IOException ex)
					{
						System.out.println("Could not delete file: " + file);
					}
				}
			});
		}
		catch (IOException exclean)
		{
			System.out.println("Could not list directory : " + dirPath);
		}
	}
}
