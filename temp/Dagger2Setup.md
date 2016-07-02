# (Dagger2) Dependency Injection

In here the following will be covered:

 * Setup
 * Sample code 
 * Small explanations

This is the specific commit with the sample Dagger2 implementation:
[LINK](https://github.com/hilfritz/Udac/commit/1a410958560ba687436f5e2db6856f811cfc418c) 

### Some theories:


 * Component - contains all the places (classes) where you need an object (ex. MainActivity, ListFragment, etc)
 * Module - here is where you create the objects you need in the Component
 * 

### Start/Explanation of the specific commit:
We will be using the MyAppPortfolio project, and use the Album classes and setup dagger 2.! :+1:


### 1. Setup:

On Top level build.gradle:
```javascript
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:1.5.0+'
        /*Below is the important part, also the gradle version used is 1.5.0+ as above
        */
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}

```


On application build.gradle:
```javascript
apply plugin: 'com.android.application'
/*
* Always make sure the code below always comes after the com.android.application plugin
*/
apply plugin: 'com.neenbedankt.android-apt'

```

On application build.gradle dependencies:
```javascript
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:21.0.3'
 	/*Below are the current dagger dependencies as the time of writing*/
    apt 'com.google.dagger:dagger-compiler:2.2'
    compile 'com.google.dagger:dagger:2.2'
    provided 'javax.annotation:jsr250-api:1.0'

}

```

Sync your android project for the gradle changes to take effect.


### 2. Creating module:
To be able to Inject a class we need to create a Module first. This module creates the objects that will be injected.

Here is a sample class file that I wanted to inject in classes AlbumActivity.java and AlbumPhotoActivity.java

```java
/** This is the class to inject
*/
public class AlbumBaseRequest {
    RestAdapter restAdapter;
    AlbumListApi albumListApi;

    public AlbumBaseRequest() {
         restAdapter = new RestAdapter.Builder()
                .setEndpoint(AlbumListApi.URL)
                .build(); 
    }
    public RestAdapter getRestAdapter(){
        return restAdapter;
    }
    public AlbumListApi getApi(){
        if (albumListApi==null)
            albumListApi = getRestAdapter().create(AlbumListApi.class);
        return albumListApi;
    }
}

```

This is the AlbumListApi.java being referenced in the AlbumBaseRequest.java that we want to inject.
```java
/** This is the retrofit interface refrenced in our class to inject.
*/
public interface AlbumListApi {
    public static final String URL="http://jsonplaceholder.typicode.com";

    @GET("/albums")
    public Observable<List<Album>> getAlbumsO();

    @GET("/photos")
    public Observable<List<Photo>> getAllPhotoO();

    @GET("/users/{id}")
    public Observable<Users> getSingleUserO(
            @Path("id") String id
    );
    @GET("/users")
    public Observable<List<Users>> getallUserO();

    @GET("/albums")
    public List<Album> getAlbums();

    @GET("/photos")
    public List<Photo> getAllPhoto();

    @GET("/photos/{id}")
    public Photo getSinglePhoto(
            @Path("id") String id
    );

    @GET("/users/{id}")
    public Users getSingleUser(
            @Path("id") String id
    );
}
```

```java
/** This is how to create the module, modules PROVIDE the object to be injected.
See the @Provides annotation methods, it returns the object I want to inject.
*/
@Module
public class RestApiModule {
    @Provides @Singleton
    AlbumBaseRequest provideAlbumRestApi(){
        return new AlbumBaseRequest();
    }
}
```

### 3. Creating the component:
A component tells Dagger the connection between the @Inject and @Provides module. 
It explicitly tells where an object will be available for injection. This is  done through:

```java
@Singleton
@Component(modules = {RestApiModule.class}) //we add here all the modules under RestApi
public interface RestApiComponent {
    void inject(AlbumActivity albumActivity); 
    void inject(AlbumPhotoActivity albumPhotoActivity);
}

```
This tells dagger that the objects returned inside the RestApiModule methods
(by the @Provides method) will be available only at
```java
AlbumActivity.java and AlbumPhotoActivity.java
```

Now Dagger2 knows where the object that it creates will be available for injection.

Component - is the connection between the @Inject and @Provides methods.

### 4. Configure Application.class:
```java
public class AppMainApplication extends Application {
    RestApiComponent restApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDagger();
    }
    private void initializeDagger(){
        restApiComponent = DaggerRestApiComponent.builder()
                .restApiModule(new RestApiModule())
                .build();
    }
    public RestApiComponent getRestApiComponent() {
        return restApiComponent;
    }
}
```
Note: <br>
Dagger creates/generates a class for your component,  here you see DaggerRestApiComponent class. They just add the prefix “Dagger”. Then just add the modules needed for the component as above. If you don’t see the prefixed classes, try to rebuild the project.

### 5. Using injections (@Inject):

In your Activity/Fragment/Class where you want to inject the objects, don’t forget to inject the Component in like so:
```java
public class AlbumPhotoActivity extends Activity {
   /**The object we want to inject
   */
    @Inject
    AlbumBaseRequest albumApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_photo);
        /**This is important, this DOES the injection*/
        (((AppMainApplication) getApplication()).getRestApiComponent()).inject(this);
            }
```

After that you can now use <b>albumApi</b> object in your activity directly. And AlbumBaseRequest is a singleton object,. No need to do the following code everytime:
```java
AlbumBaseRequest albumApi = new AlbumBaseRequest ();
```


Regards,<br> 
Hilfritz


### References:

 * https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2
 * [https://www.youtube.com/watch?v=SKFB8u0-VA0](https://www.youtube.com/watch?v=SKFB8u0-VA0) 
 * [http://code.tutsplus.com/tutorials/dependency-injection-with-dagger-2-on-android--cms-23345](http://code.tutsplus.com/tutorials/dependency-injection-with-dagger-2-on-android--cms-23345)
 * [https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2](https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2) 
 * [js-deflate](https://github.com/dankogai/js-deflate) for gzipping of data to make it fit in URLs
