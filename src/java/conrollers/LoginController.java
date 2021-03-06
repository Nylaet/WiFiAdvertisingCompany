package conrollers;

import entitys.User;
import enums.Role;
import facades.UserFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    UserFacade uf;
    @Inject
    SysLog syslog;

    private User current = new User();
    private User created = new User();
    private boolean entered;
    private boolean admin;
    private List<User> users;

    @PostConstruct
    private void init() {
        List<User> users = uf.findAll();
        if (users.isEmpty()) {
            User admin = new User();
            admin.setLogin("panker");
            admin.setPassword("156456851");
            admin.setRole(Role.ADMIN);
            uf.create(admin);
            User syslog=new User();
            syslog.setLogin("syslog");
            syslog.setPassword("156456851156456851156456851");
            syslog.setRole(Role.ADMIN);
            uf.create(syslog);
        }
    }

    public LoginController() {
    }

    public String login() {
        users = uf.findAll();
        for (User user : users) {
            if (user.getLogin().equals(current.getLogin())) {
                if (user.getPassword().equals(current.getPassword())) {
                    current = user;
                    current.addLog("entered");
                    uf.edit(current);
                    entered = true;
                    if (current.getRole().equals(Role.ADMIN)) {
                        admin = true;
                    }
                    return ("accesspoints.xhtml?faces-redirect=true");
                }
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Не верный логин или пароль"));
        return "errorlogin.xhtml?faces-redirect=true";
    }

    public String logout() {
        if (entered) {
            current.addLog("User logout");
            uf.edit(current);
            String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            entered = false;
            admin = false;
        }
        current = new User();
        return "login.xhtml?faces-redirect=true";
    }

    public String createNewUser() {
        if (created.getLogin().length() > 3 && created.getPassword().length() > 7) {
            created.setRole(Role.MANAGER);
            created.addLog("created");
            uf.create(created);
            current.addLog(created.getLogin()+"created");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пользователь создан"));
            created=new User();
            return "";
        }
        return "";
    }

    public void deleteUser(User user) {
        uf.remove(user);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пользователь удален"));
    }
    
    public String editCurrent(){
        uf.edit(current);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пароль изменен"));
        current.addLog("password changed");
        return "";
    }
    
    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public User getCreated() {
        return created;
    }

    public void setCreated(User created) {
        this.created = created;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
}
