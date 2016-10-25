package animeshrine.kortas.com.animeshrine;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aladinne on 15/10/2016.
 */

public class SeasonAnimeStealer {


    List<Integer>IDs = new ArrayList<>();
    public void getIdsSeason() {




        Document doc = null;
        try {
            Log.e("div season anime start","STarting Jsoup");
            doc = Jsoup.connect("https://www.myanimelist.net/anime/season").
            userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
            .timeout(10*1000).get();
            Log.e("JSOUP Cnx MYANIMELIST",doc.text()+"");
            org.jsoup.select.Elements newsHeadlines = doc.select("div[class=seasonal-anime js-seasonal-anime]");
            Log.d("div season anime size",newsHeadlines.size()+"");

            newsHeadlines=   newsHeadlines.select("a[class=link-title]");

            //  System.out.println(newsHeadlines.size());
            for (org.jsoup.nodes.Element e: newsHeadlines)
            {//e.text()+" "+
                String id=e.attr("href").substring(30);
                System.out.println(id);
                int slashPos=id.indexOf("/");
                System.out.println("slashpos: "+slashPos);

                id=id.substring(0,slashPos);
                System.out.println("id : "+id);
                Log.e("season id", id) ;

                //
                IDs.add(Integer.parseInt(id));


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //List<AnimeS>  animesfinal =getAnimes(IDs);

    }

    private List<AnimeS>  getAnimes(List<Integer> animesId) {

        List<AnimeS>  animesfinal =new ArrayList<>();

        Document doc = null;
        AnimeS anime ;
        for(int id : animesId) {
            try {

                AnimeS animeS = new AnimeS();
                animeS= getAnime(id);
                animesfinal.add(animeS);


            } catch (Exception e) {
                e.printStackTrace();
            }



        }
        return  animesfinal;
    }


    public AnimeS getAnime (int id)
    {   AnimeS anime = new AnimeS();
        Document doc = null;
        Log.d("AnimeS id",id+"");
        try
        {
        doc = Jsoup.connect("https://www.myanimelist.net/anime/" +id).timeout(10*1000).
                userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .timeout(10*1000).
                get();


        org.jsoup.select.Elements newsHeadlines = doc.select("span[itemprop=\"name\"]");

        anime.malID=id ;
            String name = newsHeadlines.text() ;
            if(name.contains("Top Anime"))
            {
                int startzayid = name.indexOf("Top Anime");
                name=name.substring(0,startzayid);
            }
        anime.name=name;


        //System.out.println("animesname = [" + anime.getName() + "]");

        newsHeadlines = doc.select("span[itemprop=\"description\"]");

        anime.desc=(newsHeadlines.text());
        //System.out.println("animesdesc = [" + anime.getDesc() + "]");

        newsHeadlines = doc.select("div[style=\"text-align: center;\"]").select("img");
        anime.img=(newsHeadlines.attr("src"));

        newsHeadlines=doc.select("span[class=numbers ranked]").select("strong");

        try {
            int rnk = Integer.parseInt(newsHeadlines.text().substring(1));

            anime.rank=(rnk);//
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("animesId = [" + id + "]");
            anime.rank=99999;
        }

        anime.toString();



        ArrayList<String> genres = new ArrayList<>();
        newsHeadlines=  doc.select("a[href*=/genre/]");
        System.out.println(newsHeadlines.size() +" size of genres");
        for (org.jsoup.nodes.Element e: newsHeadlines)
        {
            String genre=e.text();

            System.out.println(genre);
            genres.add(genre);

        }

        anime.genres=(genres);


            newsHeadlines= doc.select("img[itemprop=image");
            anime.img= newsHeadlines.text();


        } catch (IOException e) {
            e.printStackTrace();
        }



        return anime ;
    }

}
