package com.wet.api.notification.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.wet.api.common.model.DomainEntity;

@Entity
@Table(name="subscriber")
public class Subscriber implements DomainEntity
{
	private static final long serialVersionUID = 3409219829250873360L;
	
	public final static boolean ACTIVE = Boolean.TRUE;
	public final static boolean INACTIVE = Boolean.FALSE;
	public final static boolean CONFIRMED = Boolean.TRUE;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Version
	private Date version;
	
	@Min(1)
	private short formId;

	@Email
	@NotEmpty
	private String email;
	
	private boolean active;

	private boolean confirmed;
	
	@Column(name="create_date", columnDefinition="DATETIME", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name="activate_date", columnDefinition="DATETIME", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date activateDate;
	
	@Column(name="deactivate_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deactivateDate;

	@Column(name="confirm_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date confirmDate;
	
	@Override
	public long getId() 
	{
		return id;
	}

	@Override
	public void setId(long id) 
	{
		this.id = id;	
	}
	
	@Override
	public Date getVersion() 
	{
		return version;
	}

	@Override
	public void setVersion(Date version) 
	{
		this.version = version;
	}
	
	public short getFormId()
	{
		return formId;
	}
	
	public void setFormId(short formId)
	{
		this.formId = formId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}

	public boolean isActive() 
	{
		return active;
	}
	
	public void setActive(boolean active) 
	{
		this.active = active;
	}
	
	public boolean isConfirmed()
	{
		return confirmed;
	}
	
	public void setConfirmed(boolean confirmed)
	{
		this.confirmed = confirmed;
	}
	
	public Date getCreateDate() 
	{
		return createDate;
	}

	public void setCreateDate(Date createDate) 
	{
		this.createDate = createDate;
	}

	public Date getActivateDate() 
	{
		return activateDate;
	}

	public void setActivateDate(Date activateDate) 
	{
		this.activateDate = activateDate;
	}

	public Date getDeactivateDate() 
	{
		return deactivateDate;
	}

	public void setDeactivateDate(Date deactivateDate) 
	{
		this.deactivateDate = deactivateDate;
	}

	public Date getConfirmDate() 
	{
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate)
	{
		this.confirmDate = confirmDate;
	}

	/**
	 * Returns a string representation of a {@Subscriber}.
	 * 
	 * @Return		the string representation of a {@Subscriber}
	 */
	@Override
	public String toString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm:ssa");
		StringBuilder sb = new StringBuilder("[");
		
		// Id and Form Id
		sb.append(this.id).append(":").append(formId).append(", ");
		
		// Email
		sb.append("Email:").append(this.email).append(", ");
		
		// Subscribed
		if (this.createDate == null)
		{
			sb.append("Not Subscribed, ");
		}
		else
		{
			sb.append("Subscribed:").append(formatter.format(this.createDate)).append(", ");
		}
		
		// Active
		if (this.isActive())
		{
			sb.append("Active:").append(formatter.format(this.activateDate)).append(", ");
		}
		else
		{
			sb.append("Inactive");
			if (this.deactivateDate == null)
			{
				sb.append(", ");
			}
			else
			{
				sb.append(":").append(formatter.format(this.deactivateDate)).append(", ");
			}
		}
		
		// Confirmed
		if (this.isConfirmed())
		{
			sb.append("Confirmed:").append(formatter.format(this.confirmDate)).append(", ");
		}
		else
		{
			sb.append("Not Confirmed, ");
		}
		
		// Last Modified
		if (this.version == null)
		{
			sb.append("Not Saved");
		}
		else
		{
			sb.append("Version:").append(formatter.format(this.version));
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Equals method for {@Subscriber}
	 * 
	 * {@Subscriber}s are considered equal if the email addresses are equal
	 * since the email address is the only point of contact to the 
	 * subscriber.
	 * Note: Subscribers may subscribe to multiple things via multiple forms. 
	 * Consequently, form_id and email form a unique key. May need to revisit
	 * this when have multiple things to subscribe through via multiple forms
	 * 
	 * @param	o	the term object passed in to determine its equality with this object
	 * @return		true if this term is equal to the term passed in
	 */
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Subscriber) || o == null)
		{
			return false;
		}
		
		Subscriber subscriber = (Subscriber)o;
		if (this.email.equals(subscriber.getEmail()))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Hashcode method for {@Subscriber}
	 * 
	 * The hashcode representation for {@Subscriber}. The email address is used
	 * to determine the hashcode value.
	 * 
	 * @return		the hashcode value
	 */
	@Override
	public int hashCode()
	{
		int hashCode = 31;
		
		hashCode += this.email == null ? 0 : this.email.hashCode();
		
		return hashCode;
	}
}