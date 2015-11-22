package com.hilfritz.joke.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.Random;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeApi",
        version = "v1",
        resource = "joke",
        namespace = @ApiNamespace(
                ownerDomain = "backend.joke.hilfritz.com",
                ownerName = "backend.joke.hilfritz.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    private static final Logger logger = Logger.getLogger(JokeEndpoint.class.getName());
    /**
     * Ref: http://sms4smile.com/category/funny-sms/
     * Ref: http://sms4smile.com/category/funny-sms/page/5/
     */
    private String[] jokeList= new String[]{
            "Love is possible after friendship\n" +
                    "but\n" +
                    "friendship is not possible after love\n" +
                    "because\n" +
                    "medicines work before death\n" +
                    "later nothing can be cured....!!!" ,
            "There are two type of studies:\n" +
                    "\n" +
                    "1 - hard subjects which Cannot be studied.\n" +
                    "2 - easy subject that Doesn't need to be studied" ,
            "Thought of the day:\n" +
                    "\"if u help a gal when she is in problem,\n" +
                    "she will always remember u\n" +
                    "only when she is in problem again..!!\"" ,
            "A young girl after her honeymoon\n" +
                    "came fully exhausted and tired,\n" +
                    "\n" +
                    "When her friends asked her what happened?\n" +
                    "\n" +
                    "She replied :\n" +
                    "When this 70 year old bastard told me\n" +
                    "he has saved a lot from last 50 years,\n" +
                    "\n" +
                    "\"I thought It was MONEY\"" ,
            "1st Man: Which Is The Best Month\n" +
                    "To Get Married..?\n" +
                    "\n" +
                    "2nd Man: Octemb ruary\n" +
                    "\n" +
                    "1st Man: Don't Be Silly,\n" +
                    "There Is No Such Month\n" +
                    "\n" +
                    "2nd Man: Exactly" ,
            "One million copies of a new book sold\n" +
                    "In just 2 days due to typing error of 1 alphabet in title.\n" +
                    "\n" +
                    "\"An idea,that can change ur wife''\n" +
                    "While real word was(life)." ,
            "Little johnny: Mam,will you punish me\n" +
                    "for something that I didn't do ?\n" +
                    "\n" +
                    "Teacher : Not at all.\n" +
                    "\n" +
                    "Little johnny : That's good.\n" +
                    "Actually i didn't do my homework!" ,
            "Man : How old is your father?\n" +
                    "Boy : As old as me.\n" +
                    "\n" +
                    "Man : How can that be?\n" +
                    "Boy : He became a father only when I was born" ,
            "Love Happens Automatically.\n" +
                    ".\n" +
                    ".\n" +
                    ".\n" +
                    "Manual Working Of It\n" +
                    "Is Called Flirting :D" ,
            "Height of coolness:\n" +
                    "2 Guys coming out of the examination Hall with chips and coke in hands....\n" +
                    "1st guy:which paper was it?\n" +
                    "2nd guy:I think maths......\n" +
                    "1st guy:(surprisingly) you read the question paper?\n" +
                    "2nd guy: no I see a girl sitting besides me using calculator:>" ,
            "Employer: In this job we need someone who is responsible.\n" +
                    "\n" +
                    "Applicant: I'm the one you want.\n" +
                    "On my last job, every time anything went wrong,\n" +
                    "they said I was responsible." ,
            "A very old lady teacher of English\n" +
                    "ask this question with the class:\n" +
                    "\n" +
                    "When I say \"I am beautiful\", which tense is it?\n" +
                    "\n" +
                    "One pupil answered: Its the past tense of course." ,
            "Boy1:Meet my wife Tina\n" +
                    "Boy2.Oh! I know her\n" +
                    "Boy1:How?\n" +
                    "Boy2:v were caught sleeping together\n" +
                    "Boy1:What the hell?\n" +
                    "Boy2.during lecture in maths class" ,
            "What is the perfect example\n" +
                    "of both Good & Bad Luck?\n" +
                    "\n" +
                    "The naughty wind blows the girl's skirt high (Good luck)\n" +
                    "\n" +
                    "but at the same time\n" +
                    "\n" +
                    "Dust falls into the boy's eyes (Bad luck)" ,
            "Some people ask the secret\n" +
                    "of our long marriage.\n" +
                    "\n" +
                    "We take time to go to a restaurant\n" +
                    "two times a week.\n" +
                    "\n" +
                    "A little candlelight, dinner, soft music\n" +
                    "and dancing.\n" +
                    "\n" +
                    "She goes Tuesdays, I go Fridays." ,
            "He said... Do u love me just coz\n" +
                    "my father left me a fortune?\n" +
                    "She said... No stupid, I'd love u no matter\n" +
                    "who left you the money!" ,
            "Specially dedicated to boys:\n" +
                    "A kiss is like a stamp,\n" +
                    "Once u stamp a gal,\n" +
                    "she wouldn't go anywhere else\n" +
                    "\n" +
                    "Guys r like stamp paper," ,
            "Why love marriage\n" +
                    "is better than arranged???\n" +
                    "Because\n" +
                    "\"A KNOWN DEVIL\n" +
                    "IS BETTER THAN\n" +
                    "AN UNKNOWN GHOST\"" ,
            "When u feel sad....\n" +
                    "To cheer up just go to the mirror and say,\n" +
                    "\"damn I am really so cute\"\n" +
                    "u will overcome your sadness.\n" +
                    "But don't make this a habit.....\n" +
                    "Coz liars go to hell !!!!"
           };



    /**
     * This method gets the <code>Joke</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Joke</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getJoke")
    public Joke getJoke(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getJoke method");
        return null;
    }

    /**
     * This inserts a new <code>Joke</code> object.
     *
     * @param joke The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertJoke")
    public Joke insertJoke(Joke joke) {
        // TODO: Implement this function
        logger.info("Calling insertJoke method");
        return joke;
    }

    @ApiMethod(name = "getRandomJoke")
    public Joke getRandomJoke(){
        int randomInt = randInt(0, jokeList.length-1);
        return new Joke(jokeList[randomInt]);
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