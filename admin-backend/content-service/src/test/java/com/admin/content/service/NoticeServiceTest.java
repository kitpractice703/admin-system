package com.admin.content.service;

import com.admin.content.dto.request.NoticeRequest;
import com.admin.content.dto.response.NoticeResponse;
import com.admin.content.entity.Notice;
import com.admin.content.exception.NoticeNotFoundException;
import com.admin.content.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeService noticeService;

    @Test
    @DisplayName("전체 공지사항 조회 시 목록 반환")
    void getAllNotices() {
        Notice notice = new Notice("점검 안내", "새벽 2시 점검 예정입니다.");
        when(noticeRepository.findAll()).thenReturn(List.of(notice));

        List<NoticeResponse> result = noticeService.getAllNotices();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("점검 안내");
    }

    @Test
    @DisplayName("존재하는 ID로 조회 시 공지사항 반환")
    void getNoticeById() {
        Notice notice = new Notice("점검 안내", "새벽 2시 점검 예정입니다.");
        when(noticeRepository.findById(1L)).thenReturn(Optional.of(notice));

        NoticeResponse result = noticeService.getNoticeById(1L);

        assertThat(result.getTitle()).isEqualTo("점검 안내");
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회 시 예외 발생")
    void getNoticeByIdNotFound() {
        when(noticeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> noticeService.getNoticeById(99L))
                .isInstanceOf(NoticeNotFoundException.class);
    }

    @Test
    @DisplayName("공지사항 생성 시 저장된 공지사항 반환")
    void createNotice() {
        NoticeRequest request = new NoticeRequest();
        Notice notice = new Notice("점검 안내", "새벽 2시 점검 예정입니다.");
        when(noticeRepository.save(any(Notice.class))).thenReturn(notice);

        NoticeResponse result = noticeService.createNotice(request);

        assertThat(result.getTitle()).isEqualTo("점검 안내");
    }

    @Test
    @DisplayName("존재하지 않는 ID 삭제 시 예외 발생")
    void deleteNoticeNotFound() {
        when(noticeRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> noticeService.deleteNotice(99L))
                .isInstanceOf(NoticeNotFoundException.class);
    }
}
