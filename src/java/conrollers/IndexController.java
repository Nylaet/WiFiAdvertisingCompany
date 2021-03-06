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
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "indexController")
@ApplicationScoped
public class IndexController implements Serializable {

    @EJB
    ClientFacade cf;
    @EJB
    ModelFacade mf;
    @EJB
    AccessPointFacade apf;
    @Inject
    SysLog sysLog;
    private List<Client> clients;
    private List<Model> models = new ArrayList<>();
    private String modelNameFull;
    private String modelNameTablet;
    private String modelNamePhone;
    private String apFullImage;
    private String apTabletImage;
    private String apPhoneImage;
    private String speedRenew = "15";

    public IndexController() {
    }

    public void init() {
        clients = cf.findAll();
        getAP();
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
        init();
        if (models.isEmpty()) {
            return "emptyImg.png";
        }
        Model model = models.get(getRandomModel());
        updateModel(model);
        modelNameFull = model.getNameFullSize();
        return modelNameFull;
    }

    public String getModelNameTablet() {
        init();
        if (models.isEmpty()) {
            return getModelNameFull();
        }
        Model model = models.get(getRandomModel());
        updateModel(model);
        modelNameTablet = model.getNameTabletSize();
        return modelNameTablet;
    }

    public String getModelNamePhone() {
        init();
        if (models.isEmpty()) {
            return getModelNameTablet();
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
            if(getAP().getApFullImage()!=null)
            return getAP().getApFullImage();
        }
        return "emptyAPImage.jpg";
    }

    public void setApFullImage(String apFullImage) {
        this.apFullImage = apFullImage;
    }

    public String getApTabletImage() {
        if (getAP() != null) {
            if(getAP().getApTabletImage()!=null)
            return getAP().getApTabletImage();
        }
        return getApFullImage();
    }

    public void setApTabletImage(String apTabletImage) {
        this.apTabletImage = apTabletImage;
    }

    public String getApPhoneImage() {
        if (getAP() != null) {
            if(getAP().getApPhoneImage()!=null)
            return getAP().getApPhoneImage();
        }
        return getApTabletImage();
    }

    public void setApPhoneImage(String apPhoneImage) {
        this.apPhoneImage = apPhoneImage;
    }

    private AccessPoint getAP() {
        String devId = getAPID();
        AccessPoint ap = null;
        try {
            for (AccessPoint accessPoint : apf.findAll()) {
                if (accessPoint.getDevID().equals(devId)) {
                    ap = accessPoint;
                }
            }
        } catch (IllegalArgumentException ex) {
            sysLog.addSysLog("IndexController.getAP()"+ex);
        }
        if (ap != null) {
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nClients").length() > 0) {
                ap.setClientsLast(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nClients"));
                ap.setLastUpdate(new Date());
            }
            apf.edit(ap);
            return ap;
        }
        if (devId.length() > 0) {
            ap = new AccessPoint();
            ap.setDevID(devId);
            ap.setName("Новая точка доступа");
            String clientsCount = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nClients");
            ap.setClientsLast(clientsCount.length() > 0 ? clientsCount : "1");
            apf.create(ap);
        }
        return null;
    }

    private void updateModel(Model model) {
        model.addShow(getAPID());
        mf.edit(model);
    }

    private String getAPID() {
        String id = "";
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").length() > 0) {
                return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            }
        } catch (NullPointerException ex) {
            sysLog.addSysLog("IndexController.getAPID()"+ex);
        }
        return id;
    }

}
