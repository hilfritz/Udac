<article id="post-31" class="post-31 post type-post status-publish format-standard hentry category-it">
	
	
		JSON String to POJO and vice versa

	
		<p>JSON String thru Rest api webservices is very common in mobile development especially in Android. The String is very light weight and is a good fit for mobile devices knowing the fact that some mobile devices are not very powerful in specifications and computing.</p>
<p>This will show you how to create a JAVA class from Json string using <strong>GSON</strong>.<br>
We will be using an online tool called&nbsp;<a href="http://www.jsonschema2pojo.org/">jsonschema2pojo</a>.</p>
<p><strong>Prerequisites:</strong></p>
<ul>
<li>Make sure you have the GSON dependecy setup either maven, gradle or through manually adding the .jar file to your project</li>
</ul>
<p>For example you have the following JSON String,</p>
<pre>{
"firstName": "John", 
"lastName": "Smith", 
"address": { 
 "streetAddress": "21 2nd Street", 
 "city": "New York"
 }
}</pre>
<p>Go to&nbsp;<a href="http://www.jsonschema2pojo.org/">http://www.jsonschema2pojo.org/</a>, then copy and paste their the JSON String.</p>
<ul>
<li>Set the following form fields to:</li>
<li>Source type: JSON</li>
<li>Annotation style: GSON</li>
<li>Use Joda dates: check (optional)</li>
<li>Use Commons-Lang3: check (optional)</li>
</ul>
<p>Like in the screenshot:</p>
<p><a href="images/jsonstringtopojo.png"><img class="alignnone size-medium wp-image-32" src="http://localhost/wordpress/wp-content/uploads/2015/11/jsonschema2pojo-sample-300x255.png" alt="jsonschema2pojo-sample" width="300" height="255"></a></p>
<p>Click the Jar button at the bottom to download the archive file that contains the classes generated from your JSON string.</p>
<p>This is how the generated java class for the JSON string above:</p>
<pre>package com.example;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
//Delete/comment the above line
public class Customer {

 @SerializedName("firstName")
 @Expose
 private String firstName;
 @SerializedName("lastName")
 @Expose
 private String lastName;
 @SerializedName("address")
 @Expose
 private Address address;

 public String getFirstName() {
     return firstName;
 }

 public void setFirstName(String firstName) {
     this.firstName = firstName;
 }

 public String getLastName() {
     return lastName;
 }

 public void setLastName(String lastName) {
     this.lastName = lastName;
 }

 public Address getAddress() {
     return address;
 }

 public void setAddress(Address address) {
     this.address = address;
 }
}</pre>
<pre>package com.example;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("streetAddress")
    @Expose
    private String streetAddress;
    @SerializedName("city")
    @Expose
    private String city;

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}

</pre>
<p>That’s it.</p>
<p>Bonus:</p>
<p>Convert JSON string to POJO and vice versa using GSON .</p>
<pre>//JSONString -&gt; Java object
Gson gson = new Gson();
Customer customer = gson.fromJson(myJSONString, Customer.class);
</pre>
<pre>//Java object -&gt; JSONString 
Gson gson = new Gson();
Customer customer = new Customer("John","Doe");
String json = gson.toJson(customer);
</pre>
	
</article>