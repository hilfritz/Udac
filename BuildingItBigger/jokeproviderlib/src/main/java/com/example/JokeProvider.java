package com.example;

/*
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hilfritz.joke.backend.jokeApi.JokeApi;
*/

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class JokeProvider {
    //private  JokeApi jokeApi;
    /**
     * ref: http://www.boybanat.com/2013/12/tagalog-random-jokes-and-pinoy-favorite.html
     */
    String[] tagalogJokes = new String[]{
            "Anak: \" Dad, totoo po ba na may multo?\" \nDad: \"Anak walang multo! Kalokohan lang yun. Bakit mo naitanong?\" \nAnak: \"Sabi po kasi ni yaya me multo!\" \nDad: \"Anak, wala tayong yaya!\" ",
            "Sabi ng sosyal na Ipis kay Inday: \"Don't you dare hit me with that magazine you are holding!\" \nInday: \"Avah, and why not you dirty land-dwelling anthropod?\" \nIpis: \"Because only Havaianas touches my skin. Who touches yours?\"" ,
            "Mister: Honey nakukunsyensya ako,dapat ko ng ipagtapat ito sa iyo. \nMisis: Honey okay sa akin, mahal naman kita. \nMister: Honey alam mo, kapag nagseseks tayo, iba ang pinapantasya kong babae. \nMisis: A ganon ba!? Eh honey ikaw naman ang nasa isip ko kapag kaseks ko ang ibang lalaki!! " ,
            "Driver: oh yung mga panget jan pwde ng magsi-babaan, my checkpoint sa hrapan! \nPasahero: eh kuya? Sinu na po magmamaneho? " ,
            "Juan: \"Pedro, nahirapan ka ba sa questions ng exam?\" \nPedro: Hindi! \nJuan: \"Wow! ang galing mo naman! \nPedro: \"Mas nahirapan ako sa answers!\" " ,
            "Tatay: anak bakla ka ba? \nAnak: opo.. (sabay lubog ng mukha ng anak sa harina.) \nTatay:ANO? ngay0n la2ki ka na ba? \nAnak:geisha n po.. (nagalit ang tatay sabay nilubogmukha ng anak s baldeng puno ng tubig..) \nTatay:!@#$ anu ka na?! SAGOT! Anak:dyesebel n po.. (nagalit lalo ang tatay..kaya pinaso nya ito ng plantsa hnggang ito'y mangitim..) \nTatay:!@#$% ka! ano k n ngayon!! Anak: ako n po c beyonce!!" ,
            "(Si Pedro ay nakapulot ng bote na may genie sa loob.) \nGenie: Hohoho! Bibigyan kita ng 3 kahilingan. \nPedro: 1. Gusto kong tumira sa malaking bahay \n2. Gusto kong puti ang complexion ko. \n3. Gusto ko lahat ng babaeng makakita sa'kin ay maghuhubad ng panty at papatong sa'kin. BaBaBOOM! Naging TOILET BOWL si Pedro.",
            "Girl Banat: \"I'm warning you! baka biglang dumating si daddy!\" \nBoy Banat: \"So? eh wala naman tayong ginagawang masama ah!\" \nGirl Banat: \"Kaya nga! If you're planning something, BILISAN MO NA!\" " ,
                    "Job interview: \nBoss: \"Ano ang alam mo?\" \nApplicant: \"Alam ko po kung saan kayo nakatira. At kung saan nakatira ang kabit niyo.\" \nBoss: \"OK. Tanggap ka na!\" " ,
                    "Three nurses napagtripan si dok. During lunch break, nagkwento kung anong kalokohan ginawa nila. \nNurse 1: Nilagyan ko ng bulak yung stethoscope ni Dok para wala syang marinig. \nNurse 2: Binutas ko lahat ng condoms nya. \nNurse 3: WAAHH!!! (hinimatay) " ,
                    "(sa isang madilim na waiting shed) \nMan: \"Miss, wag kang kikilos ng masama\" \nMiss: \"Naku 'wag po! Bakit po ba?\" \nMan: \"Kasi pag kumilos ka ng masama...BAD yun!\" " ,
                    "Mam: \"Aba! kelan lang tayo bumili ng toothpick, bakit naubos agad?\" \nInday: \"Ewan ko nga mam. Kapag ako po ang gumamit, sinosoli ko naman eh!\" "
            };






    public String getRandomTagalogJokes(){
        int tagalogJokeSize=tagalogJokes.length;
        int randInt = randInt(0, tagalogJokeSize-1);
        return tagalogJokes[randInt];
    }


    /**
     *
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     * @see http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


}
