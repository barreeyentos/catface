# CatFace

## Description

This is a small service that detects a "cat face" given a confidence threshold and a character based image. 

A simple API (described) below, is exposed on the running port and responds with any found "cat faces" in the provided image.


## Build

The project uses the gradle wrapper to handle build testing and dependencies.  Minimum of Java 1.8 is needed to run the application.  To do a full test and build type:

```bash
./gradlew clean build
```
This builds a standalone jar found in the bin directory

## Run

Before running make sure to either set your security username and password in environment files or the application.yml

The easiest way to run the server is to run it locally using the spring boot gradle plugin

```
./gradlew bootRun
```

The above will run the server locally and expose the APIs on port :8080

Optionally you can run the server as a docker container via the provided Dockerfile

```bash
docker build -t catface:latest .
docker run -p 8080:8080 --name catface catface:latest
```
The above will build a local image and run it mapping port 8080

## Configuration

The application has a few configurable parameters found in `src/main/resources/config/application.yml`

## API

The server exposes a single endpoint where you can post a threshold and character matrix representing an image.  The server will respond wit all found cat faces with a corresponding confidence and position of the top left coordinate in the original image.

There is a provided sample request in the `postman_collection` folder.  Import into Postman as a collection and if you run the server locally on port 8080 it will send the post request and receive a response.

### /v1/catfaces

`POST` <service_url>:<port>/v1/catfaces

**Request Body:** 
`Content-Type:` application/json

As an image url
```json
{
  "confidenceThreshold": 0.85,
  "imageUrl": "https://somewebsite.com/catimage.txt"
}
```
As a text array
```json
{
  "confidenceThreshold": 0.85,
  "image": [["+"," "," ","+"],
			["+","+","+","+"],
			["+"," "," ","+"],
			[" ","+","+"," "]]
}
```
`confidenceThreshold:` Defines the minimum confidence needed to elicit a catface response.  This value must be a double between **0.0 - 1.0** inclusive.

The image can be sent in one of two ways:
`imageUrl:` This is the location of the cat image to analyze.  This can either be online "http://" or local file "file://".
`image:` a text matrix

**Successful Response:** 
`Status:` 200
`Content-Type:` application/json
`Body`:

```json
{
	"catFaces": [
		{
			"confidence": 0.95,
			"position": {
				"x": 0,
				"y": 0
			}
		}
	]
}
```


A successful response can send zero or more found cat faces with their associated confidence and position.

**Note:** the position represents the top left corner of where the cat face was found within the original image.  The image is a 0-indexed array where x=0, y=0 is the first character of the image.

**Unsuccessful Responses:**
`Status`: 400

Unsuccessful responses with 400 response codes are returned for either invalid confidence thresholds or missing imageURL.

`Status`: 422

Unsuccessful responses with 422 response codes indicate the given imageUrl could not be reached or read successufully. 



## Logistic Training for Theta coefficients

This service uses logistic regression without regularization to find cat faces within an image.

The folder `octave_catface_training` continas Octave scripts/functions to train the algorithm.

Simply run `> trainCatFace` from the Octave prompt.
