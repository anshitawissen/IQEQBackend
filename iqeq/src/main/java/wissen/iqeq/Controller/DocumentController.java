package wissen.iqeq.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wissen.iqeq.Service.DocumentService;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {
    private final DocumentService documentService;
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<List<Map<String, String>>> downloadExcel(@PathVariable String id) {
        try {
            InputStream inputStream = new ClassPathResource("sample.xlsx").getInputStream();
            List<Map<String, String>> jsonData = documentService.downloadExcel(inputStream);
            return ResponseEntity.ok(jsonData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            String message = documentService.uploadPdf(file);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }


}
