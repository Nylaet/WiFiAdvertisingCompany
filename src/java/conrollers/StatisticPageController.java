package conrollers;

import entitys.Client;
import entitys.Model;
import facades.ClientFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

@Named(value = "statisticPageController")
@RequestScoped
public class StatisticPageController {

    @EJB
    ClientFacade cf;
    Client client=new Client();

    public StatisticPageController() {
    }

    
    
    public List<Model> getModels(){
        if (!FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID").isEmpty()) {
            Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID"));
            client=cf.find(id);
        }
        if(client.getModels().isEmpty())return new ArrayList<Model>();
        return client.getModels();
    }

}
