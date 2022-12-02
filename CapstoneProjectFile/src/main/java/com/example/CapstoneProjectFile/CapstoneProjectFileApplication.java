package com.example.CapstoneProjectFile;

import com.example.CapstoneProjectFile.Service.HistoryDataService;
import com.example.CapstoneProjectFile.Service.StockService;
import com.example.CapstoneProjectFile.View.LogInWindow;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class CapstoneProjectFileApplication {

	public static void main(String[] args) throws IOException {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CapstoneProjectFileApplication.class);
		builder.headless(false);

		ConfigurableApplicationContext context = builder.run(args);
		LogInWindow window = context.getBean("logInWindow", LogInWindow.class);
		window.openWindow();
	}

}
