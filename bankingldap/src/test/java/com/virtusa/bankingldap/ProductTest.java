package com.virtusa.bankingldap;

import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofNanos;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.util.StringUtils;

class ProductTest {

	
	private static int i=1;
	
	@RegisterExtension
    static LoggingExtension staticExtension = new LoggingExtension("static version");

    @Test
    public void demoTest() {
        // assertions
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
	@Timeout(unit = TimeUnit.NANOSECONDS,value = 1)
    @CsvFileSource(resources = "/product.csv", numLinesToSkip = 1)
    void testWithCsvFileSource(String productName, int cost) {
        
       
            // Perform task that takes less than 2 minutes.
        	assertNotNull(productName);
            assertNotEquals(0, cost);
       
    }
	@Timeout(unit = TimeUnit.NANOSECONDS,value = 1)
	  @RepeatedTest(5)
	    void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
	        assertEquals(5, repetitionInfo.getTotalRepetitions());
	    }
	  @RepeatedTest(value = 2, name = "{displayName} {currentRepetition}/{totalRepetitions}")
	    @DisplayName("Repeat!")
	    void customDisplayName(TestInfo testInfo) {
	        assertEquals("Repeat! "+ i+"/2", testInfo.getDisplayName());
	        i++;
	    }
	  private TranslatorEngine translatorEngine;
	  @BeforeEach
	    public void setUp() {
	      translatorEngine = new TranslatorEngine();
	    }
	  
	  @Test
	    void timeoutNotExceeded() {
	        // The following assertion succeeds.
	        assertTimeout(ofNanos(1), () -> {
	            // Perform task that takes less than 2 minutes.
	        });
	    }

	  
	  @TestFactory
	  @Disabled
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
