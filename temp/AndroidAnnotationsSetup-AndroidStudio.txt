<article id="post-4" class="post-4 post type-post status-publish format-standard hentry category-android category-it">
	
	<header class="entry-header">
		<h1 class="entry-title">AndroidAnnotations Setup on Android Studio</h1>	</header><!-- .entry-header -->

	<div class="entry-content">
		<p>Android today&nbsp;is&nbsp;the next big thing. But creating Android apps is not an easy task. It requires persistence, hard work and dedication.</p>
<p>Today I am going to show you how to setup AndroidAnnotations library&nbsp;<a href="https://github.com/excilys/androidannotations">(link)</a>&nbsp;in AndroidStudio for your Android Project. It is a powerful library that really helps code very nice to read and very short. It comes with many features (like view injections, resource injections etc.) and&nbsp;simplifies your code. In my experience this is very handy, code updates or fixing bugs or adding new feature is very easy to implement and fix with the help of this library</p>
<p>Note: This is done on the following date with the following versions of software: AndroidAnnotations (3.3.2), Android Studio (1.2.1)</p>
<ol>
<li>On your main build.gradle add the following code snippets. Some of the code below are already existing in your build.gradle file, just update them by adding the lines below to them appropriately.</li>
</ol>
<pre>repositories {
&nbsp; &nbsp; mavenCentral()
&nbsp; &nbsp; mavenLocal()
 }</pre>
<pre>buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
    }
}</pre>
<p>2. On your app build.gradle add the following code snippet</p>
<pre>apply plugin: 'android-apt'
def AAVersion = 'X.X.X' //this correspond to the version of AndroidAnnotations you want to use</pre>
<pre>dependencies {
    apt "org.androidannotations:androidannotations:$AAVersion"
    compile "org.androidannotations:androidannotations-api:$AAVersion"
}</pre>
<pre>apt {
    arguments {
        androidManifestFile variant.outputs[0].processResources.manifestFile
}</pre>
<p>You are all set it is now all setup. You can now use the annotations and other features offered by AndroidAnnotations.</p>
<p>Note: Everytime you add a new Activity/Service, do ‘Make’ <strong>first</strong> before you add them to Manifest.xml.</p>
<p><a href="http://localhost/wordpress/wp-content/uploads/2015/11/make.png"><img class="alignnone size-medium wp-image-10" src="http://localhost/wordpress/wp-content/uploads/2015/11/make-300x172.png" alt="make" width="300" height="172"></a></p>
<p>Here is the sample sources&nbsp;for this post:&nbsp;<a href="http://localhost/wordpress/wp-content/uploads/2015/11/androidannotations-setup-src.zip">androidannotations-setup-src</a></p>
	</div><!-- .entry-content -->

	
	<footer class="entry-footer">
		<span class="posted-on"><span class="screen-reader-text">Posted on </span><a href="http://localhost/wordpress/2015/11/16/androidannotations-setup-on-android-studio/" rel="bookmark"><time class="entry-date published" datetime="2015-11-16T07:52:00+00:00">November 16, 2015</time><time class="updated" datetime="2015-11-16T08:00:49+00:00">November 16, 2015</time></a></span><span class="byline"><span class="author vcard"><span class="screen-reader-text">Author </span><a class="url fn n" href="http://localhost/wordpress/author/hilfritz/">hilfritz</a></span></span><span class="cat-links"><span class="screen-reader-text">Categories </span><a href="http://localhost/wordpress/category/it/android/" rel="category tag">Android</a>, <a href="http://localhost/wordpress/category/it/" rel="category tag">IT</a></span>			</footer><!-- .entry-footer -->

</article>