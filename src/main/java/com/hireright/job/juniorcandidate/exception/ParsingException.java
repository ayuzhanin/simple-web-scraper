package com.hireright.job.juniorcandidate.exception;

import java.io.IOException;

/**
 * @author Artur Yuzhanin
 * ����������  (��� ��� �������)
 */
public class ParsingException extends IOException {
    /**
     * �����������
     * @param message ���������, ������������ � ����������.
     */
    public ParsingException(String message) {
        super(message);
    }
}
