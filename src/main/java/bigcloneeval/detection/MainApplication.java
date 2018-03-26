package bigcloneeval.detection;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bigcloneeval.detection.service.StorageService;

@SpringBootApplication
public class MainApplication implements CommandLineRunner 
{
	@Resource
	StorageService storageService;

	public static void main(String[] args) 
	{
		SpringApplication.run(MainApplication.class, args);

	}
	
	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}

}
