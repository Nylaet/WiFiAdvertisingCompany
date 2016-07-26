package entitys;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int leftImpression;
    private String nameFullSize;
    private String nameTabletSize;
    private String namePhoneSize;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Show>shows;

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

    public List<Show> getShows() {
        return shows;
    }

    public void addShow(Long apID) {
        leftImpression--;
        shows.add(new Show(apID));
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLeftImpression() {
        return leftImpression;
    }

    public void setLeftImpression(int leftImpression) {
        this.leftImpression = leftImpression;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Model)) {
            return false;
        }
        Model other = (Model) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitys.Model[ id=" + id + " ]";
    }
    
}
