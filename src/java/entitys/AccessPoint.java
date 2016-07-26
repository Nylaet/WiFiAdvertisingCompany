/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Panker-RDP
 */
@Entity
public class AccessPoint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name="";
    private String address="";
    private Long devID;
    private String clientsLast="";
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastUpdate;
    private String apFullImage;
    private String apTabletImage;
    private String apPhoneImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDevID() {
        return devID;
    }

    public void setDevID(Long devID) {
        this.devID = devID;
    }

    public String getClientsLast() {
        return clientsLast;
    }

    public void setClientsLast(String clientsLast) {
        this.clientsLast = clientsLast;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApFullImage() {
        return apFullImage;
    }

    public void setApFullImage(String apFullImage) {
        this.apFullImage = apFullImage;
    }

    public String getApTabletImage() {
        return apTabletImage;
    }

    public void setApTabletImage(String apTabletImage) {
        this.apTabletImage = apTabletImage;
    }

    public String getApPhoneImage() {
        return apPhoneImage;
    }

    public void setApPhoneImage(String apPhoneImage) {
        this.apPhoneImage = apPhoneImage;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessPoint)) {
            return false;
        }
        AccessPoint other = (AccessPoint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.AccesPoint[ id=" + id + " ]";
    }
    
}
