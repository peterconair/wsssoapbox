package org.wsssoapbox.database.cert;
// Generated Jan 31, 2012 8:32:26 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * KeyStore generated by hbm2java
 */
public class KeyStore  implements java.io.Serializable {
   private static final long serialVersionUID = 5347849698420805722L;


     private Integer keystoreId;
     private String name;
     private String aliasName;
     private String password;
     private byte[] content;
     private String type;
     private String provider;
     private Date createDate;
     private int userId;

    public KeyStore() {
    }

	
    public KeyStore(String name, String aliasName, String password, byte[] content, String type, Date createDate, int userId) {
        this.name = name;
        this.aliasName = aliasName;
        this.password = password;
        this.content = content;
        this.type = type;
        this.createDate = createDate;
        this.userId = userId;
    }
    public KeyStore(String name, String aliasName, String password, byte[] content, String type, String provider, Date createDate, int userId) {
       this.name = name;
       this.aliasName = aliasName;
       this.password = password;
       this.content = content;
       this.type = type;
       this.provider = provider;
       this.createDate = createDate;
       this.userId = userId;
    }
   
    public Integer getKeystoreId() {
        return this.keystoreId;
    }
    
    public void setKeystoreId(Integer keystoreId) {
        this.keystoreId = keystoreId;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getAliasName() {
        return this.aliasName;
    }
    
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public byte[] getContent() {
        return this.content;
    }
    
    public void setContent(byte[] content) {
        this.content = content;
    }
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public String getProvider() {
        return this.provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }




}

