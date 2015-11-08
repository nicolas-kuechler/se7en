package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.se.se7en.shared.model.SelectOption;

public class SelectOptionTest {

	@Test
	public void testSelectOption() {
		SelectOption select = new SelectOption();
		assertEquals(select.getId(),null);
	}

	@Test
	public void testSelectOptionIntString() {
		SelectOption select = new SelectOption(10,"TestName");
		Integer digit = 10;
		assertEquals(select.getId(),digit);
		assertEquals(select.getName(),"TestName");
	}

	@Test
	public void testGetId() {
		SelectOption select = new SelectOption(10,"TestName");
		Integer digit = 10;
		assertEquals(select.getId(),digit);
	}

	@Test
	public void testGetName() {
		SelectOption select = new SelectOption(10,"TestName");
		assertEquals(select.getName(),"TestName");
	}

}
