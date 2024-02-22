package com.timeline.blog.blogApplicationAPI;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.timeline.blog.blogApplicationAPI.configurations.AppConstants;
import com.timeline.blog.blogApplicationAPI.entity.Role;
import com.timeline.blog.blogApplicationAPI.repositories.RoleRepo;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan("com.timeline.blog.blogApplicationAPI.*")
@EnableJpaRepositories("com.timeline.blog.blogApplicationAPI.*")
@EntityScan("com.timeline.blog.blogApplicationAPI.*") 

public class BlogApplicationApiApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationApiApplication.class, args);
	}

	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("8775787875"));
		
		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstants.REGULAR_USER);
			role2.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role1,role2);
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r -> {
				System.out.println(r.getName());
			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
} 

