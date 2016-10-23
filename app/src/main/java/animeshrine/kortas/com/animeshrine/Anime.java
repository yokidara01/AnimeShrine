package animeshrine.kortas.com.animeshrine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aladinne on 25/08/2016.
 */
public class Anime {
    private String name,desc,type,img,aired,studio,userStatus;
    private int rank,nbrEp,id ;
     private List<String> genres=new ArrayList<String>();

    @Override
    public String toString() {
        return "Anime{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", img='" + img + '\'' +
                ", aired='" + aired + '\'' +
                ", studio='" + studio + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", rank=" + rank +
                ", nbrEp=" + nbrEp +
                ", id=" + id +
                ", genres=" + genres.size() +
                '}';
    }

    public void addGenre(String s)
    {
        genres.add(s);
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAired() {
        return aired;
    }

    public void setAired(String aired) {
        this.aired = aired;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getNbrEp() {
        return nbrEp;
    }

    public void setNbrEp(int nbrEp) {
        this.nbrEp = nbrEp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
