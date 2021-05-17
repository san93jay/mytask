/**
 * 
 */
package com.heptagon.userdetailstask.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * @author ${Sanjay Vishwakarma}
 *
 */
@Entity
@Table(name = "user_token")
@Data
public class UserToken implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The user token id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_token_id", unique = true, nullable = false)
	private Integer userTokenId;
	
    /** The date. */
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
    private Date date;
    
    @Column(name = "user_login")
    private String userLogin;

    /** The ip address. */
    @Column(name = "ip_address")
    private String ipAddress;

    /** The user agent. */
    @Column(name = "user_agent")
    private String userAgent;
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_details_id")
    private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name ="expires")
    private Date expires;
    
    @Column(name = "token")
    private String token;    
}
