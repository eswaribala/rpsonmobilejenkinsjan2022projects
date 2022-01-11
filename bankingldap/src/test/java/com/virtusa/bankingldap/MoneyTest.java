package com.virtusa.bankingldap;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.virtusa.bankingldap.models.Money;

class MoneyTest {

	@Test
	public void constructorShouldSetAmountAndCurrency() {
		Money money = new Money(10, "USD");
		assertEquals(10, money.getAmount());
		assertEquals("USD", money.getCurrency());
	}

}
