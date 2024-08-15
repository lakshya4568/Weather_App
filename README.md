# Weather Project

*Add the Json-Jar file (source from net) to project structure
to the lib directory*

* Add the assets directory to src (contains img/assest)
* in the main dir, create the java class WeatherGUIApp

> **_NOTE:_**  Super() method is used to call the constructor of the parent class

Jframe constructor can take parameter as String, for title.
Also, GraphicConfiguration class, and both.

* setDefaultCloseOperation() -> to end the process
 
EXIT_ON_CLOSE -> java.swing constant to exit the application. (taken as parameter)

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