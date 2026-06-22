package com.admin.content.service;

import com.admin.content.dto.request.NoticeRequest;
import com.admin.content.dto.response.NoticeResponse;
import com.admin.content.entity.Notice;
import com.admin.content.exception.NoticeNotFoundException;
import com.admin.content.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<NoticeResponse> getAllNotices() {
        return noticeRepository.findAll().stream()
            .map(NoticeResponse::new)
            .toList();
    }

    public NoticeResponse getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new NoticeNotFoundException(id));
        return new NoticeResponse(notice);
    }

    public NoticeResponse createNotice(NoticeRequest request) {
        Notice notice = new Notice(request.getTitle(), request.getContent());
        return new NoticeResponse(noticeRepository.save(notice));
    }

    public NoticeResponse updateNotice(Long id, NoticeRequest request) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new NoticeNotFoundException(id));
        notice.update(request.getTitle(), request.getContent());
        return new NoticeResponse(noticeRepository.save(notice));
    }

    public void deleteNotice(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new NoticeNotFoundException(id);
        }
        noticeRepository.deleteById(id);
    }
}
