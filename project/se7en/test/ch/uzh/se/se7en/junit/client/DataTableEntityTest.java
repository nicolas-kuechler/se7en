package ch.uzh.se.se7en.junit.client;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.uzh.se.se7en.client.mvp.model.DataTableEntity;

public class DataTableEntityTest {

	@Test
	public void testDataTableEntity() {
		DataTableEntity tableEntity = new DataTableEntity("TEST",20);
		assertEquals(tableEntity.getName(),"TEST");
	}
	
	@Test
	public void testDataTableEntitySecondConstructor() {
		DataTableEntity tableEntity = new DataTableEntity("TEST",20, 1);
		assertEquals(tableEntity.getName(),"TEST");
		assertEquals(tableEntity.getId(),1);
		assertEquals(tableEntity.getValue(),20);
	}

	@Test
	public void testGetName() {
		DataTableEntity tableEntity = new DataTableEntity("TEST",20);
		assertEquals(tableEntity.getName(),"TEST");
		tableEntity.setName("TEST_NEW");
		assertEquals(tableEntity.getName(),"TEST_NEW");	
	}

	@Test
	public void testSetName() {
		DataTableEntity tableEntity = new DataTableEntity("TEST",20);
		assertEquals(tableEntity.getName(),"TEST");
		tableEntity.setName("TEST_NEW");
		assertEquals(tableEntity.getName(),"TEST_NEW");	
	}

	@Test
	public void testGetValue() {
		DataTableEntity tableEntity = new DataTableEntity("TEST",20);
		assertEquals(tableEntity.getName(),"TEST");
		tableEntity.setValue(10);
		assertEquals(tableEntity.getValue(),10);	
	}

	@Test
	public void testSetValue() {
		DataTableEntity tableEntity = new DataTableEntity("TEST",20);
		assertEquals(tableEntity.getName(),"TEST");
		tableEntity.setValue(10);
		assertEquals(tableEntity.getValue(),10);	
	}
	
	
	@Test
	public void testId() {
		DataTableEntity tableEntity = new DataTableEntity("TEST",20, 1);
		assertEquals(tableEntity.getName(),"TEST");
		tableEntity.setId(2);
		assertEquals(tableEntity.getId(),2);	
	}


	

	@Test
	public void testEqualsObject() {
		DataTableEntity tableEntity1 = new DataTableEntity("TEST",20);
		DataTableEntity tableEntity2 = new DataTableEntity("TEST_TEST",30);
		DataTableEntity tableEntity3 = new DataTableEntity("TEST",20);
		DataTableEntity tableEntity4 = new DataTableEntity("TEST",20, 1);
		DataTableEntity tableEntity5 = new DataTableEntity("TEST",20, 1);
		assertEquals(tableEntity1.equals(tableEntity2),false);
		assertEquals(tableEntity1.equals(tableEntity3),true);
		assertEquals(tableEntity1.equals(tableEntity4),false);
		assertEquals(tableEntity4.equals(tableEntity5),true);
	}

	@Test
	public void testToString() {
		DataTableEntity tableEntity = new DataTableEntity("TEST",20, 1);
		assertEquals(tableEntity.toString(),"DataTableEntity [name=TEST, value=20, id=1]");
	}

}
