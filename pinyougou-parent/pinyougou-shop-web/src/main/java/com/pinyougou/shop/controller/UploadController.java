package com.pinyougou.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import entity.Result;
import util.FastDFSClient;

/**
 * 文件上传Controller
 * 
 * @author Administrator
 *
 */
@RestController
public class UploadController {
	// 文件服务器地址, 定义在 config/application.properties 内.
	@Value("${FILE_SERVER_URL}")
	private String FILE_SERVER_URL;

	@RequestMapping("/upload.do")
	public Result upload(MultipartFile file) {
		String originalName = file.getOriginalFilename();
		// 获取拓展后缀
		String extName = originalName.substring(originalName.lastIndexOf(".") + 1);
		try {
			// 2、创建一个 FastDFS 的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
			// 3. 上穿文件. 获取到服务器路径
			// 客户端上传文件后存储服务器将文件 ID 返回给客户端，此文件 ID 用于以后访问该文件的索引信息。
			String filePath = fastDFSClient.uploadFile(file.getBytes(), extName);
			// 4. 拼接文件访问路径
			String url = FILE_SERVER_URL + filePath;
			return new Result(true, url);
		} catch (Exception e) {
			return new Result(true, "上传失败, 请重试");
		}
	}
}