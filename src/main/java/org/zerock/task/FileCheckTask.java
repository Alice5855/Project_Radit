package org.zerock.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.BoardAttachVO;
import org.zerock.mapper.BoardAttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// Page601 파일의 목록 처리
// 작업(task)의 순서는 1) 데이터베이스에서 오늘 잘못 서버에 남겨진 파일의 목록을 가져와서
// 매분 0초에 스케쥴링 파일을 확인한 후에 삭제 처리하도록 하겠습니다.
// 2) 해당 폴더의 파일 목록에서 데이터베이스에 없는 파일을 찾아냅니다.
// 3) 그리고 나서 데이터베이스에 없는 파일들을 삭제하는 순서로 코딩을 합니다.
@Log4j
@Component
public class FileCheckTask {

	@Setter(onMethod_ = { @Autowired })
	private BoardAttachMapper attachMapper;

	private String getFolderYesterDay() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, -1);   // 데이터베이스에서 어제 사용된 파일의 목록을 얻어옵니다.

		String str = sdf.format(cal.getTime());

		return str.replace("-", File.separator);
	}

	@Scheduled(cron = "0 0 2 * * *") // FileCheckTask의 checkFiles()는 매일 새벽 2시에 동작하게 설정했습니다.
	public void checkFiles() throws Exception {

		log.warn("===== File Check Task Running =====");
		log.warn(new Date());
		// file list in database
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();

		// ready for check file in directory with database file list
		List<Path> fileListPaths = fileList.stream()
				.map(vo -> Paths.get("C:\\Uploaded", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());

		// image file has thumnail file
		fileList.stream().filter(vo -> vo.isFileType() == true)
				.map(vo -> Paths.get("C:\\Uploaded", vo.getUploadPath(), "sthmb_" + vo.getUuid() + "_" + vo.getFileName()))
				.forEach(p -> fileListPaths.add(p));

		log.warn("===========================================");

		fileListPaths.forEach(p -> log.warn(p));

		// files in yesterday directory
		File targetDir = Paths.get("C:\\Uploaded", getFolderYesterDay()).toFile();

		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);

		log.warn("-----------------------------------------");
		for (File file : removeFiles) {

			log.warn(file.getAbsolutePath());

			file.delete();

		}
	}
}
