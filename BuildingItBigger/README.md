# Gradle for Android and Java Final Project

In this project, you will create an app with multiple flavors that uses
multiple libraries and Google Could Endpoints. The finished app will consist
of four modules. A Java library that provides jokes, a Google Could Endpoints
(GCE) project that serves those jokes, an Android Library containing an
activity for displaying jokes, and an Android app that fetches jokes from the
GCE module and passes them to the Android Library for display.

## Why this Project

As Android projects grow in complexity, it becomes necessary to customize the
behavior of the Gradle build tool, allowing automation of repetitive tasks.
Particularly, factoring functionality into libraries and creating product
flavors allow for much bigger projects with minimal added complexity.

##What Will I Learn?

You will learn the role of Gradle in building Android Apps and how to use Gradle to manage apps of increasing complexity. You'll learn to:

* Add free and paid flavors to an app, and set up your build to share code between them
* Factor reusable functionality into a Java library
* Factor reusable Android functionality into an Android library
* Configure a multi project build to compile your libraries and app
* Use the Gradle App Engine plugin to deploy a backend
* Configure an integration test suite that runs against the local App Engine development server

##How Do I Complete this Project?

### Step 0: Starting Point

This is the starting point for the final project, which is provided to you in the [course repository](https://github.com/udacity/ud867/tree/master/FinalProject).
It contains an activity with a banner ad and a button that purports to tell a
joke, but actually just complains. The banner ad was set up following the
instructions here:

https://developers.google.com/mobile-ads-sdk/docs/admob/android/quick-start

You may need to download the Google Repository from the Extras section of the
Android SDK Manager.

When you can build an deploy this starter code to an emulator, you're ready to
move on.

### Step 1: Create a Java library

Your first task is to create a Java library that provides jokes. Create a new
Gradle Java project either using the Android Studio wizard, or by hand. Then
introduce a project dependency between your app and the new Java Library. If
you need review, check out demo 4.01 from the course code.

Make the button display a toast showing a joke retrieved from your Java joke
telling library.

### Step 2: Create an Android Library

Create an Android Library containing an Activity that will display a joke
passed to it as an intent extra. Wire up project dependencies so that the
button can now pass the joke from the Java Library to the Android Library.

For review on how to create an Android library, check out demo 4.03. For a
refresher on intent extras, check out;

http://developer.android.com/guide/components/intents-filters.html

### Step 3: Create GCE Module

This next task will be pretty tricky. Instead of pulling jokes directly from
our Java library, we'll set up a Google Cloud Endpoints development server,
and pull our jokes from there. Follow the instructions in the following
tutorial to add a Google Could Endpoints module to your project:

https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints

Introduce a project dependency between your Java library and your GCE module,
and modify the GCE starter code to pull jokes from your Java library. Create
an Async task to retrieve jokes. Make the button kick off a task to retrieve a
joke, then launch the activity from your Android Library to display it.

### Step 4: Add Functional Tests

Add code to test that your Async task successfully retrieves a non-empty
string. For a refresher on setting up Android tests, check out demo 4.09.

### Step 5: Add a Paid Flavor

Add free and paid product flavors to your app. Remove the ad (and any
dependencies you can) from the paid flavor.

## Optional Tasks

To exceed expectations, do the following:

### Add Interstitial Ad

Follow these instructions to add an interstitial ad to the free version.
Display the add after the user hits the button, but before the joke is shown.

https://developers.google.com/mobile-ads-sdk/docs/admob/android/interstitial

### Add Loading Indicator

Add a loading indicator that is shown while the joke is being retrieved and
disappears when the joke is ready. The following tutorial is a good place to
start:

http://www.tutorialspoint.com/android/android_loading_spinner.htm

### Configure Test Task

To tie it all together, create a Gradle task that:

1. Launches the GCE local development server
2. Runs all tests
3. Shuts the server down again

# Rubric

### Required Components

* Project contains a Java library for supplying jokes
* Project contains an Android library with an activity that displays jokes passed to it as intent extras.
* Project contains a Google Cloud Endpoints module that supplies jokes from the Java library. Project loads jokes from GCE module via an async task.
* Project contains connected tests to verify that the async task is indeed loading jokes.
* Project contains paid/free flavors. The paid flavor has no ads, and no unnecessary dependencies.

### Required Behavior

* App retrieves jokes from Google Cloud Endpoints module and displays them via an Activity from the Android Library.

### Optional Components

To receive "exceeds specifications", your app must fully implement all of the following items.

* The free app variant displays interstitial ads between the main activity and the joke-displaying activity.
* The app displays a loading indicator while the joke is being fetched from the server.
* The root build.gradle file contains a task that will start up the GCE development server, run all Android tests, then shutdown the development server.



<b>PERSONAL NOTES</b>
<br/>
    <h4>Google Cloud Module:</h4>
    <p>https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
    </p>
<br/>
    <h4>Precautions in running a n activity from a library project</h4>
    <p>http://stackoverflow.com/questions/6157616/using-an-android-library-project-activity-within-another-project
    </p>
<br/>
    <h4>APPENGINE JAVA SDK MANUAL installation</h4>
    <ol>
        <li> First download the appengine-java-sdk here: (by clicking the <b>download zip</b>)
            <p>http://mvnrepository.com/artifact/com.google.appengine/appengine-java-sdk or
            <br>http://central.maven.org/maven2/com/google/appengine/appengine-java-sdk/1.9.18/appengine-java-sdk-1.9.18.zip
            </p>
            <p><i>as of this writing we will be using 1.9.18</i></p>
        </li>
        <li>Go to <b>C:/<Users>/.gradle/</b></li>
        <li>Create the folder <b>appengine-sdk<b/> if not existing</li>
        <li>Copy the downloaded zip file to that same directory and extract</li>
        <li>The directory structure should be something like this
            <p>C:\Users\hilfritz.p.camallere\.gradle\appengine-sdk\appengine-java-sdk-1.9.18\</p>
            <p>under that directory should be the <i>bin, config, demos, docs, lib, src folders</i> and other files</p>
        </li>
        <li>After that you have successfully installed APPENGINE JAVA SDK </li>
    </ol>

<br/>
<h4>GRADLE GUIDE </h4>
<p>ref:http://rominirani.com/2014/08/25/gradle-tutorial-part-8-gradle-app-engine-endpoints-android-studio/</p>
<p>Basic Gradle Commands</p>
    <p>
    gradle -q help  (This command prints out basic help information for Gradle. The -q parameter is just for quiet mode in the console output.)
    <br>
    gradle -q tasks (This shows a list of tasks that are already available to you)
    <br>
    gradle properties (This will list of several properties that are preconfigured for you.)
    </p>




<h4>UNIT TEST</h4>
<p>https://github.com/JCAndKSolutions/android-unit-test</p>
<p>https://github.com/mutexkid/android-studio-robolectric-example</p>


<h4>Android gradle buildtypes, duplicate classes</h4>
<p>https://stackoverflow.com/questions/18782368/android-gradle-buildtypes-duplicate-class</p>
<p>
You can not have a class in <i>main<i/> and <i>release<i/>. You need to split it to something like <i>debug<i/> and <i>release<i/>.

gradle will merge the source sets for each <i>buildType<i/> with <i>main<i/>.
This is the reason, why the class get's duplicated in your <i>release<i/> build.

So the rule is: put a class into <i>main<i/>, or in <b>every</b> <i>buildType<i/> but not both.
</p?