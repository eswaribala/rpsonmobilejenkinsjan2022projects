package com.virtusa.bankingldap;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.swing.Spring;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.virtusa.bankingldap.models.User;
import com.virtusa.bankingldap.repositories.UserRepository;


import lombok.extern.slf4j.Slf4j;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
@Slf4j

class BankingldapApplicationTests {
    @RegisterExtension
	private static LoggingExtension extension=new LoggingExtension("BankingLDAP");
	 @Autowired
	   private TestEntityManager entityManager;
	 
	    @Autowired
	    private UserRepository userRepository;
	    
	    private final static User testUser = new User();
	    
	    @BeforeAll
	   static void initAll() {
	    	testUser.setUserName("viki");
	    	testUser.setPassword("test@123");
	    	testUser.setEnabled(true);
	    }  
	    
	    
	@Test
	@DisplayName("Custom test user with db")
	void whenFindByName_thenReturnUser() {
		
		// given
	    User user = new User();
	    user.setUserName("virtusauser24322");
	    user.setPassword("test@123");
	    user.setEnabled(true);
	    //saved the object in table
	    entityManager.persist(user);
	    entityManager.flush();
	 
	    // when
	   User found = userRepository.findById(user.getUserName()).orElse(null);
	 
	    // then
	    assertEquals(found.getUserName(),user.getUserName());
	}
	@Test
    void groupedAssertions() {
        // In a grouped assertion all assertions are executed, and all
        // failures will be reported together.
        assertAll("user",
            () -> assertEquals("viki", testUser.getUserName()),
            () -> assertEquals("test@123", testUser.getPassword())
        );
    }

    @Test
    void dependentAssertions() {
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll("properties",
            () -> {
                String userName = testUser.getUserName();
                assertNotNull(userName);

                // Executed only if the previous assertion is valid.
                assertAll("User Name",
                    () -> assertTrue(userName.startsWith("v")),
                    () -> assertTrue(userName.endsWith("i"))
                );
            },
            () -> {
                // Grouped assertion, so processed independently
                // of results of first name assertions.
                String password = testUser.getPassword();
                assertNotNull(password);

                // Executed only if the previous assertion is valid.
                assertAll("Password",
                    () -> assertTrue(password.startsWith("t")),
                    () -> assertTrue(password.endsWith("3"))
                );
            }
        );
    }

    

    @Test
    void timeoutNotExceeded() {
        // The following assertion succeeds.
        assertTimeout(ofMinutes(2), () -> {
            // Perform task that takes less than 2 minutes.
        });
    }

    @Test
    void timeoutNotExceededWithResult() {
        // The following assertion succeeds, and returns the supplied object.
        String actualResult = assertTimeout(ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutExceeded() {
        // The following assertion fails with an error message similar to:
        // execution exceeded timeout of 10 ms by 91 ms
        assertTimeout(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }

    @Test
    void timeoutExceededWithPreemptiveTermination() {
        // The following assertion fails with an error message similar to:
        // execution timed out after 10 ms
        assertTimeoutPreemptively(ofMillis(10), () -> {
            // Simulate task that takes more than 10 ms.
            Thread.sleep(100);
        });
    }
    @Disabled("Disabled until bug #42 has been resolved")
    @Test
    void testWillBeSkipped() {
    }
    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void onlyOn64BitArchitectures() {
        // ...
    }
    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
    void onlyOnStagingServer() {
        // ...
    }
    @Test // Multi-line script, custom engine name and custom reason.
    @EnabledIf(value = {
                    "load('nashorn:mozilla_compat.js')",
                    "importPackage(java.time)",
                    "",
                    "var today = LocalDate.now()",
                    "var tomorrow = today.plusDays(1)",
                    "tomorrow.isAfter(today)"
                },
                engine = "nashorn",
                reason = "Self-fulfilling: {result}")
    void theDayAfterTomorrow() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        assertTrue(tomorrow.isAfter(today));
    }
    @BeforeEach
    void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetitions = repetitionInfo.getTotalRepetitions();
        String methodName = testInfo.getTestMethod().get().getName();
        log.info(String.format("About to execute repetition %d of %d for %s", //
            currentRepetition, totalRepetitions, methodName));
    }

    @RepeatedTest(10)
    void repeatedTest() {
        // ...
    }

    @RepeatedTest(5)
    void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
        assertEquals(5, repetitionInfo.getTotalRepetitions());
    }

    @RepeatedTest(value = 1, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Repeat!")
    void customDisplayName(TestInfo testInfo) {
        assertEquals("Repeat! 1/1", testInfo.getDisplayName());
    }

    @RepeatedTest(value = 1, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Details...")
    void customDisplayNameWithLongPattern(TestInfo testInfo) {
        assertEquals("Details... :: repetition 1 of 1", testInfo.getDisplayName());
    }

    @RepeatedTest(value = 5, name = "Wiederholung {currentRepetition} von {totalRepetitions}")
    void repeatedTestInGerman() {
        // ...
    }
    @ParameterizedTest
    @ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
    void hasTexts(String candidate) {
        assertTrue(StringUtils.hasText(candidate));
    }
    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, names = { "DAYS", "HOURS" })
    void testWithEnumSourceInclude(TimeUnit timeUnit) {
        assertTrue(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS).contains(timeUnit));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "./product.csv", numLinesToSkip = 1)
    void testWithCsvFileSource(String name, int cost) {
        assertNotNull(name);
        assertNotEquals(0, cost);
    }
    private TranslatorEngine translatorEngine;
    
    @BeforeEach
    public void setUp() {
      translatorEngine = new TranslatorEngine();
    }
    @TestFactory
    public Iterable<DynamicTest> translateDynamicTestsFromIterate() {
   
      List<String> inPhrases =
          new ArrayList<>(Arrays.asList("Hello", "Yes", "No", "Goodbye", "Good night", "Thank you"));
      List<String> outPhrases =
          new ArrayList<>(Arrays.asList("Bonjour", "Oui", "Non", "Au revoir", "Bonne nuit", "Merci"));
   
      return inPhrases.stream().map(phrs -> DynamicTest.dynamicTest("Test translate " + phrs, () -> {
        int idx = inPhrases.indexOf(phrs);
        assertEquals(outPhrases.get(idx), translatorEngine.tranlate(phrs));
      })).collect(Collectors.toList());
    }
}
