# SoulsVania

## Project Overview: 
For this project I was tasked with designing, building, and gathering resources necessary to create a 
fully functional videogame. The task was to be done in java utilizing only the base java library. With this task we learned how to use various functions. How we can implement projects and maintain the code when working with multiple diƯerent classes and larger project sizes. 
## Basic concept: 
Soulsvania is a 2D platformer that tasks the player with eliminating all of the enemies on the screen and once that is done, they complete the level. There are three levels so far. The enemies have a very simple ai of when they see the player they all come to the player and attack. 



## Purpose of jar Folder 
The jar folder will be used to store the built jar of your term-project.

`THIS FOLDER CAN NOT BE DELETED OR MOVED`


## Version of Java Used:
JDK 22

## IDE used: 
IntelliJ IDEA 2024.1.2
The version I used to build this game at the start of the project was Java 17 but I upgraded to 22 by 
the end of the project. And the IDE used was IntelliJ IDEA 2024.1.2. 
For the resources and images, I have provided them in the repository. For my levels though I used 
images that I got through a tutorial. And those can be found in the GitHub repository they have 
which I will attach to the project for easy reference. 
For the tiles I used this page: https://pixelfrog-assets.itch.io/treasure-hunters
For my character sprites and enemies, I used: https://clembod.itch.io/warrior-free-animation-set
Some of the UI Elements Used: 
https://github.com/KaarinGaming/PlatformerTutorial/tree/ep_24/PlatformerTutorial/res


## Steps to Build your Project:
<pre>
To import this project to IntelliJ IDEA there are various ways you could clone the repository. One way 
is to download the zip from the repository link for the game and then extract files from that zip on 
your device and then inside IntelliJ select File-> open folder and select the folder of the game. 
Another method is to clone the project from the given URL. To do this you can use the IntelliJ IDE 
directly through the toolbar and selecting Git-> clone and then entering the URL for the project. And 
from there it should directly clone to the IDE. 
From there go to the main class of the application and run the Main.java class. This will initialize the 
entire Game application. To build the project define a module to this go to File-> project structure 
click module. From there ensure the module includes SoulsVania src and resources folders. So that 
all the necessary items are present and accounted for. 
To build the jar after defining the module, be sure that you build the module. And then after that go 
back to File -> project structure and go to the artifact tab in that window. And then inside the 
Artifacts page press the + button to create new artifact and select jar-> from modules with 
dependencies. And then from there a new window should pop up set the Main Class to Main of 
soulsvania.main. Ensure that the Meta-inf/Manifest.MF is going to be in the resources folder press 
OK and then ensure the csc413-tankgame-imknott compile output is an element of the jar file. 
From there click apply. And then go to Build-> Build artifacts. Select the specified artifact and click 
build. 
From there you have options to run the jar, you can move the file to your desktop or within the 
IntelliJ IDE right click on the jar file and click run. If you chose to move it to your desktop open 
terminal by right clicking on your desktop main screen and selecting open in terminal. And from 
there if the file is not within a folder. Ensure the terminal is in the desktop directory and if it is run 
the command “java -jar jarfilename” 
Once that is done the game should run and is ready to be played
</pre>
 
## Steps to run your Project:
=======
To run the project, you can follow these steps:
1. Open the project in IntelliJ IDEA.
2. Make sure the Main.java class is selected as the main class.
3. Run the Main.java class by clicking the green play button or by running the jar file. 

## Controls to play your Game:

|          | Player 1 |
|----------|----------|
| Forward  | D        |
| Backward | A        |
| Attack   | J        |
| Pause    | ESC      |
| Jump     | SpaceBar |

<!-- More controls will be added to the game. -->

# Class Descriptions: 

## Entities package: 
<pre>
Enemy class: This class is a subclass of the Entity class which handles various logic of a 
given enemy. Such as the ai to see if the enemy can see the player. Setting up the 
animations for each enemy. And to be a parent of each other enemy character class we will 
define. 

Bringer class: This is the class for the enemy we will have throughout the game. This be 
called inside of the enemy manager class to get the animations for this specific enemy type. 

Enemy Manager class: Inside the enemy manager class it takes in the level data and 
decides how many of a specific enemy type should be displayed within the level given the 
current level. It draws them to the screen and handles various updates of animations to be 
displayed and sets the attack and hitboxes for the given characters. 

Player class: The player class handles all the necessary elements that would make a 
playable character do what they intend to such as having inputs to display diƯerent
animations. A method that updates the player’s position relative to the screen and level 
data. A hitbox that tells us if the character is above a tile, on a tile or hitting a tile. 
</pre>
## Inputs package: 
<pre>
KeyBoardIInputs class: This is the class that tells the system if we are in each state of the 
game if these keys should be able to input information to our game. If we are in a given state specified such as playing, then movement of the character will be displayed. 

MouseInputs class: This will handle the mouseinputs used such as through the various UI 
elements found through the game. 
</pre>
## Levels package: 
<pre>
Level class: This class handles the information that is for each level within the level data 
array. It loads resources such as objects, tiles, props, and characters to the screen.

LevelManager class: Defines the levels and the data to be stored for each level. With this we 
can set different values for each of the specified color items in the level image files. 
</pre>
# Main package: 
<pre>
Main class: This class runs the game class. 

Game class: Runs the program and initializes the various updates and frames that the 
application will call. 

GamePanel class: Sets the size of the game panel. And is where the inputs are defined and 
checked. And is where we will display the updates and paint components to. 

GameWindow class: Is the class for defining the JFRAME 

Sound class: Is the class that handles all sounds and sets diƯerent methods such as 
initializing when we want to play a given sound. Or if we want the song to loop or stop. 
</pre>
# Object package: 
<pre>
GameObject class: Is the super class for the objects that I would place in the game that are 
either traps or items that have an eƯect.

ObjectManager class: Handles drawing the specified objects to the screen and where they 
should be located within the given level.

Spikes class: This class draws and defines an trap that will kill the player if they fall on them.
</pre> 
# Props package: 
<pre>
Prop class: This was to be used as a super class to the various props that I wanted to add to 
the background like trees and ladders. 

PropManager class: Would handle things similarly to my other manager class where it 
would load the given information to the screen by the given color I would choose within the 
image file. 
</pre>
# States package: 
<pre>
GameState enum: This defines the constant states that the game application will have such 
as playing, menu, options and quit. 

GameStateMethods interface: Is the interface that describes the methods that each state 
class will use. 

Menu class: This is the title screen class that will display the opening menu along with the 
buttons needed and the inputs that will be used.

OptionsMenu class: This is menu that will be displayed when clicking the options button in 
the main menu of the game. 

Playing class: This is where the main action happens and where we draw the level data and 
handle enemy and player actions. 

State class: The parent class to all of the state classes.
</pre> 
# UI package:
<pre> 

functionButtons class: These are the buttons that will do diƯerent actions like return to the 
home screen start the next level and restart the level. 

GameOverLay class: This class draws the image to be displayed when the player dies. 

LevelCompleteUI class: When the player eliminates all enemies display this level complete 
UI to the gamepanel. 

MenuButton class This is where I get the images for each item to be displayed in the menu 
UI. 

MusicSfxOptions: The options screen to be drawn that will use the SoundButton 

Volumebutton to control the loudness or turn oƯ the sound eƯects of the game. 

PauseButton: is a class that defines the bounds of the buttons in our pause ui/ 

PauseUI class: Draws the various images and buttons when the player presses pause.
 
SoundButton class: In charge of methods for button the turns oƯ sound eƯects.
 
VolumeButton class: Controls the eƯects of the slider and music controller. 
</pre>
# Utils package: 
<pre>

Constants class: This specifies the constant values used throughout the game such as 
enemy state, player state, size of buttons and objects. It also handles setting constant 
health values for enemies. 

HelperMethods: These helper methods handle collisions, get players starting point in the 
game. As well as defining the actions enemies will take when seeing the player within a 
certain point and tile location. 

LoadSave class: this class sets up each of the images within the game to return a buƯered 
image and reduce the amount of try and catch statements needed to type out for each 
image. 

UtilityTool class: reduces the file sizes to have better rendering times. Didn’t end up using 
yet but was going to add it within to the setup method under the image within the try 
statement. 
</pre>