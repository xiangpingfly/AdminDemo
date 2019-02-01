package com.example.demo.component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * 自定义的类用于将base64格式的图片转成multiPartFile图片;
 */
public class BASE64DecodedMultipartFile implements MultipartFile {

	private final byte[] imgContent;
	private final String header;

	public BASE64DecodedMultipartFile(byte[] imgContent, String header) {
		this.imgContent = imgContent;
		this.header = header.split(";")[0];
	}

	@Override
	public String getName() {
		return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
	}

	@Override
	public String getOriginalFilename() {
		return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
	}

	@Override
	public String getContentType() {
		return header.split(":")[1];
	}

	@Override
	public boolean isEmpty() {
		return imgContent == null || imgContent.length == 0;
	}

	@Override
	public long getSize() {
		return imgContent.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return imgContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(imgContent);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(dest);
			fos.write(imgContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fos.close();
		}
	}
}
