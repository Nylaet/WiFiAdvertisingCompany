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
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "addTemplateController")
@SessionScoped
public class AddTemplateController implements Serializable {

    @EJB
    ClientFacade cf;
    @EJB
    ModelFacade mf;
    
    @Inject
    private LoginController lc;
    
    Model createdModel;
    Client selectedClient;
    
    private String nameFullSize;
    private String nameTabletSize;
    private String namePhoneSize;
            
    public AddTemplateController() {
    }
    
    public String addModel(){
        if(selectedClient!=null){
            if(nameFullSize.length()>0&&createdModel.getLeftImpression()>0){
                createdModel.setNameFullSize(nameFullSize);
                createdModel.setNameTabletSize(nameTabletSize);
                createdModel.setNamePhoneSize(namePhoneSize);
                cf.find(selectedClient.getId()).addModel(createdModel);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Сохранено"));
                lc.getCurrent().addLog(createdModel.getId()+" model added");
                return "templates.xhtml?faces-redirect=true";
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fail :'('"));
        return "";
    }
    
    public Model getCreatedModel() {
        if(createdModel==null)createdModel=new Model();
        return createdModel;
    }

    public void setCreatedModel(Model createdModel) {
        this.createdModel = createdModel;
    }

    public Client getSelectedClient() {
        if(selectedClient==null)selectedClient=new Client();
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }
    
    public List <Client> getClients(){
        List<Client> clients=cf.findAll();        
        if(clients==null)return new ArrayList<>();
        return clients;
    }

    public String getNameFullSize() {
        return nameFullSize;
    }

    public void setNameFullSize(String nameFullSize) {
        this.nameFullSize = nameFullSize;
    }

    public String getNameTabletSize() {
        return nameTabletSize;
    }

    public void setNameTabletSize(String nameTabletSize) {
        this.nameTabletSize = nameTabletSize;
    }

    public String getNamePhoneSize() {
        return namePhoneSize;
    }

    public void setNamePhoneSize(String namePhoneSize) {
        this.namePhoneSize = namePhoneSize;
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
        
    
    
    
}
