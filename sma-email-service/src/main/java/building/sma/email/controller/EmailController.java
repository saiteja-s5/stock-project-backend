package building.sma.email.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import building.sma.email.model.EmailPayload;
import building.sma.email.service.EmailService;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/with-excel")
    public ResponseEntity<Void> sendEmailWithExcelAttachment(@RequestBody EmailPayload payload) {
	emailService.sendEmailWithExcelAttachment(payload.getFrom(), payload.getTo(), payload.getSubject(),
		payload.getBody(), payload.getAttachmentName(), payload.getAttachmentData());
	return ResponseEntity.ok().build();
    }

}
