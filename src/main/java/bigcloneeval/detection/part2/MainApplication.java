package bigcloneeval.detection.part2;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bigcloneeval.detection.part2.MainApplication;
import bigcloneeval.detection.part2.service.StorageService;

@SpringBootApplication
public class MainApplication
{
	@Resource
	StorageService storageService;

	public static void main(String[] args) 
	{
		SpringApplication.run(MainApplication.class, args);

	}

}
