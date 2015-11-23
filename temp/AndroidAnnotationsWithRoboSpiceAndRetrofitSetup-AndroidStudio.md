<article id="post-18" class="post-18 post type-post status-publish format-standard hentry category-android category-it">
	
	
	## AndroidAnnotations with Robospice’s Retrofit Module</h1>	

	
<h2>Intro:</h2>
<p>AndroidAnnotations is a very good library for code readability and shortening lines of code. It offers many features like view injection, easy handling of the UI and background threads.</p>
<p>Robospice is a very interesting tool for handling rest api webservice calls. It handles these calls efficiently and helps prevent memory leakages and the effects on&nbsp;orientation change or changes device configuration.</p>
<p>Retrofit is also a promising Rest client service for Java, it is also a good addition or option to use for Android development.</p>
<p>This article picks up from the <a href="./2015/11/16/androidannotations-setup-on-android-studio/">previous post</a> AndroidAnnotations Setup on Android Studio. Let us get started.</p>
<h2>Setup for Robospice’s Retrofit Module</h2>
<p>On your application’s build.gradle file add the following:</p>
<pre>compile <span class="pl-s"><span class="pl-pds">'</span>com.octo.android.robospice:robospice-retrofit:1.4.14<span class="pl-pds">'</span></span></pre>
<h2>Setup Retrofit&nbsp;library</h2>
<p>On your application’s build.gradle file add the following:</p>
<pre>compile 'com.squareup.retrofit:retrofit:1.9.0'
compile 'com.squareup.okhttp:okhttp-urlconnection:2.0.0'
compile 'com.squareup.okhttp:okhttp:2.0.0'
//take note here, the current version may be later than the ones used //here, there is no problem using the current versions</pre>
<p>Now&nbsp;you have set up robospice and retrofit, you need to create the class representations of your JSON responses.</p>
<p>//Assumed here that you already have these classes for GSON to use.<br>
//See this post:&nbsp;<a href="./2015/11/16/json-string-to-pojo-and-vice-versa/">json-string-to-pojo-and-vice-versa</a></p>
<p>&nbsp;</p>
<p>We need to create an <strong>INTERFACE</strong> where we declare all our network requests. Create an interface named <strong>SpotifyApi</strong>:</p>
<pre>public interface SpotifyApi {
    @GET("/search")
     public SearchWrapper searchArtistW(
        @Query("q") String artistName,
        @Query("type") String type,         //should always be artist
        @Query("limit") int limit,
        @Query("offset") int offset);       
    //should either be DEFAULT_LIMIT + OFFSET

    @GET("/artists/{id}/top-tracks")
    public TopTracksWrapper getTopTracks(
        @Path("id") String id,
        @Query("country") String country,  
            @Query("limit") int limit,
            @Query("offset") int offset
            );
}</pre>
<p>We need to create a <strong>SERVICE</strong>&nbsp;to handle all our network requests and use the above interface (SpotifyApi). Like so, the methods in the Service is important.</p>
<pre>import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;</pre>
<pre>public class SpotifySpiceService extends RetrofitGsonSpiceService {
 //the base url of the webservice
 private final static String BASE_URL = "https://api.spotify.com/v1";
 @Override
 public void onCreate() {
 super.onCreate();
 addRetrofitInterface(SpotifyApi.class);
 }
 @Override
 protected String getServerUrl() {
 return BASE_URL;
 }
}</pre>
<p>Make sure to have this permissions in the Manifest file:</p>
<pre>&lt;uses-permission android:name="android.permission.INTERNET" /&gt;
&lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /&gt;
&lt;uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /&gt;</pre>
<p>Also the Service itself needs to be declared:</p>
<pre>&lt;service
    android:name=".SpotifySpiceService"
    android:exported="false" /&gt;
</pre>
<p>In your base activity, we need to declare there the spicemanager to handle all rest requests. Like so.</p>
<pre>public abstract class BaseActivity extends ActionBarActivity {
    //this is the spice manager, we pass our service class that handles
    //all rest requests
    private SpiceManager spiceManager = new SpiceManager(SpotifySpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(getApplicationContext());
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        spiceManager.shouldStop();
    }
    public SpiceManager getSpiceManager() {
        return spiceManager;
    }
}</pre>
<p>Everything is now setup, we&nbsp;can now go to the logic of calling the webservice. We need to create their corresponding Request classes. Basically this is the ratio, the number of <strong>GET</strong> requests you put in your interface (SpotifyApi.java), that is also the <strong>same number of Request classes</strong>&nbsp;we need.</p>
<table>
<tbody>
<tr>
<th>SpotifyApi.java</th>
<th>Request Classes to create</th>
</tr>
<tr>
<td>searchArtist</td>
<td>SearchArtistRequest</td>
</tr>
<tr>
<td>getTopTracks</td>
<td>SearchArtistTopTracksRequest</td>
</tr>
</tbody>
</table>
<h3>SearchArtistRequest.java</h3>
<pre>import com.hilfritz.webserviceclient.SpotifyApi;
import com.hilfritz.webserviceclient.wrapper.SearchWrapper;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class SearchArtistRequest extends RetrofitSpiceRequest&lt;SearchWrapper, SpotifyApi&gt; {
    public static final String TYPE_ARTIST = "artist";
    public static final int DEFAULT_LIMIT = 5;
    public static final int DEFAULT_OFFSET = 0;
    private String artistName;
    private int limit = DEFAULT_LIMIT;
    private int offset = DEFAULT_OFFSET;

    public SearchArtistRequest(String artistName, int limit, int offset) {
        super(SearchWrapper.class, SpotifyApi.class);
        this.artistName = artistName;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public SearchWrapper loadDataFromNetwork() throws Exception {
        return getService().searchArtist(artistName, TYPE_ARTIST, this.limit, this.offset);
    }
}</pre>
<h3>SearchArtistTopTracksRequest.java</h3>
<pre>import com.hilfritz.webserviceclient.SpotifyApi;
import com.hilfritz.webserviceclient.wrapper.TopTracksWrapper;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class SearchArtistTopTracksRequest extends RetrofitSpiceRequest&lt;TopTracksWrapper, SpotifyApi&gt; {
    public static final String COUNTRY = "PH";
    public static final int DEFAULT_LIMIT = 10;
    public static final int DEFAULT_OFFSET = 0;
    private String id;
    private int limit = DEFAULT_LIMIT;
    private int offset = DEFAULT_OFFSET;

    /**
     *
     * @param id String refers to the id of the artist
     * @param limit
     * @param offset
     */
    public SearchArtistTopTracksRequest(String id, int limit, int offset) {
        super(TopTracksWrapper.class, SpotifyApi.class);
        this.id = id;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public TopTracksWrapper loadDataFromNetwork() throws Exception {
        return getService().getTopTracks(id, COUNTRY, this.limit, this.offset);
    }
}</pre>
<p>As you can see both SearchArtistRequest and&nbsp;SearchArtistTopTracksRequest should extend RetrofitSpiceRequest. Both of their first generic class on RetrofitSpiceRequest refers to the bean that GSON will convert the json string to. The second generic class of RetrofitSpiceRequest should always be the interface created&nbsp;before.</p>
<h4>Using the SearchArtistRequest</h4>
<p>This is how you call the request:</p>
<ol>
<li>Make an object of the Request class</li>
<li>Call the SpiceManager to execute the request</li>
<li>Add a <strong>RequestListener</strong> class, that is responsible for handling the result of the request</li>
</ol>
<pre>//Performing the request
SearchArtistRequest searchArtistRequest = new SearchArtistRequest("eminem", SearchArtistRequest.DEFAULT_LIMIT * 10, SearchArtistRequest.DEFAULT_OFFSET);
((BaseActivity)this).getSpiceManager().execute(searchArtistRequest, "search", DurationInMillis.ALWAYS_EXPIRED, new SearchRequestListener());</pre>
<h4>Listening for the Result of the SearchArtistRequest</h4>
<ol>
<li>Create a class that implements <strong>RequestListener</strong> class</li>
<li>Add the generics class of the RequestListener same as the <strong>1st parameter</strong> of the Request class</li>
<li>Implement the methods <strong>onRequestSuccess</strong> and <strong>onRequestFailure</strong> of the class</li>
</ol>
<pre>private class SearchRequestListener implements RequestListener&lt;SearchWrapper&gt; {
    @Override
    public void onRequestFailure(SpiceException spiceException) {
    }

    @Override
    public void onRequestSuccess(SearchWrapper searchWrapper) {

        searchWrapper.getArtists().getItems();
    }
}</pre>
<h3>Additional:</h3>
<p>I ended up adding the following to my app’s build.gradle file, to prevent build errors:</p>
<p>Inside the <strong>android&nbsp;</strong>block add this code</p>
<pre>packagingOptions {
    exclude 'META-INF/services/javax.annotation.processing.Processor'
    exclude 'META-INF/LICENSE.txt'
    exclude 'META-INF/NOTICE.txt'
}</pre>
<p>Here is the source code for this article: <a href="files/androidannotations-robospice-retrofit.zip">androidannotations-robospice-retrofit</a></p>
	
</article>