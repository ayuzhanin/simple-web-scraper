package com.hireright.job.juniorcandidate.tools.handlers.base;

import com.hireright.job.juniorcandidate.tools.Text;

import java.util.List;
import java.util.Map;

public interface Handler {
    void verboseRun(Map<Character, Boolean> flags, List<String> words, Text text);
}
