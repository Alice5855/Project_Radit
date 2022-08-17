package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	// Page 494 Get, Post method로 file upload를 처리
	// @RequestMapping을 적용하지 않기 때문에 /views folder에 uploadForm.jsp의
	// 10행에서 multipart를 적용하여 업로드 처리 할 수 있도록 함
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("Upload Form ===== ");
	}
	
	// Page 497 : Spring MVC에서 지원하는 MultipartFile type으로 file handle
	// upload를 multiple 속성으로 여러 개 업로드 할 수 있으므로 배열 type으로 param을 받음
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model m) {
		String uploadFolder = "C:/Uploaded";
		
		for (MultipartFile multipartFile : uploadFile) {
			log.info("===============================");
			log.info("Upload File ===== " + multipartFile.getOriginalFilename());
			log.info("Upload File Size ====== " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			// File(parent, child) 상위 경로와 file 이름 경로를 설정
			
			// file upload handle (transferTo() method 활용)
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			} // catch
		} // for
	} // uploadFormPost
	
	// Page 500 ajax 활용 file upload
	// Ajax 활용 시 FormData 객체 사용
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("=== Upload via Ajax ===");
	}
	
	/* Page508 중복된 이름의 첨부파일 handle
	 * 중복된 이름의 file : 현재 시각을 milsec으로 구분하여 file 이름을 생성하거나
	 * UUID를 이용하여 중복이 발생할 가능성이 매우 낮은 문자열을 생성하여 처리
	 * 한 폴더 내에 생성될 수 있는 file 개수 제한 문제와 performance 저하 문제 handle : 
	 * 년/월/일 단위의 folder를 생성하여 file을 저장
	 */
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	
	// Page 517 AttachFileDTO의 list 반환 구조 변경
	// ResopnseEntity<list<AttachFileDTO>>를 JSON 형태로 반환하도록 변경하고
	// 내부적으로 각 file type에 맞춰 AttachFileDTO를 생성하여 전달하는 구조로 변경
	
	// public void uploadAjaxPost(MultipartFile[] uploadFile)
	// originally returns nothing (void)
	
	// page724 첨부파일의 등록, 삭제(post)는 로그인한 사용자만 가능하도록 제한 
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();
		// Added (page517)
		
	    log.info("=== Upload File Handle with Ajax ===");
	    String uploadFolder = "C:/Uploaded";
	    
	    // Page 509 getFolder() method 적용
	    File uploadPath = new File(uploadFolder, getFolder());
	    log.info("Upload Path ====== " + uploadPath);
	    
	    if (uploadPath.exists() == false) {
	    	// uploadPath가 존재하지 않는경우 folder를 생성하여 경로 작성
	    	uploadPath.mkdirs();
	    }
	    // year/month/day 경로에 저장됨
	   
	    for (MultipartFile multipartFile : uploadFile) {
	    	AttachFileDTO attachDTO = new AttachFileDTO();
	    	// Added (page517)
	   
		    log.info("========================================");
		    log.info("Upload File Name: " + multipartFile.getOriginalFilename());
		    log.info("Upload File Size: " + multipartFile.getSize());
		   
		    String uploadFileName = multipartFile.getOriginalFilename();
		    // Page504 이름 중복 방지를 위한 UUID 적용
		   
		    // IE has file path
		    // IE의 경우 전체 file 경로가 전송되기 때문에 마지막 '\'를 기준으로 잘라낸 문자열
		    // 이 실제 file 이름이 됨
		    uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
		    log.info("Uploaded file name ===== " + uploadFileName);
		    
		    attachDTO.setB_fileName(uploadFileName);
		    // Added (page517)
		    
		    UUID uuid = UUID.randomUUID();
		    uploadFileName = uuid.toString() + "_" + uploadFileName;
		    // 첨부 파일에 'randomUUID_'를 붙여서 저장
		   
		    log.info("UUID ===== " + uuid);
		    
		    try {
		    	// File saveFile = new File(uploadFolder, uploadFileName);
		    	// 설정한 uploadPath에 file을 저장하도록 경로 지정
		    	File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setB_uuid(uuid.toString());
				attachDTO.setB_uploadPath(getFolder());
				// Added (page517)
				
				// Image type인지를 검증
				//if (checkImageType(saveFile)) {
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "sthmb_" + uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					// createThumbnail(InputStream, OutputStream, width, height)
					// 100 x 100 size 'sthmb_filename' 의 thumbnail file 생성
					 
					thumbnail.close();
				//}
				list.add(attachDTO);
				// Added (page517)
			} catch (Exception e) {
				log.error(e.getMessage());
			} // catch
	    } // for
	    return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
    } // uploadAjaxPost
	
	/*
	// file type이 image인지를 검증
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	*/
	
	// Page 526 Thumbnail data transfer
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {
		log.info("fileName ===== " + fileName);
		
		File file = new File("C:/Uploaded/" + fileName);
		
		log.info("file ===== " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	// file name을 기준으로 thumbnail data를 byte[] type으로 전송.
	// MIME Type을 전송한 file type과 맞추기 위해 probeContentType() method로
	// HttpHeader에 file과 맞는 MIME type을 전송하도록 함
	
	// Page531 첨부 파일 다운로드
	// org.springframework.core.io.Resource type을 활용하여 간단하게 handle
	// application/octet-stream : file을 다운로드 할 수 있는 MIME type
	@GetMapping(value="/download", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
		log.info("downloaded file ===== " + fileName);
		Resource resource = new FileSystemResource("C:/Uploaded/" + fileName);
		
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// 입력된 resource가 없을 경우 status 반환
		
		log.info("resource ===== " + resource);
		
		String resourceName = resource.getFilename();
		
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		// Page539 remove UUID
		HttpHeaders headers = new HttpHeaders();
		
		try {
			String downloadName = null;
			
			if(userAgent.contains("Trident")) {
				log.info("RIP IE");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
			} else if(userAgent.contains("Edge")) {
				// we don't need it Edge uses chromium engine
				log.info("Edge browser uses chromium engine so we totally don't need this code");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
			} else {
				// how you be sure it is not gecko but chrome
				log.info("Chromium or Gecko or Webkit even");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}
			// bottom line we don't need this since other than Trident
			// there is no problem of encode type
			// ...and IE is dead so
			// it does not even detect edge as edge so this whole code
			// is good as dead with IE
			// also you gotta encode the fuggin query string too like
			// wtf am i even suppose to do with this shitty browser
			// thank god IE died
			log.info("resourceOriginalName ===== " + resourceOriginalName);
			log.info("downloadName ===== " + downloadName);
			// INFO : org.zerock.controller.UploadController - resourceOriginalName ===== 테스트.txt
			// INFO : org.zerock.controller.UploadController - downloadName ===== íì¤í¸.txt
			// i don't get why it shows not encoded text but downloads
			// the file perfectly fine
			// maybe it suppose to be like this in chromium idk
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
			// header의 Content-Disposition을 이용하여  UTF-8 type으로 이름을 저장
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	// @RequestHeader("User-Agent") String userAgent 추가 된 code의 경우 
	// IE 호환을 위한 code, 다시 말해 필요가 없는 code다
	// no seriously IE is now dead as shit
	/* pref
	@GetMapping(value="/download", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName) {
		log.info("downloaded file ===== " + fileName);
		Resource resource = new FileSystemResource("C:/Uploaded/" + fileName);
		log.info("resource ===== " + resource);
		
		String resourceName = resource.getFilename();
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("UTF-8"), "ISO-8859-1"));
			// header의 Content-Disposition을 이용하여  UTF-8 type으로 이름을 저장
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	 */
	
	// Page548 Request에서 전달되는 parameter의 이름, 종류를 파악하여 Response 
	// handle. image인 경우 browser에서 전송되는 file 이름인 UUID + "_" + 
	// file name file과 thumbnail("sthmb_" + ...)을 함께 삭제해준다
	// (try문의 nested if문)
	
	// page724 첨부파일의 등록, 삭제(post)는 로그인한 사용자만 가능하도록 제한 
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		log.info("deleteFile ===== " + fileName);
		
		File file;
		
		try {
			file = new File("C:\\Uploaded\\" + URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			if (type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("sthmb_", "");
				log.info("largeFileName : " + largeFileName);
				file = new File(largeFileName);
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}
	
}
