package com.admin.content.controller;

import com.admin.content.dto.request.NoticeRequest;
import com.admin.content.dto.response.NoticeResponse;
import com.admin.content.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<List<NoticeResponse>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getNoticeById(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getNoticeById(id));
    }

    @PostMapping
    public ResponseEntity<NoticeResponse> createNotice(@RequestBody @Valid NoticeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noticeService.createNotice(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponse> updateNotice(@PathVariable Long id,
                                                       @RequestBody @Valid NoticeRequest request) {
        return ResponseEntity.ok(noticeService.updateNotice(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}
