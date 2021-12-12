package com.steve.app.messageQ;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomMessage {

	private String messageId;
	private String email;
	private String date;
	private String content;
}
