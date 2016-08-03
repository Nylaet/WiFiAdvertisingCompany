package conrollers;

import entitys.Client;
import entitys.Model;
import facades.ClientFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named(value = "clientsController")
@SessionScoped
public class ClientsController implements Serializable {

    @Inject
    LoginController lc;
    @Inject
    SysLog syslog;
    @EJB
    ClientFacade cf;
    private List<Client>clients;
    private Client created=new Client();
    
    public ClientsController() {
    }

    public List<Client> getClients() {
        clients=cf.findAll();
        if(clients==null)clients=new ArrayList<>();
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
    
    public String getShowsAll(Client client){
        int shows=0;
        for (Model model : client.getModels()) {
            shows+=model.getShows().size();
        }
        return String.valueOf(shows);
    }
    
    public void updateClient(Client client){
        cf.edit(client);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Обновлено"));
        lc.getCurrent().addLog(client.getId()+"modified client");
    }
    
    public void sendEmail(Client client) {
        try {
            if (client.getEmail() != "") {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "false");
                props.put("mail.smtp.ssl.enable", "true");

                Session sess = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication("wifi.promo.group@gmail.com", "156456851");
                    }
                }
                );

                Message msg = new MimeMessage(sess);
                msg.setSubject("You link to statistic");
                msg.setRecipient(Message.RecipientType.TO, new InternetAddress(client.getEmail()));
                String bodyPart = "<a href=\""+client.getUrlPersonal()+"\" value=\"Ваша ссылка на просмотр статистики\" target=\"_blank\" />";
                msg.setContent(bodyPart, "text/html; charset=utf-8");
                msg.setSentDate(new Date());
                Transport.send(msg);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Почта отправлена"));
                return;
            }

        } catch (MessagingException ex) {
            syslog.addSysLog("ClientsController.sendMail()"+ex);
            
        } finally {

        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Почта не отправлена"));
    }

    public Client getCreated() {
        return created;
    }

    public void setCreated(Client created) {
        this.created = created;
    }
    
    public String createClient(){
        if(created.getName().length()>0&&created.getEmail().length()>0
                &&created.getPhone().length()>0){
            cf.create(created);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Клиент создан"));
            lc.getCurrent().addLog(created.getId()+"create client");
            created=new Client();
            return "clients.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Неа... Перепроверь, что б все было заполнено!!!"));
        return "";
    }
}
