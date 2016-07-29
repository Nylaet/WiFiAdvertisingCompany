/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrollers;

import entitys.Logger;
import entitys.User;
import facades.UserFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "sysLog")
@RequestScoped
public class SysLog {

    @EJB
    UserFacade uf;
    private User syslog;

    public SysLog() {
    }

    public void addSysLog(String log) {
        getSyslog();
        syslog.addLog(log);
        uf.edit(syslog);
    }

    public List<Logger> getLog() {
        getSyslog();
        return syslog.getLog();
    }

    public String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YY HH:mm:ss");
        return sdf.format(date);
    }

    private void getSyslog() {
        if (syslog == null) {
            for (User user : uf.findAll()) {
                if (user.getLogin().equals("syslog")) {
                    syslog = user;
                    break;
                }
            }
        }
    }

}
