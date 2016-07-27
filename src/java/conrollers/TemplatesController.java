package conrollers;

import entitys.Client;
import entitys.Model;
import facades.ClientFacade;
import facades.ModelFacade;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

@Named(value = "templatesCntroller")
@SessionScoped
public class TemplatesController implements Serializable {

    @EJB
    ClientFacade cf;
    @EJB
    ModelFacade mf;
    @Inject
    LoginController lc;
    private List <Client> clients;
    private Client selected;
    private String nameFullSize;
    private String nameTabletSize;
    private String namePhoneSize;
    @PostConstruct
    public void init(){
        clients=new ArrayList<>();
        if(cf.findAll()!=null){
            clients=cf.findAll();
        
        }
    }
    
    public TemplatesController() {
    }
    
    public void updateClient(Model model){
        if(mf.find(model.getId())!=null){
            if(nameFullSize.length()>0){
                model.setNameFullSize(nameFullSize);
            }
            if(nameTabletSize.length()>0){
                model.setNameTabletSize(nameTabletSize);
            }
            if(namePhoneSize.length()>0){
                model.setNamePhoneSize(namePhoneSize);
            }
            mf.edit(model);
            lc.getCurrent().addLog(model.getId() +" model modified");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Успешно сохранено"));
        }
    }
    
    public void setNewFullImage(FileUploadEvent event){
        String relative="/resources/images/advertImage/full/";
        ServletContext context=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolute=context.getRealPath(relative);
        
        UploadedFile uploadedFile=(UploadedFile)event.getFile();
        Path path=Paths.get(absolute);
        InputStream is=null;
        try {
            is=uploadedFile.getInputstream();
        } catch (IOException ex) {
            System.out.println("проблема с открытием потока");
        }
        
        File file=new File(path.toString()+"/"+uploadedFile.getFileName());
        
        try {
            FileUtils.copyInputStreamToFile(is, file); 
            nameFullSize=uploadedFile.getFileName();
        } catch (IOException ex) {
            System.out.println("проблема с записью файла на диск");
        }
    }
    
    public void setNewTabletImage(FileUploadEvent event){
        String relative="/resources/images/advertImage/tablet/";
        ServletContext context=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolute=context.getRealPath(relative);
        
        UploadedFile uploadedFile=(UploadedFile)event.getFile();
        Path path=Paths.get(absolute);
        InputStream is=null;
        try {
            is=uploadedFile.getInputstream();
        } catch (IOException ex) {
            System.out.println("проблема с открытием потока");
        }
        
        File file=new File(path.toString()+"/"+uploadedFile.getFileName());
        
        try {
            FileUtils.copyInputStreamToFile(is, file); 
            nameTabletSize=uploadedFile.getFileName();
        } catch (IOException ex) {
            System.out.println("проблема с записью файла на диск");
        }
    }
    
    public void setNewPhoneImage(FileUploadEvent event){
        String relative="/resources/images/advertImage/phone/";
        ServletContext context=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String absolute=context.getRealPath(relative);
        
        UploadedFile uploadedFile=(UploadedFile)event.getFile();
        Path path=Paths.get(absolute);
        InputStream is=null;
        try {
            is=uploadedFile.getInputstream();
        } catch (IOException ex) {
            System.out.println("проблема с открытием потока");
        }
        
        File file=new File(path.toString()+"/"+uploadedFile.getFileName());
        
        try {
            FileUtils.copyInputStreamToFile(is, file); 
            namePhoneSize=uploadedFile.getFileName();
        } catch (IOException ex) {
            System.out.println("проблема с записью файла на диск");
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Client getSelected() {
        if(selected==null)selected=new Client();
        return selected;
    }

    public void setSelected(Client selected) {
        this.selected = selected;
    }
    
    
}
