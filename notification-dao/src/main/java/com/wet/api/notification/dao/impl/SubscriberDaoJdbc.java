package com.wet.api.notification.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;

import com.wet.api.common.dao.impl.AbstractDaoJdbc;
import com.wet.api.notification.dao.SubscriberDao;
import com.wet.api.notification.model.Subscriber;

@Repository("subscriberDaoJdbc")
public class SubscriberDaoJdbc extends AbstractDaoJdbc<Subscriber> implements SubscriberDao 
{
	private final static String NOT_IMPLEMENTED_MESSAGE = "This method is currently not implemented";
	
	public SubscriberDaoJdbc() 
	{
		super(Subscriber.class);
	}

	@Override
	public Subscriber find(long id)
	{
		Subscriber subscriber = new Subscriber();
		Connection connection = null;

		StringBuilder sql = new StringBuilder("SELECT * FROM ")
				.append(getTableName())
				.append(" WHERE id=?");
		
		System.out.println("JDBC: " + sql.toString());
		
		try
		{
			connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.first())
	        {
	        	subscriber.setId(resultSet.getLong("id"));
	        	subscriber.setFormId(resultSet.getShort("form_id"));
	        	subscriber.setEmail(resultSet.getString("email"));
	        	subscriber.setActive(resultSet.getBoolean("active"));
	        	subscriber.setConfirmed(resultSet.getBoolean("confirmed"));
	        	subscriber.setCreateDate(resultSet.getTimestamp("create_date"));
	        	subscriber.setActivateDate(resultSet.getTimestamp("activate_date"));
	        	subscriber.setDeactivateDate(resultSet.getTimestamp("deactivate_date"));
	        	subscriber.setConfirmDate(resultSet.getTimestamp("confirm_date"));
	        	subscriber.setVersion(resultSet.getTimestamp("version"));
	        }	
		}
		catch (SQLException e)
		{
			// TODO: Log error message
			System.out.println(e.getMessage());
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				// TODO: Log error message
				System.out.println(e.getMessage());
			}
		}
		
		return subscriber;
	}

	@Override
	public List<Subscriber> findAll() 
	{
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	@Override
	public void save(Subscriber subscriber)
	{
		Connection connection = null;

		StringBuilder sql = new StringBuilder("INSERT INTO ")
				.append(getTableName())
				.append(" (form_id,email,active,confirmed,create_date,activate_date,deactivate_date,confirm_date,version)")
				.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, now())")
				.append(" ON DUPLICATE KEY UPDATE")
				.append(" form_id=?, email=?, active=?, confirmed=?, create_date=?, activate_date=?, deactivate_date=?, confirm_date=?, version=now()");
		
		System.out.println("JDBC: " + sql.toString());
		
		try
		{
			connection = dataSource.getConnection();
			PreparedStatement statement = loadPreparedStatement(subscriber, connection, sql.toString());
			statement.executeUpdate();
	        ResultSet resultSet = statement.getGeneratedKeys();
	        if (resultSet.first())
	        {
	        	subscriber.setId(resultSet.getInt(1));
	        }	
		}
		catch (SQLException e)
		{
			// TODO: Log error message
			System.out.println(e.getMessage());
		}
		finally
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				// TODO: Log error message
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void delete(Subscriber subscriber) 
	{
        Connection connection = null;

        StringBuilder sql = new StringBuilder("DELETE FROM ")
				.append(getTableName())
				.append(" WHERE id = ? LIMIT 1");

        System.out.println("JDBC: " + sql.toString());
        
        try
        {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            statement.setLong(1, subscriber.getId());
			statement.executeUpdate();
        }
        catch (SQLException e)
        {
            // TODO: log error
        }
		finally
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				// TODO: log error
			}
		}
	}

	@Override
	protected PreparedStatement loadPreparedStatement(Subscriber subscriber, Connection connection, String sql) throws SQLException 
	{
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		statement.setShort(1, subscriber.getFormId());
		statement.setString(2, subscriber.getEmail());
		statement.setBoolean(3, subscriber.isActive());
		statement.setBoolean(4, subscriber.isConfirmed());
		statement.setTimestamp(5, new Timestamp(subscriber.getCreateDate().getTime()));
		statement.setTimestamp(6, new Timestamp(subscriber.getActivateDate().getTime()));
		statement.setTimestamp(7, subscriber.getDeactivateDate() == null ? null : new Timestamp(subscriber.getDeactivateDate().getTime()));
		statement.setTimestamp(8, subscriber.getConfirmDate() == null ? null : new Timestamp(subscriber.getConfirmDate().getTime()));
		statement.setShort(9, subscriber.getFormId());
		statement.setString(10, subscriber.getEmail());
		statement.setBoolean(11, subscriber.isActive());
		statement.setBoolean(12, subscriber.isConfirmed());
		statement.setTimestamp(13, new Timestamp(subscriber.getCreateDate().getTime()));
		statement.setTimestamp(14, new Timestamp(subscriber.getActivateDate().getTime()));
		statement.setTimestamp(15, subscriber.getDeactivateDate() == null ? null : new Timestamp(subscriber.getDeactivateDate().getTime()));
		statement.setTimestamp(16, subscriber.getConfirmDate() == null ? null : new Timestamp(subscriber.getConfirmDate().getTime()));
		
		return statement;
	}
}