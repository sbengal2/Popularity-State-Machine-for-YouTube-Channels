# CSX42: Assignment 2
**Name:**
Shashank Bengaluru Srinivasa
-----------------------------------------------------------------------

Following are the commands and the instructions to run ANT on your project.


Note: build.xml is present in [channelpopularity/src](./channelpopularity/src) folder.

## Instruction to clean:

```commandline
ant -buildfile channelpopularity/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

## Instructions to compile:

```commandline
ant -buildfile channelpopularity/src/build.xml all
```
The above command compiles your code and generates .class files inside the BUILD folder.

## Instructions to run:

```commandline
ant -buildfile channelpopularity/src/build.xml run -Dinput="input.txt" -Doutput="output.txt"
```
Note: Arguments accept the absolute path of the files.
The input.txt file or any other input file for the program should be present in channelpopularity directory or in the path : ./csx42-summer-2020-assign2-username/channelpopularity.
If it is placed anywhere else absolute path for the file should be provided in the command used to run. 

##Output Discrepencies:
The popularity score of the channel is an average.(average of popularity scores of all its videos).
Therefore, the channel's popularity score is a float. The score is rounded off to two decimal places.
## Description:
A Youtube channel has a popularity score.
The popularity score is average popularity score of all videos contained in the channel. 
Based on its popularity score, a channel can be in one of following states:

unpopular

mildly-popular

highly-popular

ultra-popular.

Operations are performed on the videos in the channel and in turn on the channel.
These operations are performed from the current state a channel is present in.

## justification for the choice of data structures (in terms of time and/or space complexity). 
#### A video class is created.An instance of this video class can store all the attributes of a given video.I have used a Hash map data structure to map each video instance to the name of the video.The combination of video instances and hasp map is efficient because:
1.Without the Video class each attribute of a video should have been mapped separately to different maps.
2.Without the hashmap the access or searching time would have increased significantly.

Space-complexity:O(n)

Time-Complexity to search a video by its name: O(1)
## citations for external material utilized.
Stackoverflow and geeks for geeks only for reference.

## Academic Honesty statement:

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date:06/24/2020


