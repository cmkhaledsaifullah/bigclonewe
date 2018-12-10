package bigcloneeval.evaluation.controller;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControler {

	 @GetMapping(value = "/api/image/carousel")
	    public ResponseEntity<InputStreamResource> getImage(@RequestParam("background") String background) throws IOException {

	    	ClassPathResource imgFile;
	    	
	    	switch(background){
	    		case "1":
	    			imgFile = new ClassPathResource("images/image1.png");
	    			break;
	    		case "2":
	    			imgFile = new ClassPathResource("images/image2.jpg");
	    			break;
	    		case "3":
	    			imgFile = new ClassPathResource("images/image3.jpg");
	    			break;
	    		default:
	    			imgFile = new ClassPathResource("images/image1.png");
	    	}
	    	
	        return ResponseEntity
	                .ok()
	                .contentType(MediaType.IMAGE_PNG)
	                .body(new InputStreamResource(imgFile.getInputStream()));
	    }
	
}

