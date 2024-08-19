# Weather Project

*Add the Json-Jar file (source from net) to project structure
to the lib directory*

* Add the assets directory to src (contains img/asset)
* in the main dir, create the java class WeatherGUIApp

> **_NOTE:_**  Super() method is used to call the constructor of the parent class

Jframe constructor can take parameter as String, for title.
Also, GraphicConfiguration class, and both.

* setDefaultCloseOperation() -> to end the process

EXIT_ON_CLOSE -> java. Swing constant to exit the application. (taken as parameter)

**setLocationRelativeTo()** -> Sets the
location of the window relative to the specified component ... Null can be used to place the window at center of screen.

A layout manager in Java Swing is an object that controls  the size and position of components within a container.

* Set Location, Layout of the gui.
* set the resizeable of gui, true or false based on your condition

> We will create the main() in AppLauncher class and display our GUI

__Async__ -> it's a term associated with asynchronous programming, a paradigm where tasks can start
executing without waiting for previous one to finish.

__Runnable interface in Main method :__
*When we execute our app, it will call the run() which instantiates our gui and displays it*

## Adding some GUI Components

In the WeatherGUI, go and add some gui components
* JTextField will add a text box in the window
* customise the ui with the help of bound, font and fixes


**For adding a imageIcon button ->**  We can create a loadImage method of ImageIcon class.

* BufferImage class will help to manipulate the image content;
* create the "image", object with BufferImage class, and assign it to the value of source image destination
* ImagIO class will help to read and write to an image, .read() method will read the image from the source
* create the object of file class with new operator, and give the string value object assign to file object
* Return the image object of ImageIcon return type

### Weather Description Method

> **`SwingConstants`** - It's an interface that is a collection of constants which generally uses for positioning and orientation of swing components.

* Set bounds, fonts and alignment for this component.
* Font method -> **new** Font(name, style(font), size)-> Font class
* add the component

#### Add More GUI Components 

Add Components such as Humidity identifier and Wind-speed using JLabel class of swing library and set their font and bounds.

## Weather API

### JSON

Import the JSON jar file to the project structure, which contains the org.json package, it has classes as JSONArray and
JSONObject.
which will help to parse the JSON data. We can store the JSON data as array through JSONArray class and as object
through JSONObject class.

To use weather forecast api we need to give the longitude and latitude data which can be found using their Geolocation
API,
that's why we will be creating another API call where it will take in and entered location, and returns the latitude and
longitude data

#### HTTPS Status Code

<h6> Level 200 </h6>
<ol>
<li>200 : OK</li>
<li>201 : Created</li>
<li>202: Accepted</li>
<li>203: Non-Authoritative Information</li>
<li>204: No Content</li>
</ol>

about the string Builder, scanner hasNext method, and explaining in detail about the
getLocationData method is necessary;

_JavaScript Format for different data Structure_

> Array, objects and json = [], {}, "{}";

The geolocation api will return us a list of different countries that have entered city,
so we are going to use the first's object data, hence we use get(0) in `location object` of JSONObject class in
getLocationData method.

<p> we can also see, that we can retrieve the values using the get ("json property"), we also have to cast type whenever we get a value from JSONObject</p>

> The get(key) method of JSONObject class will return the value to which the key is specified, and we can cast it to the
> required type.