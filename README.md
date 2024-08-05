# About the project

This project is an application that interacts with the external gitHub API to fetch information about a user's repositories and all their branches. It provides a singular endpoint returning a JSON with information from two APIs in a more readable form.
After running it, the endpoint on the 8080 localport with the address of: /repositoriesInfo/user/${username} and provides the information concerning all the public, non-fork repositories. 
This information is: Repository Name, Owner Login and for each branch it’s name and the last commit sha.

The project is written in the Java language. It uses Maven and Spring Boot framework. It has a service and controller layers, as well as all the DTO classes needed to handle external API. Testing was performed using Mockito framework and
was done for both service and controller classes. In case it's needed the API for user's repositories can be changed in application.properties file. The url for branch info API is taken from the user repositories' API.

## Features

- Fetch repositories for a specified GitHub user.
- Present repository information in a readable format through a REST controller.
- Provide the user with easy to understand information and instructions in case of an error.

### Technologies Used
- Java 21
- Spring Boot
- Spring Web
- RestTemplate
- GitHub API
- Mockito

#### Installation
- Clone the repository
- Build the project
- Run the application
