package animeshrine.kortas.com.animeshrine;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aladinne on 16/10/2016.
 */

public class AnimeS extends SugarRecord

{ String name,desc,type,img,aired,studio,userStatus;
    int rank,nbrEp,id ;
    int malID ;
    List<String> genres=new ArrayList<String>();


    public AnimeS(String name, String desc, String type, String img, String aired, String studio, String userStatus, int rank, int nbrEp, int id, List<String> genres) {
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.img = img;
        this.aired = aired;
        this.studio = studio;
        this.userStatus = userStatus;
        this.rank = rank;
        this.nbrEp = nbrEp;
        this.id = id;
        this.genres = genres;
    }

    public AnimeS() {
        super();
    }

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

}
