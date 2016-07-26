/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entitys.AccessPoint;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Panker-RDP
 */
@Stateless
public class AccessPointFacade extends AbstractFacade<AccessPoint> {

    @PersistenceContext(unitName = "WiFiPromoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccessPointFacade() {
        super(AccessPoint.class);
    }
    
}
