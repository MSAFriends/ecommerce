package com.github.msafriends.serviceproduct.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.github.msafriends.serviceproduct.moduleapi.ModuleApiApplication;

@AutoConfigureMockMvc
@SpringBootTest(classes = ModuleApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
	@Autowired
	protected MockMvc mockMvc;
}
