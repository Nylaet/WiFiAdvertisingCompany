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

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    UserFacade uf;

    private User current = new User();
    private User created = new User();
    private boolean entered;
    private boolean admin;

    @PostConstruct
    private void init() {
        List<User> users = uf.findAll();
        if (users.isEmpty()) {
            User admin = new User();
            admin.setLogin("panker");
            admin.setPassword("156456851");
            admin.setRole(Role.ADMIN);
            uf.create(admin);
        }
    }

    public LoginController() {
    }

    public String login() {
        List<User> users = uf.findAll();
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

    public void createNewUser() {
        if (created.getLogin().length() > 3 && created.getPassword().length() > 8) {
            created.setRole(Role.MANAGER);
            created.addLog("created");
            uf.create(created);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пользователь создан"));
        }
    }

    public void deleteUser(User user) {
        uf.remove(user);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пользователь удален"));
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

}
