Fuzzer
==============================================================================================

Contributors:
	Gabriel Marcano <gem5597> 	
	Connor Hack <csh6900>
	Joseph Ksiazek <jak3122>

Current Release 
==============================================================================================
1.0

Description 
==============================================================================================

File contains two main folders:

   - docs

   - src

-> documents : a folder containing:

   - README.txt : This file contains a description of the project's contents
   - pageGuesses.txt : An example file containing common page names and extensions for use
                       with the --common-words parameter

-> src : a folder containing the source code for our Fuzzer
   
   -> fuzzer.apps
   		
   		- CLIParser.java : Command-line Interface Parser
   		- CLIParserTest.java
   		- ExecuteVectors.java
   		- fuzzer.java : The main program
        - InputDiscovery.java : Class that discovers various inputs on a webpage
        - InputManipulation.java
        - PageLogin.java : Handles logging in to webpages
        - PageLoginTest.java
        - SensitiveDataSearch.java
        
   -> fuzzer.apps.VVector
   
   		- BufferOverflowVector.java
   		- BufferOverflowVectorTest.java
   		- SanitizationVector.java
   		- SanitizationVectorTest.java
   		- VVector.java
   		- VVectorTest.java   
   		- XSS_SQLVector.java
   		- XSS_SQLVectorTest.java

Installation and Usage
==============================================================================================
For Windows:

1. Extract the contents of SWEN-Fuzzer
2. Run compile.bat to compile the program
3. Modify run.bat and add or edit the command line arguments at the end of the "java" command
4. Execute run.bat to run the program

Alternatively:
Use this command to COMPILE the program:
javac -cp lib\* -d bin src\fuzzer\apps\*.java

Use this command to RUN the program:
java -classpath bin;lib\* fuzzer.apps.fuzzer [discover | test] url OPTIONS
Example:
java -classpath bin;lib\* fuzzer.apps.fuzzer discover http://127.0.0.1:8080/bodgeit/login.jsp --custom-auth=bodgeit --common-words=docs/pageGuesses.txt


