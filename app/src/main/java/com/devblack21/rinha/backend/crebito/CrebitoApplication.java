package com.devblack21.rinha.backend.crebito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

@EnableMongoRepositories
@SpringBootApplication
public class CrebitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrebitoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void run() {
		Runtime runtime = Runtime.getRuntime();
		System.out.println(humanReadableByteCountSI(runtime.maxMemory()));
		System.out.println(humanReadableByteCountSI(runtime.freeMemory()));
	}

	public static String humanReadableByteCountSI(long bytes) {
		if (-1000 < bytes && bytes < 1000) {
			return bytes + " B";
		}
		CharacterIterator ci = new StringCharacterIterator("kMGTPE");
		while (bytes <= -999_950 || bytes >= 999_950) {
			bytes /= 1000;
			ci.next();
		}
		return String.format("%.1f %cB", bytes / 1000.0, ci.current());
	}
}
