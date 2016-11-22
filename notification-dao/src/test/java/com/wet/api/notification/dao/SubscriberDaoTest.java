package com.wet.api.notification.dao;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import org.h2.tools.RunScript;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.wet.api.notification.dao.SubscriberDao;
import com.wet.api.notification.model.Subscriber;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/spring.xml"})
public abstract class SubscriberDaoTest
{	
	// TODO: This should be put into a test-specific spring configuration or properties file
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
	private static final String JDBC_URL = "jdbc:h2:mem:notification;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	private static final String SCHEMA_LOCATION = "classpath:META-INF/h2/schema.sql";
	private static final String DATASET_LOCATION = "META-INF/h2/dataset.xml";
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	private static final boolean CONTINUE_ON_ERROR = false;
	
	private static final int NUMBER_ROWS = 3;
	private static final String TABLE = "SUBSCRIBER";
	private static final String ID = "ID";
	private static final String FORM_ID = "FORM_ID";
	private static final String EMAIL = "EMAIL";
	private static final String ACTIVE = "ACTIVE";
	private static final String CONFIRMED = "CONFIRMED";
	private static final String CREATE_DATE = "CREATE_DATE";
	private static final String ACTIVATE_DATE = "ACTIVATE_DATE";
	private static final String DEACTIVATE_DATE = "DEACTIVATE_DATE";
	private static final String CONFIRM_DATE = "CONFIRM_DATE";
	private static final String VERSION = "VERSION";
	
	private static List<Subscriber> expectedSubscribers = new ArrayList<Subscriber>();
	private static IDataSet dataSet;
	
	protected abstract SubscriberDao getSubscriberDao();
	
	private static IDataSet readDataSet() throws Exception 
	{
		ClassLoader classLoader = SubscriberDaoTest.class.getClassLoader();
		File file = new File(classLoader.getResource(DATASET_LOCATION).getFile());
		return new FlatXmlDataSetBuilder().build(file);
	}
	
	private static Subscriber createSubscriberFromDataSet(ITable table, int row) throws Exception
	{
		Subscriber subscriber = new Subscriber();
		subscriber.setId(Long.valueOf((String)table.getValue(row, ID)));
		subscriber.setFormId(Short.valueOf((String)table.getValue(row, FORM_ID)));
		subscriber.setEmail((String)table.getValue(row, EMAIL));
		subscriber.setActive(((String)table.getValue(row, ACTIVE)).equals("1"));
		subscriber.setConfirmed(((String)table.getValue(row, CONFIRMED)).equals("1"));
		subscriber.setCreateDate(convertRowToDate(table.getValue(row, CREATE_DATE)));
		subscriber.setActivateDate(convertRowToDate(table.getValue(row, ACTIVATE_DATE)));
		subscriber.setDeactivateDate(convertRowToDate(table.getValue(row, DEACTIVATE_DATE)));
		subscriber.setConfirmDate(convertRowToDate(table.getValue(row, CONFIRM_DATE)));
		subscriber.setVersion(convertRowToDate(table.getValue(row, VERSION)));
		
		return subscriber;
	}
	
	private static Date convertRowToDate(Object value)
	{
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		return formatter.parseDateTime((String)value).toDate();
	}
	
	@BeforeClass
	public static void createSubscribers() throws Exception
	{
		dataSet = readDataSet();
		ITable table = dataSet.getTable(TABLE);
		
		for (int row=0;row<NUMBER_ROWS;row++)
		{
			expectedSubscribers.add(createSubscriberFromDataSet(table, row));
		}
	}
	
	@BeforeClass
	public static void createSchema() throws Exception 
	{
		RunScript.execute(JDBC_URL, USER, PASSWORD, SCHEMA_LOCATION, CHARSET, CONTINUE_ON_ERROR);
	}
	
	private void cleanlyInsertDataset(IDataSet dataSet) throws Exception 
	{
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}
	
	@Before
	public void importDataSet() throws Exception 
	{
		cleanlyInsertDataset(dataSet);
	}
	
	protected Subscriber getSubscriber()
	{
		Subscriber subscriber = new Subscriber();
		subscriber.setFormId((short)31);
		subscriber.setEmail("tester11@email.com");
		subscriber.setActive(Subscriber.ACTIVE);
		subscriber.setConfirmed(Subscriber.CONFIRMED);
		subscriber.setCreateDate(new Date());
		subscriber.setActivateDate(new Date());
		subscriber.setDeactivateDate(new Date());
		subscriber.setConfirmDate(new Date());
		
		return subscriber;
	}
	
	@Test
	public void testFindById()
	{
		Iterator<Subscriber> expectedItr = expectedSubscribers.iterator();
		while(expectedItr.hasNext())
		{
			Subscriber expected = expectedItr.next();
			Subscriber actual = getSubscriberDao().find(expected.getId());
			
			assertThat(actual, is(expected));
			assertThat(actual.getId(), is(expected.getId()));
			assertThat(actual.getFormId(), is(expected.getFormId()));
			assertThat(actual.getEmail(), is(expected.getEmail()));
			assertThat(actual.isActive(), is(expected.isActive()));
			assertThat(actual.isConfirmed(), is(expected.isConfirmed()));
			assertThat(actual.getCreateDate().compareTo(expected.getCreateDate()), is(0));
			assertThat(actual.getActivateDate().compareTo(expected.getActivateDate()), is(0));
			assertThat(actual.getDeactivateDate().compareTo(expected.getDeactivateDate()), is(0));
			assertThat(actual.getConfirmDate().compareTo(expected.getConfirmDate()), is(0));
			assertThat(actual.getVersion().compareTo(expected.getVersion()), is(0));
		}
	}
	
	@Test
	public void testFindAll()
	{
		List<Subscriber> actualSubscribers = getSubscriberDao().findAll();
		
		Iterator<Subscriber> expectedItr = expectedSubscribers.iterator();
		while(expectedItr.hasNext())
		{
			assertThat(actualSubscribers, hasItem(expectedItr.next()));
		}
	}
	
	@Test
	public void testSave()
	{
		Subscriber subscriber = getSubscriber();
		getSubscriberDao().save(subscriber);
		
		assertThat(subscriber.getId(), greaterThan(0L));
		assertThat(subscriber.getVersion(), notNullValue());
	}
	
	@Test
	public void testDelete()
	{
		Subscriber subscriber = getSubscriberDao().find(1L);
		getSubscriberDao().delete(subscriber);
		List<Subscriber> actualSubscribers = getSubscriberDao().findAll();

		assertThat(actualSubscribers, not(hasItem(subscriber)));
		
		Subscriber deletedSubscriber = getSubscriberDao().find(1L);
		assertNull(deletedSubscriber);
	}
}