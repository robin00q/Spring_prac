package net.slipp.domain.users;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTest {
	private static Validator validator;
	
	private static final Logger log = LoggerFactory.getLogger(UserTest.class);
	
	
	@BeforeClass
	public static void Setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void userIdWhenIsEmpty() {
		User user = new User("", "password", "name", "hello@world.com");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
	
		assertThat(constraintViolations.size(), is(2));
	
		for (ConstraintViolation<User> constraintViolation : constraintViolations) {
			log.debug("violation error message : {}", constraintViolation.getMessage());
		}
	}

}
