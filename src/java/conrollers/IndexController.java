package conrollers;

import entitys.AccessPoint;
import entitys.Client;
import entitys.Model;
import facades.AccessPointFacade;
import facades.ClientFacade;
import facades.ModelFacade;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

@Named(value = "indexController")
@ApplicationScoped
public class IndexController implements Serializable {

    @EJB
    ClientFacade cf;
    @EJB
    ModelFacade mf;
    @EJB
    AccessPointFacade apf;
    private List<Client> clients;
    private List<Model> models = new ArrayList<>();
    private String modelNameFull;
    private String modelNameTablet;
    private String modelNamePhone;
    private String apFullImage;
    private String apTabletImage;
    private String apPhoneImage;
    private String speedRenew="15";

    public IndexController() {
    }

    @PostConstruct
    public void init() {
        clients = cf.findAll();
        if (clients.isEmpty()) {
            //default models init
        } else {
            for (Client client : clients) {
                List<Model> clientModels = client.getModels();
                for (Model clientModel : clientModels) {
                    if (clientModel.getLeftImpression() > 0) {
                        models.add(clientModel);
                    }
                }
            }
        }
    }

    public String getModelNameFull() {
        if (models.isEmpty()) {
            return "emptyImg.png";
        }
        Model model = models.get(getRandomModel());
        updateModel(model);
        modelNameFull = model.getNameFullSize();
        return modelNameFull;
    }

    public String getModelNameTablet() {
        if (models.isEmpty()) {
            return "emptyImg.png";
        }
        Model model = models.get(getRandomModel());
        updateModel(model);
        modelNameTablet = model.getNameTabletSize();
        return modelNameTablet;
    }

    public String getModelNamePhone() {
        if (models.isEmpty()) {
            return "emptyImg.png";
        }
        Model model = models.get(getRandomModel());
        updateModel(model);
        modelNamePhone = model.getNamePhoneSize();
        return modelNamePhone;
    }

    private int getRandomModel() {
        int index;
        while (true) {
            int degr = 1;

            while (Math.floor(models.size() / Math.pow(10, degr)) > 1) {
                degr++;
            }
            index = (int) Math.floor(Math.random() * (Math.pow(10, degr)));
            if (index < models.size()) {
                return index;
            }
        }
    }

    public String update() {
        init();
        return "";
    }

    public String getSpeedRenew() {
        return speedRenew;
    }

    public void setSpeedRenew(String speedRenew) {
        this.speedRenew = speedRenew;
    }

    public String getApFullImage() {
        if (getAP() != null) {
            return getAP().getApFullImage();
        }
        return "emptyAPImage.jpg";
    }

    public void setApFullImage(String apFullImage) {
        this.apFullImage = apFullImage;
    }

    public String getApTabletImage() {
        if (getAP() != null) {
            return getAP().getApTabletImage();
        }
        return "emptyAPImage.jpg";
    }

    public void setApTabletImage(String apTabletImage) {
        this.apTabletImage = apTabletImage;
    }

    public String getApPhoneImage() {
        if (getAP() != null) {
            return getAP().getApPhoneImage();
        }
        return "emptyAPImage.jpg";
    }

    public void setApPhoneImage(String apPhoneImage) {
        this.apPhoneImage = apPhoneImage;
    }

    private AccessPoint getAP() {
        Long devId = getAPID();
        AccessPoint ap = null;
        try {
            ap = apf.find(devId);
        } catch (IllegalArgumentException ex) {
        }
        if (ap != null) {
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nClients").length() > 0) {
                ap.setClientsLast(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nClients"));
                ap.setLastUpdate(new Date());
            }
            apf.edit(ap);
            return ap;
        }
        return null;
    }
    
    
    
    private void updateModel(Model model) {
        model.addShow(getAPID());
        mf.edit(model);
    }

    private Long getAPID() {
        Long id = Long.MIN_VALUE;
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").length() > 0) {
                return Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            }
        } catch (NullPointerException ex) {
        }
        return id;
    }

}
