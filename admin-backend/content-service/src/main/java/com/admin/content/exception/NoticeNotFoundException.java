package com.admin.content.exception;

public class NoticeNotFoundException extends RuntimeException{
    public NoticeNotFoundException(Long id) {
        super("Notice not found with id: " + id);
    }
}
