package com.douzone.weboard.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.weboard.exception.FileUploadException;

@Service
public class FileuploadService {
	@Value("${fileupload.fileSaveBasePath}")
	private String BASE_PATH;
	@Value("${fileupload.urlBasePath}")
	private String URL_BASE;
	@Value("${server.port}")
	private String port;

	public String restoreImage(MultipartFile file, String directory) throws FileUploadException {
		try {
			String savePath = BASE_PATH + directory;
			String url = URL_BASE + directory;

			if (file == null || file.isEmpty()) {
				return null;
			}

			File uploadDirectory = new File(savePath);
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdir();
			}

			String originFilename = file.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf('.') + 1);
			String saveFilename = UUID.randomUUID() + "." + extName;

			byte[] data = file.getBytes();
			OutputStream os = new FileOutputStream(savePath + "/" + saveFilename);
			os.write(data);
			os.close();

			return url + "/" + saveFilename;

		} catch (IOException ex) {
			throw new FileUploadException("file upload error:" + ex);
		}
	}

}
