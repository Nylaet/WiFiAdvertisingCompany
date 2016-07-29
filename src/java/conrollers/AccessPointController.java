package conrollers;

import entitys.AccessPoint;
import facades.AccessPointFacade;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "accessPointController")
@SessionScoped
public class AccessPointController implements Serializable {

    @EJB
    AccessPointFacade apf;
    @Inject
    LoginController lc;
    @Inject
    SysLog syslog;

    private AccessPoint select;
    private List<AccessPoint> aps;
    private String selectedAPFullImage;
    private String selectedAPTabletImage;
    private String selectedAPPhoneImage;
    private String numVisible;
    private boolean selected = false;

    @PostConstruct
    public void init() {
        aps = new ArrayList<>();
        if (apf.findAll() != null) {
            aps = apf.findAll();

        }
    }

    public AccessPointController() {
    }

    public void setAPFullImage(FileUploadEvent event) {
        String relative = "/resources/images/apImage/full/";
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolute = context.getRealPath(relative);

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        Path path = Paths.get(absolute);
        InputStream is = null;
        try {
            is = uploadedFile.getInputstream();
        } catch (IOException ex) {
            syslog.addSysLog("проблема с открытием потока");
        }

        File file = new File(path.toString() + "/" + uploadedFile.getFileName());

        try {
            FileUtils.copyInputStreamToFile(is, file);
            selectedAPFullImage = uploadedFile.getFileName();
        } catch (IOException ex) {
            syslog.addSysLog("проблема с записью файла на диск");
        }
    }

    public void setAPTabletImage(FileUploadEvent event) {
        String relative = "/resources/images/apImage/tablet/";
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolute = context.getRealPath(relative);

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        Path path = Paths.get(absolute);
        InputStream is = null;
        try {
            is = uploadedFile.getInputstream();
        } catch (IOException ex) {
            syslog.addSysLog("проблема с открытием потока");
        }

        File file = new File(path.toString() + "/" + uploadedFile.getFileName());

        try {
            FileUtils.copyInputStreamToFile(is, file);
            selectedAPTabletImage = uploadedFile.getFileName();
        } catch (IOException ex) {
            syslog.addSysLog("проблема с записью файла на диск");
        }
    }

    public void setAPPhoneImage(FileUploadEvent event) {
        String relative = "/resources/images/apImage/phone/";
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolute = context.getRealPath(relative);

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        Path path = Paths.get(absolute);
        InputStream is = null;
        try {
            is = uploadedFile.getInputstream();
        } catch (IOException ex) {
            syslog.addSysLog("проблема с открытием потока");
        }

        File file = new File(path.toString() + "/" + uploadedFile.getFileName());

        try {
            FileUtils.copyInputStreamToFile(is, file);
            selectedAPPhoneImage = uploadedFile.getFileName();
        } catch (IOException ex) {
            syslog.addSysLog("проблема с записью файла на диск");
        }
    }

    public void updateSelected() {
        if (apf.find(select.getId()) != null) {
            if (selectedAPFullImage.length() > 0) {
                select.setApFullImage(selectedAPFullImage);
            }
            if (selectedAPTabletImage.length() > 0) {
                select.setApTabletImage(selectedAPTabletImage);
            }
            if (selectedAPPhoneImage.length() > 0) {
                select.setApPhoneImage(selectedAPPhoneImage);
            }
            apf.edit(select);
            lc.getCurrent().addLog(select.getId() + " ap modified");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Точка доступа обновлена"));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Что-то пошло не так"));
    }

    public List<AccessPoint> getAps() {
        return aps;
    }

    public void setAps(List<AccessPoint> aps) {
        this.aps = aps;
    }

    public AccessPoint getSelect() {
        if (select == null) {
            select = new AccessPoint();
        }
        return select;
    }

    public void setSelect(AccessPoint select) {
        this.select = select;
    }

    public String getFormatedDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
        return (date != null ? sdf.format(date) : "Не известно");
    }

    public String getNumVisible() {
        if (aps.size() > 5) {
            return "5";
        }
        if (aps.isEmpty()) {
            return "3";
        }
        return String.valueOf(aps.size());
    }

    public void setNumVisible(String numVisible) {
        this.numVisible = numVisible;
    }

    public boolean isSelected() {
        try {
            if (getSelect().getId() > 0) {
                return true;
    }
        } catch (NullPointerException npe) {
        }
        return false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
