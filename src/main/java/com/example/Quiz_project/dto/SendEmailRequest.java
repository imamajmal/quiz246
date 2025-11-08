// SendEmailRequest.java
package com.example.Quiz_project.dto;
import java.util.Map;

public record SendEmailRequest(String to, String templateCode, Map<String,String> variables) {}

