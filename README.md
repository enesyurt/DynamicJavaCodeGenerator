# Java Code Generator from XML

## Overview

This project is a Java-based tool that generates Java class and enum files from an XML input file. The purpose of this project is to automate the creation of Java classes and enums based on predefined structures defined in an XML file.

## Features

- **Dynamic Class Generation**: Automatically generates Java classes based on `dataType` elements defined in the XML file.
- **Enum Generation**: Automatically generates Java enums based on `enumType` elements defined in the XML file.
- **XML Parsing**: Utilizes Java DOM parser to read and parse the XML file.
- **File Writing**: Writes the generated Java class and enum files to a specified directory.

## Project Structure
```plainText
DynamicJavaCodeGenerator/
├── generated/
│   └── [Generated Java Files]             # This folder will contain the generated Java files (e.g., id.java, Name.java, Status.java)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── CodeGenerator.java # Main class responsible for generating Java classes and enums from XML
│   │   │           ├── DataType.java      # DataType class representing a data type parsed from XML
│   │   │           ├── EnumType.java      # EnumType class representing an enum type parsed from XML
│   │   │           └── XMLParser.java     # XMLParser class responsible for parsing the XML file
│   │   └── resources/
│   │       └── example.xml                # Example XML file containing the structure of data types and enum types
├── pom.xml                                # Maven configuration file
└── README.md                              # Project documentation (this file)

```
## XML Structure

The XML file (`example.xml`) should be structured as follows:

```xml
<root>
    <dataType>
        <name>id</name>
        <type>int</type>
    </dataType>
    <dataType>
        <name>name</name>
        <type>String</type>
    </dataType>
    <enumType>
        <name>Status</name>
        <value>ACTIVE</value>
        <value>INACTIVE</value>
        <value>DELETED</value>
    </enumType>
</root>
```

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
3. **Try with your data**: Place your XML file (e.g., `example.xml`) in the `src/main/resources` directory.
4. **Run**: Run the following Maven command to execute the CodeGenerator:
```bash
mvn exec:java -Dexec.mainClass="com.example.CodeGenerator"
```
5. **Check generated files**: The generated Java files will be located in the generated/ directory.

## Example

For the [XML Structure](#xml-structure) given the tool will generate:
- id.java
```java
public class id {
    private int id;
}
```
- name.java
```java
public class name {
    private String name;
}
```
- Status.java
```java
public enum Status {
    ACTIVE,
    INACTIVE,
    DELETED
}
```

## Contributing
If you would like to contribute to this project, please fork the repository and submit a pull request. We welcome any improvements or bug fixes.

## Contact
For any inquiries or questions, please contact [enesyurt78@gmail.com](enesyurt78@gmail.com).


## Licence
This project is licenced under the MIT Licence.