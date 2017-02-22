# Image Tagger

This is a server to assign tags to a bunch of images.

## Setup

### Requirements
- Install maven `apt-get install maven`
- Create a database in mariadb

### Config

Config file are in */src/main/resources*

- in **application.properties**
	- Set *spring.datasource.url* property to the url of your newly created database
	- Set *spring.datasource.username* and *spring.datasource.password* to your db's credentials
	- *spring.jpa.hibernate.ddl-auto* should be set at *create-drop* to initialize the database, *images.path* to the path of your image stash folder, *generate_from_scratch* set to true

- Write desired tags in **tags.json** file
- Users for HTTP authentication are generated in source file because i'm lazy ¯\\_(ツ)_/¯. Change them in *src/main/java/date/animu/image_tagger/controller/WebSecurity.java*

When restarting the server with an existing database you don't want to erase, set *spring.jpa.hibernate.ddl-auto* to *validate* and *generate_from_scratch* to false.