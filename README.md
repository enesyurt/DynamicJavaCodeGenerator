# Java Code Generator from XML

This is one of the tasks given during my internship at HAVELSAN.

## Overview

This project is a Java application that generates Java classes from XML files. It provides a graphical user interface (GUI) for users to select XML files or folders containing XML files and specify an output folder where the generated Java classes will be saved.

## Features

- **Select Multiple Input Files**: Select multiple XML files or a folder containing XML files.
- **XML Parsing**: Parse the selected XML files to generate Java classes.
- **Specify Output Folder**: Specify an output folder for the generated Java classes.
- **Graphical User Interface**: Simple and intuitive graphical user interface built with Swing.

## Project Structure
```plainText
DynamicJavaCodeGenerator/
├── generated/
│   └── [Generated Java Files]                # This folder will contain the generated Java files 
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       ├──  example/
│   │   │       │    ├── ModelGenerator.java  # Handles the generation of Java classes from XML documents.
│   │   │       │    ├── Parser.java          # Parses XML files into Document objects.
│   │   │       │    ├── TypeLoader.java      # Loads type information from XML documents.
│   │   │       │
│   │   │       └──  gui/
│   │   │            └── MainGUI.java         # Provides the graphical user interface for the application.
│   │   └── resources/
│   │       └── TestInfogramDefinition.xml    # Example XML file containing the structure of data types and enum types
├── pom.xml                                   # Maven configuration file
└── README.md                                 # Project documentation (this file)

```
## Dependencies

The project uses the following dependencies:
* ```bash javax.swing``` : For creating the graphical user interface.
* ```org.w3c.dom``` : For handling XML documents.

## Usage

To generate Java classes and enums from an XML file, follow these steps:
1. **Clone the repository**
```bash
git clone https://github.com/enesyurt/DynamicJavaCodeGenerator.git
cd DynamicJavaCodeGenerator
```
2. **Build the project**: Ensure you have Java and Maven installed on your system, then run:
```bash
mvn clean install
```
3. **Try with the data**: Place your XML file (e.g., `TestInfogramDefinition.xml`) in the `src/main/resources` directory.
4. **Run**: Run the following Maven command to execute the CodeGenerator:
```bash
mvn exec:java -Dexec.mainClass="com.gui.MainGUI"
```
5. **Use the GUI**: The program will open you a GUI for you to select input and output folders.

6. **Select XML Files or Folder**: Click on the `Select XML Files or Folder` button to choose multiple XML files or a folder containing XML files. The paths of the selected files will be displayed in the text field.

7. **Select Output Folder**: Click on the `Select Output Folder` button to specify the folder where the generated Java classes will be saved.

8. **Generate Java Classes**: Click on the `Generate Java Classes` button to start the generation process. A message will be displayed upon successful generation or if an error occurs.

## Contributing
If you would like to contribute to this project, please fork the repository and submit a pull request. Any improvements or bug fixes are welcomed.

## Contact
For any inquiries or questions, please contact [enesyurt78@gmail.com](mailto:enesyurt78@gmail.com).


## Licence
This project is licenced under the MIT Licence.