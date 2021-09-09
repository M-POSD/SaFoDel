
<p align="center">
  <img src="https://user-images.githubusercontent.com/88869576/130990605-514f741e-8113-465d-bba5-64700b490d3e.png">
</p>
  
## [SaFoDel](https://m-posd.github.io/)
Educating Food Delivery Bike Riders in Victoria

<img src="https://user-images.githubusercontent.com/88869576/130999668-f6d115d8-a5d7-4e58-bb18-cd1047a8add2.gif">


## Features✨
- Accident Trends add accident trend graph for each suburb and common information among all suburbs
- E-Bike Delivery information sharing (Find information on using e-bikes for food delivery)
- Safety Gears information sharing (Find out the cycling gear you need to deliver safe)
- In an Accident tips sharing (Find out the information while encountered the accident)
- Ride Safer tips sharing (Find out how to ride safely while delivering food)
- A quiz for testing user road rules understanding
- A map to see accident location base on LGA area
- Locate the current location  
- A good Logo
- A good UI
 
## Download

Download `.apk` file on our [Website](https://m-posd.github.io/) or on the [Release](https://github.com/M-POSD/SaFoDel/releases) page

## Try it online

[Appetize](https://appetize.io/embed/004vf8q2r7afaz8rhbvkg7w3zm?device=pixel4&scale=75&orientation=portrait&osVersion=10.0&location=(39.903924,116.391432)) (Iteration 2)

Appetize limite our account to 1 concurrent user each time. If screen showing 'Account-level queue', please wait.

## Android development

- Written in [Kotlin](https://kotlinlang.org/) 
- Using [Mapbox](https://www.mapbox.com/) developed map functions
- A server built by [Python](https://www.python.org/) and deployed in [AWS](https://aws.amazon.com/)

## Building
### Open the Project in [Android Studio](https://developer.android.com/studio)

```
git clone https://github.com/M-POSD/SaFoDel.git
```
Open the `SaFoDel/` directory in Android Studio.

### Get Started
```shell
./gradlew build
```
### Tips
- Require the latest Android Studio Arctic Fox release to be able to build the app
- Update the Kotlin to latest version.
- Emulator with the latest sdk 30 (at least 24) by clicking on the AVD Manager on the navigation bar.

## Mapbox tokens

Follow Mapbox's [docs](https://docs.mapbox.com/help/getting-started/access-tokens/) to create your tokens.

- Edit "mapbox_access_token" in `res/values/strings.xml`.
- Edit "MAPBOX_DOWNLOADS_TOKEN" in `/gradle.properties`

## Third-party open source projects

| Module              | Description
| ------------------- | -----------
| [AutoSize](https://github.com/JessYanCoding/AndroidAutoSize) | Auto size the screen
| [Explosionfield](https://github.com/tyrantgit/ExplosionField)  | Explosive dust effect for views
| [Material-dialogs](https://github.com/afollestad/material-dialogs) | Create dialog
| [Spotlight](https://github.com/TakuSemba/Spotlight)              | Lights wherever we want




