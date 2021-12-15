package com.pde.pdedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import com.pde.pdedemo.model.AzureModel;
import com.pde.pdedemo.service.AzureStorageSvc;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@RestController
@RequestMapping("blob")
public class BlobController {
	
	@Value("${blob.url}")
    private Resource blobFile;
	
	@Autowired
	private AzureStorageSvc azureStorageSvc; 

    @GetMapping("/readBlobFile")
    public String readBlobFile() throws IOException {
        return StreamUtils.copyToString(
                this.blobFile.getInputStream(),
                Charset.defaultCharset());
    }
    
    @PostMapping("/writeBlobFile")
    public String writeBlobFile(@RequestBody String data) throws IOException {
        try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream()) {
            os.write(data.getBytes());
        }
        return "file was updated";
    }
    
    @PostMapping("/readBlobItem")
    public String readBlobItem(@RequestBody AzureModel azureModel) throws IOException {
    	return azureStorageSvc.readFromBlob(azureModel.getContainerName(), azureModel.getFileName());
    }

}
